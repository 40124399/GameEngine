package Utility;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configurations {

    private static final Logging LOG = new Logging(Configurations.class);
    private static final String PROPERTIES_PATH = System.getProperty("user.dir")+"\\res\\Config\\settings.properties";

    private static final List<String> keys = Arrays.asList(
            "LOGGING_LEVEL"
    );
    private static final List<String> values = Arrays.asList(
            "10"
    );

    private static Map<String,String> map;

    private static Properties prop = new Properties();
    private static boolean loaded = false;

    private static void save() {
        OutputStream output = null;
        LOG.printWithLevel(1, "ATTEMPTING TO SAVE PROPERTIES");

        try {
            output = new FileOutputStream(PROPERTIES_PATH);

            // set the properties value
            for(String s : map.keySet()) {
                prop.setProperty(s, map.get(s));
            }

            // save properties to project root folder
            prop.store(output, null);
            LOG.printWithLevel(1, "PROPERTIES SAVED SUCCESSFULLY");

        } catch (IOException | NullPointerException io) {
            LOG.printWithLevel(1,"CRITICAL ERROR - UNABLE TO SAVE PROPERTIES - "+PROPERTIES_PATH);
            LOG.printStackTrace(io);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    LOG.printWithLevel(1,"ERROR - UNABLE TO CLOSE OUTPUT STREAM");
                    LOG.printStackTrace(e);
                }
            }

        }
    }

    public static void load(boolean secondAttempt) {
        InputStream input = null;

        try {
            input = new FileInputStream(PROPERTIES_PATH);
            LOG.printWithLevel(5, "LOADING FROM -> "+PROPERTIES_PATH);
            prop.load(input);

            map = new HashMap<>(prop.keySet().size(),1);
            for(Object key : prop.keySet()) {
                String k = (String) key;
                map.put(k, prop.getProperty(k));
            }

            LOG.printWithLevel(5,"LOADED SUCCESSFULLY");

        } catch (IOException | NullPointerException ex) {
            LOG.printWithLevel(1,"ERROR - NO SUCH FILE - "+PROPERTIES_PATH);
            create();
            if(!secondAttempt)
                load(true);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.printWithLevel(1,"ERROR - UNABLE TO CLOSE INPUT STREAM");
                    LOG.printStackTrace(e);
                }
            }
        }
    }

    private static void create() {
//        String filePath = System.getProperty("user.dir")+"\\res"+PROPERTIES_PATH;
        LOG.printWithLevel(1,"ATTEMPTING TO CREATE - "+PROPERTIES_PATH);
        OutputStream output = null;

        try {
            output = new FileOutputStream(PROPERTIES_PATH);

            for(int i = 0; i < keys.size(); i++) {
                prop.setProperty(keys.get(i), values.get(i));
            }

            prop.store(output, null);

            LOG.printWithLevel(1, "CREATED SUCCESSFULLY");
        } catch (IOException | NullPointerException io) {
            LOG.printWithLevel(1,"ERROR - UNABLE TO CREATE - "+PROPERTIES_PATH);
            LOG.printStackTrace(io);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    LOG.printWithLevel(1,"ERROR - UNABLE TO CLOSE OUTPUT STREAM");
                    LOG.printStackTrace(e);
                }
            }

        }
    }

    public static String getString(String setting) {
        String value = map.get(setting);
        if(value == null) {
            LOG.printWithLevel(1, "MISSING FIELD - " + setting + " - RECREATING PROPERTIES");
            create();
            value = prop.getProperty(setting);
            if (value == null)
                LOG.printWithLevel(-1, "CRITICAL FIELD MISSING - "+setting);
        }
        return value;
    }

    public static Integer getInt(String setting) {
        try {
            String value = map.get(setting);
            if(value == null) {
                LOG.printWithLevel(1, "MISSING FIELD - " + setting + " - RECREATING PROPERTIES");
                create();
                value = prop.getProperty(setting);
                if (value == null) {
                    LOG.printWithLevel(-1, "CRITICAL FIELD MISSING - " + setting);
                    value = "0";
                }
            }
            return Integer.parseInt(prop.getProperty(setting));
        } catch (NumberFormatException e) {
            LOG.printWithLevel(1,"ERROR - BAD NUMBER FORMAT -> RETURNING 0");
            LOG.printStackTrace(e);
            return 0;
        }
    }

    public static void editValue(String key, String value) {
        map.put(key, value);
    }
}

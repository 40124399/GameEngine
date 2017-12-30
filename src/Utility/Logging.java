package Utility;

import jdk.jfr.StackTrace;

public class Logging {

    private static final String LOGGING_LEVEL_NAME = "LOGGING_LEVEL";
    private static final String MULTILOG_DELIMITER = " -- ";
    private static int LOGGING_LEVEL = 10;

    private String className;
    private Class aClass;

    public Logging() {
        this.className = "UNKNOWN_CLASS";
        this.aClass = Object.class;
        this.LOGGING_LEVEL = Configurations.getInt(LOGGING_LEVEL_NAME);
    }

    public Logging(String className) {
        this.className = className;
        this.aClass = Object.class;
        this.LOGGING_LEVEL = Configurations.getInt(LOGGING_LEVEL_NAME);
    }

    public Logging(Class aClass) {
        this.className = aClass + "";
        this.aClass = aClass;

        if(aClass.equals(Configurations.class))
            LOGGING_LEVEL = 10;
        else
            this.LOGGING_LEVEL = Configurations.getInt(LOGGING_LEVEL_NAME);
    }

    public void print(String text) {
        System.out.println(prependClassInfo(text));
    }

    public void printWithLevel(int level, String text) {
        if(level > LOGGING_LEVEL)return;
        System.out.println(prependClassInfo(text));
    }

    public void print(String text, String ... multiple) {
        String toPrint = prependClassInfo(text) + " ";

        for(String s : multiple) {
            toPrint += s + MULTILOG_DELIMITER;
        }

        System.out.println(toPrint.substring(0,toPrint.length()-4));
    }

    public void printWithLevel(int level, String text, String ... multiple) {
        if(level > LOGGING_LEVEL)return;
        String toPrint = prependClassInfo(text) + " ";

        for(String s : multiple) {
            toPrint += s + MULTILOG_DELIMITER;
        }

        System.out.println(toPrint.substring(0,toPrint.length()-4));
    }

    public void printStackTrace(Exception e) {
        if (LOGGING_LEVEL > 0) e.printStackTrace();
    }

    private String prependClassInfo(String text) {
        return className + " :: " + text;
    }
}

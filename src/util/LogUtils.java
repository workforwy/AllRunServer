package util;

import org.apache.log4j.PropertyConfigurator;

import java.util.logging.Logger;

public class LogUtils {

    private static LogUtils singleton = null;

    public Logger sysException;

    public int i = 0;

    private LogUtils() {
        sysException = null;
        //使用相对路径
        String path = (LogUtils.class.getClassLoader().getResource("")).toString();
        System.out.println(path);

        //去掉  file:/
        path = path.substring(6);
        System.out.println(path);

        path = path + "log4j.properties";
        PropertyConfigurator.configure(path);
        sysException = Logger.getLogger("sysException");
    }

    public static LogUtils getInstance() {
        try {
            init();
        } catch (Exception e) {
            return null;
        }
        return singleton;
    }

    static void init() throws Exception {
        singleton = new LogUtils();
    }

    public static void main(String[] args) {
        LogUtils logger = LogUtils.getInstance();
        if (logger != null) {
            logger.sysException.info("wap6");
        }
    }
}

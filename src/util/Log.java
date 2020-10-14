package util;

import org.apache.log4j.PropertyConfigurator;

import java.util.logging.Logger;

public class Log {

    private static Log singleton = null;

    public Logger sysException;

    public int i = 0;

    private Log() {
        sysException = null;
        //ʹ�����·��
        String path = (Log.class.getClassLoader().getResource("")).toString();
        System.out.println(path);

        //ȥ��  file:/
        path = path.substring(6);
        System.out.println(path);

        path = path + "log4j.properties";
        PropertyConfigurator.configure(path);
        sysException = Logger.getLogger("sysException");
    }

    public static Log getInstance() {
        try {
            init();
        } catch (Exception e) {
            return null;
        }
        return singleton;
    }

    static void init() throws Exception {
        singleton = new Log();
    }

    public static void main(String[] args) {
        Log logger = Log.getInstance();
        if (logger != null) {
            logger.sysException.info("wap6");
        }
    }
}

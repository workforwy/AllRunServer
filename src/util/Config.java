package util;

/**
 * @author wangyong
 */
public class Config {

    public static final String allClassFolder = "";

    public static String siteRoot = "";

    //发布时用
    public static final String webSite = "http://192.168.1.10";

    //测试开发机模拟器上android客户端时使用，开发机模拟器上android客户端必须用ip直接访问
//    public static final String webSite="http://192.168.1.101";

    public static final String comicImageDir = "upLoadComicData";

    static {
        siteRoot = (Log.class.getClassLoader().getResource("")).toString();
        System.out.println(siteRoot);

        //去掉file:/
        siteRoot = siteRoot.substring(6);
        System.out.println(siteRoot);

        //去掉WEB-INF/classes/
        int length = siteRoot.length();
        siteRoot = siteRoot.substring(0, length - "/WEB-INF/classes/".length());
        System.out.println(siteRoot);

        //把/替换成 \\
        siteRoot = Tools.replaceString(siteRoot, "/", "\\");
        System.out.println(siteRoot);
    }


    public static void main(String[] args) {
        System.out.println(siteRoot);
    }


}

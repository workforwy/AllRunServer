package util;

public class Config {

    public static String siteRoot = "";
    //发布时用
    public static final String webSite = "http://192.168.1.10";

    //测试开发机模拟器上android客户端时使用，开发机模拟器上android客户端必须用ip直接访问
    //public static final String webSite="http://192.168.1.101";
    public static final String comicImageDir = "upLoadComicData";

    static {
        siteRoot = (Log.class.getClassLoader().getResource("")).toString();
        // System.out.println(siteRoot);
        //去掉file:/
        siteRoot = siteRoot.substring(6);
        //去掉WEB-INF/classes/

        int length = siteRoot.length();
        siteRoot = siteRoot.substring(0, length - "/WEB-INF/classes/".length());
// 把/替换成 \\
        // System.out.println(siteRoot);

        siteRoot = Tools.replaceString(siteRoot, "/", "\\");
    }


    public static void main(String[] args) {
        System.out.println(siteRoot);
    }


}

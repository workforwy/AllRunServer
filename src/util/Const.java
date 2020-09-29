package util;

public class Const {

    public static final int STATUS_OK = 0;
    public static final String STATUS_OK_MSG = "成功";

    public static final int STATUS_FAILURE = 1;

    public static final int STATUS_SERVER_ERROR = 101;
    public static final String STATUS_SERVER_ERROR_MSG = "服务器代码执行出错";

    public static final int STATUS_LOGIN_ERROR = 102;
    public static final String STATUS_LOGIN_ERROR_MSG = "账户登录不成功，账号或者密码错误！";

    public static final int STATUS_REGISTER_ERROR = 201;
    public static final String STATUS_REGISTER_ERROR_MSG = "用户已经存在";
    public static final int STATUS_WITHOUT_RELEASE = 301;
    public static final String STATUS_WITHOUT_RELEASE_MSG = "没有新版本";

    public static final int STATUS_NO_FILE = 401;
    public static final String STATUS_NO_FILE_MSG = "没有上传图片";
    public static final int STATUS_REGISTER_FILE_SIZE_BIG = 402;
    public static final String STATUS_REGISTER_FILE_SIZE_BIG_MSG = "文件超过10M";

}

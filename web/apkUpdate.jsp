<%@page import="dao.TopicDAO" %>
<%@page import="util.Tools" %>
<%@page import="entity.TopicEntity" %>
<%@page import="dao.UserDAO" %>
<%@page import="entity.UserEntity" %>
<%@page import="util.Const" %>
<%@page import="java.util.*" %>

<%@page contentType="text/html; charset=UTF-8" %>

<%
    int status = Const.STATUS_OK;
    String msg = Const.STATUS_OK_MSG;
    TopicEntity[] topicEntitys = null;
    boolean userIsExist = false;
    try {
        String username = request.getParameter("username");
        username = Tools.FileToUtf8(username);
        //999999不允许用户升级
        if ("999999".equals(username)) {
            status = Const.STATUS_WITHOUT_RELEASE;
            msg = Const.STATUS_WITHOUT_RELEASE_MSG;
        } else {
            status = Const.STATUS_OK;
            msg = Const.STATUS_OK_MSG;
        }

    } catch (Exception e) {//myPic = null;
        e.printStackTrace();

    } finally {
        //servlets/ApkUpdateServlet输出的中文也是乱码，android 上不乱码
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"status\":\"" + status + "\",");
        buffer.append("\"msg\":\"" + msg + "\",");
        if (status == Const.STATUS_OK) {
            String changeLog = "增加了二维码扫描功能\n修改了某些机型登录失败问题";
            buffer.append("\"version\":\"9.0\",");
            buffer.append("\"changeLog\":\"" + changeLog + "\",");
            buffer.append("\"apkUrl\":\"http://172.60.8.89:8080/allRunServer/v10.apk\"");
        }
        buffer.append("}");
        out.write(buffer.toString());
    }
%>
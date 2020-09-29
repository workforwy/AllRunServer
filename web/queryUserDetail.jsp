<%@page import="   dao.UserDAO" %>
<%@page import="   entity.UserEntity" %>
<%@page import="   util.Const" %>
<%@page import="   util.Tools" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.*" %>
<%
    int status = Const.STATUS_OK;
    String msg = Const.STATUS_OK_MSG;
    UserEntity userEntity = null;
    boolean userIsExist = false;
    try {

        String username = request.getParameter("username");
        String md5password = request.getParameter("md5password");
        username = Tools.FileToUtf8(username);

        md5password = Tools.FileToUtf8(md5password);
        UserDAO userDAO = new UserDAO();
        userIsExist = userDAO.checkUserIsExist(username, md5password);
        if (!userIsExist) {
            status = Const.STATUS_LOGIN_ERROR;
            msg = Const.STATUS_LOGIN_ERROR_MSG;
        } else {
            userEntity = userDAO.queryDetailByUsername(username, md5password);
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
            buffer.append("\"data\":");
            buffer.append("{");
            buffer.append("\"id\":" + userEntity.getId() + ",");
            buffer.append("\"username\":\""
                    + userEntity.getUsername() + "\",");
            buffer.append("\"md5password\":\""
                    + userEntity.getMd5password() + "\",");
            buffer.append("\"nickname\":\""
                    + userEntity.getNickname() + "\",");
            buffer.append("\"gender\":\"" + userEntity.getGender()
                    + "\",");
            buffer.append("\"iconUrl\":\""
                    + userEntity.getIconUrl() + "\",");
            buffer.append("\"latitude\":"
                    + userEntity.getLatitude() + ",");
            buffer.append("\"longitude\":"
                    + userEntity.getLongitude() + ",");
            buffer.append("\"intro\":\"" + userEntity.getIntro()
                    + "\",");
            buffer.append("\"regTime\":" + userEntity.getRegTime());
            buffer.append("}");
        }
        buffer.append("}");
        out.write(buffer.toString());
    }
%>
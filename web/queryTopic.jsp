<%@page import="   dao.TopicDAO" %>
<%@page import="   entity.TopicEntity" %>
<%@page import="   dao.UserDAO" %>
<%@page import="   entity.UserEntity" %>
<%@page import="   util.Const" %>
<%@page import="   util.Tools" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.*" %>
<%
    int status = Const.STATUS_OK;
    String msg = Const.STATUS_OK_MSG;
    TopicEntity[] topicEntitys = null;
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
            TopicDAO topicDAO = new TopicDAO();
            topicEntitys = topicDAO.queryAll();
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
            buffer.append("\"data\":[");
            for (int i = 0; i < topicEntitys.length; i++) {
                TopicEntity topicEntity = topicEntitys[i];
                buffer.append("{");
                buffer.append("\"id\":" + topicEntity.getId() + ",");
                buffer.append("\"username\":\"" + topicEntity.getUsername() + "\",");
                buffer.append("\"content\":\"" + topicEntity.getContent() + "\",");
                buffer.append("\"imageUrl\":\"" + topicEntity.getImageUrl() + "\",");
                buffer.append("\"latitude\":" + topicEntity.getLatitude() + ",");
                buffer.append("\"longitude\":" + topicEntity.getLongitude() + ",");
                buffer.append("\"address\":\"" + topicEntity.getAddress() + "\",");
                buffer.append("\"createTime \":" + topicEntity.getCreateTime());
                buffer.append("}");
                if (i < topicEntitys.length - 1) {
                    buffer.append(",");
                }
            }
            buffer.append("]");
        }
        buffer.append("}");
        out.write(buffer.toString());
    }
%>
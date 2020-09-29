<%@page import="   dao.UserDAO" %>
<%@page import="   entity.UserEntity" %>
<%@page import="   util.Const" %>
<%@page import="   util.Tools" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.*" %>
<%
    int status = Const.STATUS_OK;
    String msg = Const.STATUS_OK_MSG;
    UserEntity[] userEntitys = null;
    boolean userIsExist = false;
    try {

        String username = request.getParameter("username");
        String md5password = request.getParameter("md5password");
        String strPageIndex = request.getParameter("pageIndex");
        String strRowNum = request.getParameter("rowNum");

        username = Tools.FileToUtf8(username);

        md5password = Tools.FileToUtf8(md5password);
        UserDAO userDAO = new UserDAO();
        userIsExist = userDAO.checkUserIsExist(username, md5password);
        if (!userIsExist) {
            status = Const.STATUS_LOGIN_ERROR;
            msg = Const.STATUS_LOGIN_ERROR_MSG;

        } else if (Tools.isNull(strPageIndex)
                || !Tools.isNum(strPageIndex)) {
            status = Const.STATUS_FAILURE;
            msg = "pageIndex参数为空或pageIndex不是数字";
        } else if (Tools.isNull(strRowNum) || !Tools.isNum(strRowNum)) {
            status = Const.STATUS_FAILURE;

            msg = "rowNum为空或rowNum不是数字";
        } else {
            int pageIndex = Integer.parseInt(strPageIndex);
            int rowNum = Integer.parseInt(strRowNum);
            userEntitys = userDAO.queryUserByPage(pageIndex, rowNum);
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
            for (int i = 0; i < userEntitys.length; i++) {
                UserEntity userEntity = userEntitys[i];
                buffer.append("{");
                buffer.append("\"id\":" + userEntity.getId() + ",");
                buffer.append("\"username\":\""
                        + userEntity.getUsername() + "\",");

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
                if (i < userEntitys.length - 1) {
                    buffer.append(",");
                }
            }
            buffer.append("]");
        }
        buffer.append("}");
        out.write(buffer.toString());
    }
%>
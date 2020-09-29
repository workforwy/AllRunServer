<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    try {
        //接收客户端传过来是一个form
        String word = request.getParameter("word");
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        //调biz,调dao访问Mysql
        //List<Entity>
        //for{ sb.append('')}
        sb.append("<data>");
        out
        if ("android".equals(word)) {
            sb.append("<mp3Url>android.mp3</mp3Url>");
        } else {
            sb.append("<mp3Url>other.mp3</mp3Url>");
        }
        sb.append("</data>");

        String xmlStr = sb.toString();
        if (xmlStr != null && !"".equals(xmlStr)) {
            response.setContentType("text/xml;charset=UTF-8");
            response.getWriter().write(xmlStr);
            response.flushBuffer();
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "inline;filename=2.xml");
        }
    } catch (Exception e) {

    } finally {

    }
%>



<%@ page import="util.Const" %>
<%@ page import="java.io.File" %>
<%@ page import="util.Tools" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="entity.TopicEntity" %>
<%@ page import="dao.TopicDAO" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    long st = 0, dbst = 0, dbet = 0, ent = 0;
    st = System.currentTimeMillis();

    request.setCharacterEncoding("UTF-8");
    boolean isHasImage = false;

    int status = Const.STATUS_OK;
    String msg = Const.STATUS_OK_MSG, iconUrl = "";

    try {
        String imageSaveRooot = "topicImage";
        String username = "", md5password = "", nickname = "", content = "", latitude = "", longitude = "", address = "";

        String path = application.getRealPath("/");
        path = path + imageSaveRooot;
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        final String allowExtNames = "gif,jpeg,jpg,png,GIF,JPEG,JPG,PNG";
        long sizeMax = 1024 * 1024 * 10;//设置文件的大小为2M

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setSizeMax(sizeMax);

        List items = sfu.parseRequest(request);
        for (int i = 0; i < items.size(); i++) {
            FileItem item = (FileItem) items.get(i);
            if (!item.isFormField()) {
                String fileName = item.getName();
                String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (allowExtNames.indexOf(extName) != -1) {
                    //out.println("fileName=" + fileName);
                    iconUrl = imageSaveRooot + "/" + fileName;
                    item.write(new File(application.getRealPath("/") + iconUrl));
                    isHasImage = true;
                }
            } else {
                String fieldName = item.getFieldName();
                String vlaue = item.getString();
                //out.println(fieldName + " : " + vlaue);
                if ("username".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    username = vlaue;
                }
                if ("md5password".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    md5password = vlaue;
                }
                if ("content".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    content = vlaue;
                }
                if ("address".equals(fieldName)) {
                    //vlaue = Tools.ToUtf8(vlaue);
                    address = vlaue;
                }
                if ("latitude".equals(fieldName)) {
                    latitude = vlaue;
                }
                if ("longitude".equals(fieldName)) {
                    longitude = vlaue;
                }


            }
        }

        if (status == Const.STATUS_OK) {


            if (Tools.isNull(latitude)) {
                latitude = "0.000000";
            }

            if (Tools.isNull(longitude)) {
                longitude = "0.000000";
            }
            iconUrl = "/allRunServer/" + iconUrl;

            long createTime = System.currentTimeMillis();
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setUsername(username);
            topicEntity.setContent(content);
            topicEntity.setImageUrl(iconUrl);
            topicEntity.setAddress(address);
            topicEntity.setLatitude(Double.parseDouble(latitude));
            topicEntity.setLongitude(Double.parseDouble(longitude));
            topicEntity.setCreateTime(createTime);
            dbst = System.currentTimeMillis();

            TopicDAO topicDAO = new TopicDAO();
            topicDAO.addTopic(topicEntity);
            dbet = System.currentTimeMillis();
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (!isHasImage) {
            status = Const.STATUS_NO_FILE;
            msg = Const.STATUS_NO_FILE_MSG;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"status\":\"" + status + "\",");
        buffer.append("\"msg\":\"" + msg + "\",");
        if (status == Const.STATUS_OK) {
            buffer.append("\"imageUrl\":\" " + iconUrl + "\"");
        }
        buffer.append("}");
        out.write(buffer.toString());
        ent = System.currentTimeMillis();
        //st=0,dbst=0,dbet=0,ent=0
        System.out.println("st=" + st);
        System.out.println("dbst=" + dbst);
        System.out.println("dbet=" + dbet);
        System.out.println("ent=" + ent);
        out.isAutoFlush();
    }
%>
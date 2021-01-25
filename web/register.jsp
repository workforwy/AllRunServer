<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="org.apache.commons.fileupload.FileItem" %>
<%@page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>

<%@page import="   util.Tools" %>
<%@page import="   dao.UserDAO" %>
<%@page import="   entity.UserEntity" %>
<%@page import="   util.Const" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>

<%
    long st = 0, dbst = 0, dbet = 0, ent = 0;
    st = System.currentTimeMillis();
    request.setCharacterEncoding("UTF-8");

    int status ;
    String msg = Const.STATUS_OK_MSG, iconUrl = "";
    boolean isHasImage = false;
    try {
        String imageSaveRooot = "userIcon";
        String username = "", md5password = "", nickname = "";
        String gender = "";
        String latitude = "";
        String longitude = "";
        String intro = "";

        String path = application.getRealPath("/");
        path = path + imageSaveRooot;
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        filePath = null;

        final String allowExtNames = "gif,jpeg,jpg,png,GIF,JPEG,JPG,PNG";
        long sizeMax = 1024 * 1024 * 10;//设置文件的大小为2M

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setSizeMax(sizeMax);

        List<FileItem> items = sfu.parseRequest(request);
        for (int i = 0; i < items.size(); i++) {
            FileItem item = items.get(i);
            if (!item.isFormField()) {
                String fileName = item.getName();
                String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (allowExtNames.contains(extName)) {
                    iconUrl = imageSaveRooot + "/" + fileName;
                    item.write(new File(application.getRealPath("/") + iconUrl));
                    isHasImage = true;
                }
            } else {
                String fieldName = item.getFieldName();
                String vlaue = item.getString();
                if ("username".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    username = vlaue;
                }

                if ("md5password".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    md5password = vlaue;
                }
                if ("nickname".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    nickname = vlaue;
                }
                if ("gender".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    gender = vlaue;
                }
                if ("intro".equals(fieldName)) {
                    vlaue = Tools.ToUtf8(vlaue);
                    intro = vlaue;
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

            long regTime = System.currentTimeMillis();
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setMd5password(md5password);
            userEntity.setNickname(nickname);
            userEntity.setGender(gender);

            userEntity.setIconUrl(iconUrl);
            userEntity.setLatitude(Double.parseDouble(latitude));
            userEntity.setLongitude(Double.parseDouble(longitude));
            userEntity.setIntro(intro);
            userEntity.setRegTime(regTime);
            dbst = System.currentTimeMillis();

            UserDAO userDAO = new UserDAO();
            userDAO.register(userEntity);
            dbet = System.currentTimeMillis();
        }

    } catch (Exception e) {//myPic = null;
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
            buffer.append("\"iconUrl\":\" " + iconUrl + "\"");
        }
        buffer.append("}");
        out.write(buffer.toString());
        ent = System.currentTimeMillis();
        //st=0,dbst=0,dbet=0,ent=0
        System.out.println("st=" + st);
        System.out.println("dbst=" + dbst);
        System.out.println("dbet=" + dbet);
        System.out.println("ent=" + ent);


    }
%>
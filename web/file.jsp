<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="com.great.fileUpload.upBean"%>
<%@ page language="java" import="com.great.fileUpload.files"%>
<%@ page language="java" import="com.great.fileUpload.file"%>
<%@page import="java.util.*"%>
<%@ page import="java.io.*"%>

<%
	String status = "1", msg = "failure";
	try {
%>

<jsp:useBean id="myUpload" scope="page" class="com.great.fileUpload.upBean" />

<%
	myUpload.initialize(pageContext);
		myUpload.setAllowedExtList("txt,gif,jpeg,jpg,png"); // set supported extends file name	
		myUpload.setIsCover(true); // cover the same name file
		myUpload.upload(); // get all upload file data

		files myFiles = myUpload.getFiles();

		int nFileCount = myFiles.getCount();
		if (nFileCount < 1) {
			msg = "no data";
		} else {
			//c://tomcat/webapps/1310server
			String path = application.getRealPath("/");
			path = path + "userImages";
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			filePath = null;
			//out.print(path);
			//path不能有中文，或太长
			//out.print("path=" + path+"</br>");
			myUpload.setRealPath(path);
			com.great.fileUpload.file file = myFiles.getFile(0);
			if (file.getSize() > 1024 * 1024 * 10) {
				msg = "file size must low 10MB,your file size is" + file.getSize();

			} else {
				file.saveAs();
				String fileName = file.getName();
				status = "0";
				msg = "/tlbsServer/userImages/" + fileName;
			}
		}
	} catch (Exception e) {//myPic = null;
		e.printStackTrace();
	} finally {
		out.println("{'status':"+status+",'msg':"+msg+"}");
	}
%>

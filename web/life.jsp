<%@ page import="java.util.Date" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>jsp 生命周期</title>
</head>
<body>

<%!
    private int initVar = 0;
    private int serviceVar = 0;
    private int destroyVar = 0;
%>

<%!
    public void jspInit() {
        initVar++;
        System.out.println("jspInit(): JSP被初始化了" + initVar + "次");
    }

    public void jspDestroy() {
        destroyVar++;
        System.out.println("jspDestroy(): JSP被销毁了" + destroyVar + "次");
    }
%>

<%
    serviceVar++;
    System.out.println("_jspService(): JSP共响应了" + serviceVar + "次请求");

    String content1 = "初始化次数 : " + initVar;
    String content2 = "响应客户请求次数 : " + serviceVar;
    String content3 = "销毁次数 : " + destroyVar;

%>



<%
    out.println("你的 IP 地址 " + request.getLocalAddr());
%>
<%! int day = 3; %>

<h1>菜鸟教程 JSP 测试实例</h1>
<%--注释部分--%>
<p>
    <%=content1 %>
</p>
<p>
    <%=content2 %>
</p>
<p>
    <%=content3 %>
</p>

<p>
    今天的日期是: <%= (new Date()).toString()%>
</p>

<% if (day == 1 | day == 7) { %>
<p>今天是周末</p>
<% } else { %>
<p>今天不是周末</p>
<% } %>

<%! int fontSize; %>
<%for (fontSize = 1; fontSize <= 3; fontSize++) { %>
<font color="green" size="<%= fontSize %>">
    菜鸟教程
</font><br/>
<%}%>

<%while (fontSize <= 5) { %>
<font color="red" size="<%= fontSize %>">
    菜鸟教程
</font><br/>
<%fontSize++;%>
<%}%>

<table width="100%" border="1" align="center">
    <tr bgcolor="#949494">
        <th>Header Name</th><th>Header Value(s)</th>
    </tr>
    <%
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String paramName = (String)headerNames.nextElement();
            out.print("<tr><td>" + paramName + "</td>\n");
            String paramValue = request.getHeader(paramName);
            out.println("<td> " + paramValue + "</td></tr>\n");
        }
    %>
</table>

<%
    // 设置每隔5秒自动刷新
//    response.setIntHeader("Refresh", 5);
    // 获取当前时间
    Calendar calendar = new GregorianCalendar();
    String am_pm;
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    if(calendar.get(Calendar.AM_PM) == 0)
        am_pm = "AM";
    else
        am_pm = "PM";
    String CT = hour+":"+ minute +":"+ second +" "+ am_pm;
    out.println("当前时间: " + CT + "\n");
%>
</body>
</html>

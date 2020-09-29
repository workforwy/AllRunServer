<?xml version="1.0" encoding="utf-8"?>
<%@page language="java" contentType="text/xml;charset=utf-8" pageEncoding="utf-8" import="java.util.*" %>
<%@ page import="entity.Flight" %>
<%
    //编辑数据,模拟航班数据
    List<Flight> flights = new ArrayList<Flight>();
    flights.add(new Flight(1, "No.1", "北京", "上海", "2016-1-1", 2000));
    flights.add(new Flight(2, "No.2", "北京", "上海", "2016-1-1", 2000));
    flights.add(new Flight(3, "No.3", "北京", "上海", "2016-1-1", 2000));
    flights.add(new Flight(4, "No.4", "北京", "上海", "2016-1-3", 2000));
    flights.add(new Flight(5, "No.5", "北京", "上海", "2016-1-3", 2000));
    flights.add(new Flight(6, "No.6", "北京", "上海", "2016-1-4", 2000));
    flights.add(new Flight(7, "No.7", "北京", "上海", "2016-1-4", 2000));
    flights.add(new Flight(8, "No.8", "北京", "上海", "2016-1-7", 2000));
    flights.add(new Flight(9, "No.9", "北京", "上海", "2016-1-7", 2000));
    flights.add(new Flight(10, "No.10", "上海", "北京", "2016-1-9", 2000));
%>
<flights>
    <%
        //通过请求参数名获得参数值
        String date = request.getParameter("date");
        String number = request.getParameter("number");
        String from = request.getParameter("from");

        for (int i = 0; i < flights.size(); i++) {
            Flight f = flights.get(i);
            if (date != null && !f.getData().equals(date)) {
                continue;
            }
            if (from != null && !f.getFrom().equals(from)) {
                continue;
            }
    %>

    <flight>
        <id><%=f.getId()%></id>
        <number><%=f.getNumber()%></number>
        <from><%=f.getFrom()%></from>
        <to><%=f.getTo()%></to>
        <date><%=f.getData()%></date>
        <price><%=f.getPrice()%></price>
    </flight>

    <%
        }
    %>
</flights>

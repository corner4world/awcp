<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="BP.DTS.DoType"%>
<%@page import="org.jflow.framework.common.model.DoPortModel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String PK=request.getParameter("PK");
	System.out.print(PK);
	
	String EnName=request.getParameter("EnName");
	
	
	String Lang=request.getParameter("Lang");
	
	
	
	
	String DoType=request.getParameter("DoType");
	
	DoPortModel dpm=new DoPortModel(request,response,EnName,PK,Lang,DoType,basePath);
	
	dpm.init();
%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>功能入口</title>
</head>
<body>
    <form id="form1" runat="server">
    <%=dpm.Pub1.toString() %>
    
    </form>
    </body>
</html>
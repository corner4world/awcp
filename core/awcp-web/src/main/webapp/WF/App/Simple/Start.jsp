<%@page import="BP.WF.Glo"%>
<%@page import="BP.DA.DataRow"%>
<%@page import="BP.Port.WebUser"%>
<%@page import="BP.DA.DataTable"%>
<%@page import="BP.WF.Dev2Interface"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发起</title>
</head>
<body>
	<h2>发起</h2>
	<%
   //获取可以发起的流程集合。
   DataTable dt = Dev2Interface.DB_GenerCanStartFlowsOfDataTable(WebUser.getNo());
	
   // 输出集合.
   %>
   <table border="1">
   <tr>
   <th>类别</th>
   <th>流程</th>
   </tr>
     <%
     int size = dt.Rows.size();
     for(int i = 0; i<size; i++){
    	 DataRow dr = dt.Rows.get(i);
        %>
        <tr>
        <td><%=dr.getValue("FK_FlowSortText") %></td>

        <td> <a target=_blank href='<%=Glo.getCCFlowAppPath() %>WF/MyFlow.jsp?FK_Flow=<%=dr.getValue("No") %>' ><%=dr.getValue("Name") %> </a>  </td>
        </tr>
   <% } %> 
   </table>
</body>
</html>
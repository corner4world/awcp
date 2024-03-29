<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sc" uri="szcloud"%>
<%@page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>组内用户</title>

<%@ include file="/resources/include/common_css.jsp"%><!-- 注意加载路径 -->

</head>
<body id="main">

		<div class="container-fluid">

			<!-- <div class="row" id="breadcrumb">
				<ul class="breadcrumb">
					<li><i class="icon-location-arrow icon-muted"></i></li>
					<li><a >首页</a></li>
					<li><a >组织机构与权限</a></li>
					<li class="active">选择需要添加的用户</li>
				</ul>
			</div> -->
			
			<div class="row" id="buttons">
				<button type="button" class="btn btn-success" id="addBtn">
					<i class="icon-plus-sign"></i>确定
				</button>
				<button type="button" class="btn btn-info" id="cancelBtn">
					<i class="icon-trash"></i>取消
				</button>
				<button type="button" class="btn btn-info" id="searchBtn"
					data-toggle="collapse" data-target="#collapseButton">
					<i class="icon-search"></i>
				</button>
			</div>
			
			<div class="row" id="searchform">
				<div id="collapseButton" class="collapse">
					<form action="<%=basePath%>dev/punDevGroupNotIncludeSearch.do"
						id="createForm">
						<input type="hidden" name="currentPage" value="0" />
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon">用戶名</span> <input name="account"
									class="form-control" id="account" type="text" />
							</div>
						</div>
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon">姓名</span> <input name="name"
									class="form-control" id="name" type="text" />
							</div>
						</div>
						<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon">身份证号</span> <input
									name="identityNumber" class="form-control"
									id="identityNumber" type="text" />
							</div>
						</div>
						<div class="col-md-3 btn-group">
							<button class="btn btn-primary" type="submit">提交</button>
							<a class="btn" data-toggle="collapse"
								data-target="#collapseButton">取消</a>
						</div>
					</form>
				</div>
			</div>
	
			<form method="post" id="userList">
				<input type="hidden" name="groupId" value="${group.id}">
				<div class="row" id="datatable">
					<table class="table datatable table-bordered">
						<thead>
							<tr>
								<th class="hidden"></th>
								<th>用户名</th>
								<th>姓名</th>
								<th>身份证号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${vos}" var="vo">
								<tr>
									<td class="hidden formData"><input id="boxs" name="id" value="${vo.id}"></td>
									<td>${vo.account}</td>
									<td>${vo.name}</td>
									<td>${vo.identityNumber}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
			<div class="row navbar-fixed-bottom text-center" id="pagers">
				<sc:PageNavigation dpName="vos"></sc:PageNavigation>
			</div>
		</div>
		<%@ include file="/resources/include/common_js.jsp"%>
		<script type="text/javascript" src="<%=basePath%>resources/scripts/pageTurn.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/scripts/jquery.serializejson.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/scripts/common.js"></script>
		<script type="text/javascript">
			$(function(){
				  var count=0;//默认选择行数为0
				  $('table.datatable').datatable({
					  checkable: true,
					  checksChanged:function(event){
						  this.$table.find("tbody tr").find("input#boxs").removeAttr("name");
						  var checkArray = event.checks.checks;
						  count = checkArray.length;//checkbox checked数量
						  for(var i=0;i<count;i++){//给隐藏数据机上name属性
							  this.$table.find("tbody tr").eq(checkArray[i]).find("input#boxs").attr("name","boxs");
						  }
					  }
				  });
				  $("#addBtn").click(function(){
						if(count<1){
		          			alertMessage("请至少选择一项进行操作");
		          			return false;
		          		}
						//ajax提交
						$.post("<%=basePath%>dev/punDevGroupAddUser.do",$("#userList").serialize(),function(o){
							if(o.result == "1"){
								alertMessage(o.msg);
								try {
									var dialog = top.dialog.get(window);
								} catch (e) {
									return;
								}
								dialog.close(o.result).remove();
								//dialog.remove();
								
							} else if(o.result == "2"){
								alertMessage(o.msg);
							} else if(o.result == "3"){
								alertMessage(o.msg);
								//返回到主页面，让主页面跳转到grouplist页面
							}
						});
		      			return false;
		      	  })
				  $("#cancelBtn").click(function(){
					  	try {
							var dialog = top.dialog.get(window);
						} catch (e) {
							return;
						}
						dialog.remove();
		      			return false;
		      	  })
		      	  
			})
		</script>


</body>
</html>

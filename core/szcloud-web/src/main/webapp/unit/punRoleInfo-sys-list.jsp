<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sc" uri="szcloud"%>
<%@page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>用户管理</title>
<%@ include file="/resources/include/common_css.jsp"%><!-- 注意加载路径 -->
</head>
<body id="main">
	<div class="container-fluid">
<!-- 		<div class="row" id="breadcrumb"> -->
<!-- 			<ul class="breadcrumb"> -->
<!-- 				<li><i class="icon-location-arrow icon-muted"></i></li> -->
<!-- 				<li><a href="#">系统管理</a></li> -->
<!-- 				<li class="active">系统角色管理</li> -->
<!-- 			</ul> -->
<!-- 		</div> -->

		<div class="row" id="buttons">
			<button type="button" class="btn btn-success" id="addBtn">
				<i class="icon-plus-sign"></i>新增
			</button>
			<button type="button" class="btn btn-info" id="deleteBtn">
				<i class="icon-trash"></i>删除
			</button>
			<button type="button" class="btn btn-warning" id="updateBtn">
				<i class="icon-edit"></i>修改
			</button>
			<button type="button" class="btn btn-danger" id="accessBtn">
				<i class="icon-edit"></i>授权
			</button>
			<button type="button" class="btn btn-info" id="searchBtn"
				data-toggle="collapse" data-target="#collapseButton">
				<i class="icon-search"></i>
			</button>
		</div>

		<div class="row" id="searchform">
			<div id="collapseButton" class="collapse">
				<form action="<%=basePath%>unit/listRolesInSys.do" id="createForm">
					<input type="hidden" name="currentPage" value="0" />
					<input type="hidden" name="boxs" value="${sysId}"/>
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon">角色名</span> <input name="roleName"
								class="form-control" id="roleName" type="text"
								value="${vo.roleName}" />
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

		<div class="row" id="datatable">
			<form method="post" id="userList">
				<input type="hidden" name="currentPage" value="${currentPage}">
				<input type="hidden" name="sysId" value="${sysId}">
				<table class="table datatable table-bordered">
					<thead>
						<tr>
							<th class="hidden"></th>
							<th>名称</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${vos}" var="vo">
							<tr>
								<td class="hidden formData"><input id="boxs" type="name"
									value="${vo.roleId}"></td>
								<td>${vo.roleName}</td>
								<td>${vo.dictRemark}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div class="row navbar-fixed-bottom text-center" id="pagers">
			<sc:PageNavigation dpName="vos"></sc:PageNavigation>
		</div>
	</div>

	<%@ include file="../resources/include/common_js.jsp"%>
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
	          	//新增
	      		$("#addBtn").click(function(){
<%-- 	      			$("#userList").attr("action","<%=basePath%>unit/intoPunRoleInfo.do").submit(); --%>
	      			window.location="<%=basePath%>unit/intoPunRoleInfo.do";
	      			return false;
	      		});
	      		
	      		//update
	      		$("#updateBtn").click(function(){
	      			if(count == 1){
	      				$("#userList").attr("action","<%=basePath%>unit/editRoleInSys.do").submit();
	      			}else{
	      				alert("请选择一项进行操作");
	      			}
	      			return false;
	      		});
	      		
	          	//delete
	          	$("#deleteBtn").click(function(){
	          		if(count<1){
	          			alertMessage("请至少选择一项进行操作");
	          			return false;
	          		}
	          		if(window.confirm("确定删除？")){
	          			$("#userList").attr("action","<%=basePath%>unit/delRoleInSys.do").submit();
	          		}
	      			return false;
	          	});
	          	
	        	//updata Access
	        	$("#accessBtn").click(function(){
	      			if(count == 1){
	      				$("#userList").attr("action","<%=basePath%>unit/punRoleMenuAccessEdit.do").submit();
	      			}else{
	      				alert("请选择一项进行操作");
	      			}
	      			return false;
	      		});
		  })
	</script>
</body>
</html>

<%@page import="java.text.DecimalFormat"%>
<%@page import="BP.En.QueryObject"%>
<%@page import="BP.En.FieldTypeS"%>
<%@page import="BP.En.Attr"%>
<%@page import="BP.En.EntitiesNoName"%>
<%@page import="BP.En.EntityNoName"%>
<%@page import="BP.En.Attrs"%>
<%@page import="BP.En.UIContralType"%>
<%@page import="BP.WF.Node"%>
<%@page import="java.util.*"%>
<%@page import="BP.DA.*"%>
<%@page import="BP.WF.*"%>
<%@page import="BP.Sys.*"%>
<%@page import="BP.Port.*"%>
<%@page import="BP.Tools.StringHelper"%>
<%@page import="cn.jflow.common.model.DtlControlModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath;
	String ensName; 
	String refPKVal; 
	String fId; 
	String fkNode; 
	String isWap;
	String requestParas; 
	String FK_MapData;
	StringBuffer dtlCtrl = null;
	int isReadonly = 0;
	String rowCount;
	String AddRowNum;
	String path = request.getContextPath();
	basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";

	// 明细表
	ensName = request.getParameter("EnsName");
	// workId
	refPKVal = request.getParameter("RefPKVal");
	// 是否可读
	if(!StringHelper.isNullOrEmpty(request.getParameter("IsReadonly"))){
		String isRead = request.getParameter("IsReadonly");
		if(isRead.equals("false")){
			isReadonly = 0;
		}else if(isRead.equals("true")){
			isReadonly = 1;
		}else{
			isReadonly = Integer.parseInt(isRead);
		}
	}
	rowCount = request.getParameter("rowCount");
	AddRowNum = request.getParameter("AddRowNum");
	fId = request.getParameter("FID");
	fkNode = request.getParameter("FK_Node");
	isWap = request.getParameter("IsWap");
	
	if(null == isWap){
		isWap = "0";
	}
	// 拼接重定向参数
	requestParas = "&EnsName="+ensName+"&RefPKVal="+refPKVal+"&IsReadonly="+isReadonly+"&FID="+fId+"&FK_Node="+fkNode+"";
	DtlControlModel dm = new DtlControlModel(request,response);
	dm.init();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
     <meta http-equiv="Page-Enter" content="revealTrans(duration=0.5, transition=8)" />
    <title></title>
    <script src="<%=basePath%>WF/Scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>WF/Scripts/easyUI/jquery.easyui.min.js" type="text/javascript"></script>
    <link href="<%=basePath%>WF/Scripts/easyUI/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>WF/Scripts/easyUI/themes/icon.css" rel="stylesheet" type="text/css" />  
    <script src="<%=basePath%>WF/Comm/JS/Calendar/WdatePicker.js" type="text/javascript"></script>
    <link href="<%=basePath%>WF/Style/FormThemes/Table0.css" rel="stylesheet" type="text/css" />
 	<script  src="<%=basePath%>WF/CCForm/MapExt.js"  type='text/javascript' ></script>

<script type="text/javascript">
 
        var isChange = false;
        function SaveDtlData() {
            if (isChange == false)
                return;
            var btn = document.getElementById('Button1');
            btn.click();
            isChange = false;
        }
        function SaveDtlDataTo(url) {

            if (isChange == true) {
                alert('请先执行保存,在退出.');
                return true;
            }
            SaveDtlData();
            window.location.href = url;
        }

        function TROver(ctrl) {
            ctrl.style.backgroundColor = 'LightSteelBlue';
        }

        function TROut(ctrl) {
            ctrl.style.backgroundColor = 'white';
        }

        function Del(id, ens, refPk, pageIdx) {
            if (window.confirm('您确定要执行删除吗？') == false)
                return;

            var url = "<%=basePath%>WF/CCForm/Do.do?DoType=DelDtl&OID=" + id + "&EnsName=" + ens;
            //		        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 400px; dialogWidth: 600px;center: yes; help: no');

            $.post(url, null, function () {

                window.location.href = 'Dtl2.jsp?EnsName=' + ens + '&RefPKVal=' + refPk + '&PageIdx=' + pageIdx;

            }
		        );
        }
        function SetChange(value) {

        	isChange = value;
        }
        /* function DtlOpt(workId, fk_mapdtl) {
            //var url = 'DtlOpt.jsp?WorkID=' + workId + '&FK_MapDtl=' + fk_mapdtl;
            //var b = window.showModalDialog(url, 'ass', 'dialogHeight: 400px; dialogWidth: 600px;center: yes; help: no');
            //window.location.href = 'Dtl2.jsp?EnsName=' + fk_mapdtl + '&RefPKVal=' + workId;
        } */
        function DtlOpt(workId, fk_mapdtl) {
            var url = 'DtlOpt.jsp?WorkID=' + workId + '&FK_MapDtl=' + fk_mapdtl;
            var b = window.showModalDialog(url, 'ass', 'dialogHeight: 400px; dialogWidth: 600px;center: yes; help: no');
            window.location.href = 'Dtl2.jsp?EnsName=' + fk_mapdtl + '&RefPKVal=' + workId;
        }
    </script>
   <%-- <style type="text/css">
        .HBtn
        {
            width: 1px;
            height: 1px;
            display: none;
        }
    </style>--%>
    <script language="JavaScript" src="../Comm/JScript.js"></script>
    <script language="JavaScript" src="../Comm/JS/Calendar/WdatePicker.js" defer="defer"></script>
    <script src="MapExt.js" type="text/javascript"></script>
    <script type="text/javascript">
        function SetVal() {
            // document.getElementById('KVs').value = this.GenerPageKVs();
            //  kvs = this.GenerPageKVs();
        }
    </script>
    <script language="javascript" type="text/javascript">
        // row主键信息 .
        var rowPK = null;
        // ccform 为开发者提供的内置函数.
        // 获取DDL值.
        function ReqDDL(ddlID) {
            var v = document.getElementById('DDL_' + ddlID + "_" + rowPK).value;
            if (v == null) {
                alert('没有找到ID=' + ddlID + '的下拉框控件.');
            }
            return v;
        }
        // 获取TB值
        function ReqTB(tbID) {
            var v = document.getElementById('TB_' + tbID + "_" + rowPK).value;
            if (v == null) {
                alert('没有找到ID=' + tbID + '的文本框控件.');
            }
            return v;
        }
        // 获取CheckBox值
        function ReqCB(cbID) {
            var v = document.getElementById('CB_' + cbID + "_" + rowPK).value;
            if (v == null) {
                alert('没有找到ID=' + cbID + '的单选控件.');
            }
            return v;
        }

        /// 获取DDL Obj
        function ReqDDLObj(ddlID) {
            var v = document.getElementById('DDL_' + ddlID + "_" + rowPK);
            if (v == null) {
                alert('没有找到ID=' + ddlID + '的下拉框控件.');
            }
            return v;
        }
        // 获取TB Obj
        function ReqTBObj(tbID) {
            var v = document.getElementById('TB_' + tbID + "_" + rowPK);
            if (v == null) {
                alert('没有找到ID=' + tbID + '的文本框控件.');
            }
            return v;
        }
        
        // 获取CheckBox Obj值
        function ReqCBObj(cbID) {
            var v = document.getElementById('CB_' + cbID + "_" + rowPK);
            if (v == null) {
                alert('没有找到ID=' + cbID + '的单选控件.');
            }
            return v;
        }
        
        function addRow(obj,url,rowCount)
        {

        	var index=obj.selectedIndex; //序号，取当前选中选项的序号 

        	var val = obj.options[index].text; 
        	var toal = eval(rowCount)+eval(val)
        	
        	window.location.href = url+"&AddRowNum="+val+"&rowCount="+toal;
        }

        // 设置控件值.
        function SetCtrlVal(ctrlID, val) {
            document.getElementById('TB_' + ctrlID + "_" + rowPK).value = val;
            document.getElementById('DDL_' + ctrlID + "_" + rowPK).value = val;
            document.getElementById('CB_' + ctrlID + "_" + rowPK).value = val;
        }
        
        function submitClick()
        {
        	document.getElementById("pData").value = document.forms[0].innerHTML;
        	return true;
        }
    </script>
</head>
<body onkeypress="Esc()" style="font-size: smaller;" class="easyui-layout" onblur="SetVal();"
    onload="SetVal();" topmargin="0" leftmargin="0">
    <form id="form1"  action="<%=basePath%>WF/CCForm/DtlSave.do?EnsName=<%=ensName%>&RefPKVal=<%=refPKVal%>&IsReadonly=<%=isReadonly %>&FID=<%=fId%>&FK_Node=<%=fkNode%>" method="post">
    <div id="mainPanle" region="center" border="false" style="position: fixed">
        <input type="submit" id="Button1"  Text=""  class="HBtn"  onclick="submitClick();"/>
         <input type="hidden"  id="pData"  name="pData"  value=""></input>
         <input type="hidden"  id="AddRowNum"  name="AddRowNum"  value="<%=AddRowNum %>"></input>
         <input type="hidden"  id="rowCount"  name="rowCount"  value="<%=rowCount %>"></input>
        <%=dm.Pub1.toString() %>
        <%=dm.Pub2.toString() %>
    </div>
    </form>
</body>
</html>

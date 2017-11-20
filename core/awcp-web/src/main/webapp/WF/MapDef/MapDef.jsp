<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/head/head.jsp"%>

<%
	String MyPK=request.getParameter("MyPK");
	if(StringHelper.isNullOrEmpty(MyPK)) {
		MyPK = request.getParameter("PK");
	}
	if(StringHelper.isNullOrEmpty(MyPK)) {
		MyPK = request.getParameter("FK_MapData");
	}
	if(StringHelper.isNullOrEmpty(MyPK)) {
		MyPK = "ND1601";
	}
	String FK_Flow = request.getParameter("FK_Flow")==null ? "":request.getParameter("FK_Flow");
	boolean IsEditMapData=request.getParameter("IsEditMapData") == null || request.getParameter("IsEditMapData") == "1" ? true:false;
	MapDefModel mapDefModel=new MapDefModel(basePath,request,response,MyPK,FK_Flow,IsEditMapData);
	mapDefModel.Page_Load();
%>
<%
	int script_size=mapDefModel.getScripts().size();
	for(int i=0;i<script_size;i++)
	{
		String script=mapDefModel.getScripts().get(i);
	%>
		<script type="text/javascript" src="<%=basePath+script%>"></script>
		<%
		}
		%>
<style type="text/css">
	body   
	{
	    font-size: .80em;
	    font-family: "Helvetica Neue", "Lucida Grande", "Segoe UI", Arial, Helvetica, Verdana, sans-serif;
	    margin: 0px;
	    padding: 0px;
	    color: #696969;
	}
</style>
 <script language="javascript"  >
        function FrmEvent(mypk) {
            var url = 'FrmEvent.jsp?FK_MapData=' + mypk;
            var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        }
	function HelpGroup()
	{
	   var msg='字段分组：就是把类似的字段放在一起，让用户操作更友好。\t\n比如：我们纳税人设计一个基础信息采集节点。';
	   msg+='在登记纳税人基础信息时，我们可以把基础信息、车船信息、房产信息、投资人信息分组。\t\n \t\n分组的格式为:@从字段名称1=分组名称1@从字段名称2=分组名称2 ,\t\n比如：节点信息设置，@NodeID=基本信息@LitData=考核信息。';
       alert( msg);
	}
	function DoGroupF( enName)
	{
	    var b=window.showModalDialog( 'GroupTitle.jsp?EnName='+enName , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
	}
	function Insert(mypk,IDX)
    {
        var url='Do.jsp?DoType=AddF&MyPK='+mypk+'&IDX=' +IDX ;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
	function AddF(mypk) {
        var url='Do.jsp?DoType=AddF&MyPK='+mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    function AddTable(mypk)
    {
        var url='EditCells.jsp?MyPK='+mypk;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    function MapExt(mypk) {
        var url = 'MapExt.jsp?FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 800px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function BatchEdit(mypk) {
        var url = 'BatchEdit.jsp?FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 800px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function MapDataEdit(mypk) {
        var url = 'EditMapData.jsp?FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 350px; dialogWidth: 500px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function CopyFieldFromNode(mypk) {
        var url = 'CopyFieldFromNode.jsp?DoType=AddF&FK_Node=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 700px; dialogWidth: 900px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function GroupFieldNew(mypk)
    {
        var url='GroupField.jsp?RefNo='+mypk+"&RefOID=0&DoType=FunList";
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 200px; dialogWidth: 600px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    function ExpImp(mypk, fk_flow) {
        var url = 'ExpImp.jsp?RefNo=' + mypk + "&RefOID=0&DoType=FunList&FK_Flow=" + fk_flow;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 400px; dialogWidth: 600px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function GroupField(mypk, OID )
    {
        var url='GroupField.jsp?RefNo='+mypk+"&RefOID="+OID ;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 200px; dialogWidth: 600px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    function GroupFieldDel(mypk,refoid)
    {
        var url='GroupField.jsp?RefNo='+mypk+'&DoType=DelIt&RefOID='+refoid ;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
   
    function Edit(mypk,refno, ftype)
    {
        var url='EditF.jsp?DoType=Edit&MyPK='+mypk+'&RefNo='+refno +'&FType=' + ftype;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    function EditEnum(mypk,refno)
    {
        var url='EditEnum.jsp?DoType=Edit&MyPK='+mypk+'&RefNo='+refno;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
     function EditTable(mypk,refno)
    {
        var url='EditTable.jsp?DoType=Edit&MyPK='+mypk+'&RefNo='+refno;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    
	function Up(mypk,refoid,idx,t) {
        var url='Do.jsp?DoType=Up&MyPK='+mypk+'&RefNo='+refoid+'&ToIdx='+idx+'&T='+t;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        //window.location.href ='MapDef.jsp?PK='+mypk+'&IsOpen=1';
        window.location.href = window.location.href ;
    }
    function Down(mypk,refoid,idx,t)
    {
        var url = 'Do.jsp?DoType=Down&MyPK=' + mypk + '&RefNo=' + refoid + '&ToIdx=' + idx + '&T=' + t;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    function GFDoUp(refoid)
    {
        var url='Do.jsp?DoType=GFDoUp&RefOID='+refoid ;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href ;
    }
    function GFDoDown(refoid)
    {
        var url='Do.jsp?DoType=GFDoDown&RefOID='+refoid ;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }

    function FrameDoUp(MyPK) {
        var url = 'Do.jsp?DoType=FrameDoUp&MyPK=' + MyPK;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function FrameDoDown(MyPK) {
        var url = 'Do.jsp?DoType=FrameDoDown&MyPK=' + MyPK;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        window.location.href = window.location.href;
    }
   
    function DtlDoUp(MyPK)
    {
        var url='Do.jsp?DoType=DtlDoUp&MyPK='+MyPK ;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href ;
    }
    function DtlDoDown(MyPK)
    {
        var url='Do.jsp?DoType=DtlDoDown&MyPK='+MyPK;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }

    function M2MDoUp(MyPK) {
        var url = 'Do.jsp?DoType=M2MDoUp&MyPK=' + MyPK;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function M2MDoDown(MyPK) {
        var url = 'Do.jsp?DoType=M2MDoDown&MyPK=' + MyPK;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        window.location.href = window.location.href;
    }

    function Del(mypk,refoid)
    {
        if (window.confirm('您确定要删除吗？') ==false)
            return ;
    
        var url='Do.jsp?DoType=Del&MyPK='+mypk+'&RefOID='+refoid;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
	function Esc()
    {
        if (event.keyCode == 27)     
        window.close();
       return true;
    }

    function GroupBarClick(rowIdx) {

        var alt = document.getElementById('Img' + rowIdx).alert;
        var sta = 'block';
        if (alt == 'Max') {
            sta = 'block';
            alt = 'Min';
        } else {
            sta = 'none';
            alt = 'Max';
        }

        document.getElementById('Img' + rowIdx).src = './Img/' + alt + '.gif';
        document.getElementById('Img' + rowIdx).alert = alt;
        var i = 0
        for (i = 0; i <= 40; i++) {
            if (document.getElementById(rowIdx + '_' + i) == null)
                continue;
            if (sta == 'block') {
                document.getElementById(rowIdx + '_' + i).style.display = '';
            } else {
                document.getElementById(rowIdx + '_' + i).style.display = sta;
            }
        }
    }

    var isInser = "";

    function CopyFieldFromNode(mypk) {
        var url = 'CopyFieldFromNode.jsp?FK_Node=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 700px; dialogWidth: 900px;center: yes; help: no');
        window.location.href = window.location.href;
    }

  function EditDtl(mypk, dtlKey) {
      var url = 'MapDtl.jsp?DoType=Edit&FK_MapData=' + mypk + '&FK_MapDtl=' + dtlKey;
      var b = window.showModalDialog(url, 'ass', 'dialogHeight: 600px; dialogWidth: 700px;center: yes; help:no;resizable:yes');
     // var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
      window.location.href = window.location.href;
  }

  function EditM2M(mypk, dtlKey) {
      var url = 'MapM2M.jsp?DoType=Edit&FK_MapData=' + mypk + '&NoOfObj=' + dtlKey;
      var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
      window.location.href = window.location.href;
  }
  
  function MapDtl( mypk  )
  {
      var url='MapDtl.jsp?DoType=DtlList&FK_MapData=' + mypk   ;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    /// 多选.
    function MapM2M(mypk) {
        var url = 'MapM2M.jsp?DoType=List&FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
        window.location.href = window.location.href;
    }

    function MapM2MM(mypk) {
        var url = 'MapM2MM.jsp?FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function EditAth(fk_mapdata, ath) {
        var url = 'Attachment.jsp?FK_MapData=' + fk_mapdata + '&Ath=' + ath;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function Ath(mypk) {
        var url = 'Attachment.jsp?DoType=List&FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
        window.location.href = window.location.href;
    }

    function AthDoUp(MyPK) {
        var url = 'Do.jsp?DoType=AthDoUp&MyPK=' + MyPK;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function AthDoDown(MyPK) {
        var url = 'Do.jsp?DoType=AthDoDown&MyPK=' + MyPK;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        window.location.href = window.location.href;
    }

    function EditFrame(mypk, dtlKey) {
        var url = 'MapFrame.jsp?DoType=Edit&FK_MapData=' + mypk + '&FK_MapFrame=' + dtlKey;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function MapFrame(mypk) {
        var url = 'MapFrame.jsp?DoType=DtlList&FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 600px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function HidAttr(fk_mapData) {
        var url = 'HidAttr.jsp?FK_MapData=' + fk_mapData;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function EnableAthM(fk_MapDtl) {
        var url = '../CCForm/AttachmentUpload.jsp?IsBTitle=1&PKVal=0&Ath=AthM&FK_MapData=' + fk_MapDtl + '&FK_FrmAttachment='+fk_MapDtl+'_AthM';
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
      //  window.location.href = window.location.href;
    }
    function Sln(fk_mapdata) {
        var url = 'Sln.jsp?IsBTitle=1&PKVal=0&Ath=AthM&FK_MapData=' + fk_mapdata + '&FK_FrmAttachment=' + fk_mapdata + '_AthM';
        WinOpen(url);
        //var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
    }
</script>
	<base target="_self" />
    <style type="text/css">
div#nv
{
    top: 0;
    left: 0;
    width: auto;
    text-align: center;
    padding: 0px;
}

div.wrapper
{
    padding: 3px;
    margin: 1px;
}
 
div#nv ul
{
    width: 100%;
    margin: 3;
    padding: 3;
    text-align: center;
}
div#nv li
{
    float: left;
    display: inline;
    list-style: none;
    margin: 0;
    padding: 0 6px;
    line-height: 1em;
    background-position: right center;
    background-repeat: no-repeat;
    margin: 3;
    padding: 3;
    text-align: center;
}

div#nv li.last
{
    background: none;
}

div#nv a,
div#nv a:link,
div#nv a:active,
div#nv a:visited {
  display: inline-block;
  /* hide from ie/mac \*/
  display: block;
  /* end hide */
  font-weight: bold;
  text-decoration: none;
  margin: 0;
}

div#nv a:hover, .S
{
    background-position: right center;
    background-repeat: no-repeat;
    margin: 3;
    padding: 3;
}

body
{
    font-size:small;
}
    </style>
</head>
<body>
<table width='100%' height='100%' align="center" >
	<tr>
		<td valign="top" align=left width='250px'  bgcolor="#cccccc">
			<%=mapDefModel.Left.toString() %>
		</td>
		
		 <td valign=top  style="width:900px" >
			<%=mapDefModel.Pub1.toString() %>
		</td>
	</tr>
</table>
</body>
</html>
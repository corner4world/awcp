<%@page import="BP.Sys.Frm.MapDtl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String fkMapData = request.getParameter("FK_MapData");
	String fkMapDtl = request.getParameter("FK_MapDtl");
%>
<%@ include file="/head/head1.jsp"%>
<script language="javascript">
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
    function CopyFieldFromNode(mypk)
    {
        var url='CopyFieldFromNode.jsp?DoType=AddF&FK_Node='+mypk;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 700px; dialogWidth: 900px;center: yes; help: no'); 
        window.location.href = window.location.href;
    }
    function HidAttr(mypk) {
        alert(mypk);
        var url = 'HidAttr.jsp?FK_MapData=' + mypk;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 700px; dialogWidth: 900px;center: yes; help: no');
        window.location.href = window.location.href;
    }
    function GroupFieldNew(mypk)
    {
        var url='GroupField.jsp?RefNo='+mypk+"&RefOID=0&DoType=FunList";
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 200px; dialogWidth: 600px;center: yes; help: no'); 
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
    
	function Up(mypk,refoid,idx)
    {
        var url='Do.jsp?DoType=Up&MyPK='+mypk+'&RefNo='+refoid+'&ToIdx='+idx;
        var b=window.showModalDialog( url , 'ass' ,'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no'); 
        //window.location.href ='MapDef.jsp?PK='+mypk+'&IsOpen=1';
        window.location.href = window.location.href ;
    }
    function Down(mypk, myfield, idx)
    {
        alert(mypk );
        var url='Do.jsp?DoType=Down&MyPK='+mypk+'&RefNo='+myfield +'&ToIdx='+idx;
        var b = window.showModalDialog(url, 'ass', 'dialogHeight: 500px; dialogWidth: 700px;center: yes; help: no');
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
     // var b = window.showModalDialog(url, 'ass', 'dialogHeight: 700px; dialogWidth: 800px;center: yes; help:no;resizable:yes');
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
    //然浏览器最大化.
    function ResizeWindow() {
        if (window.screen) {  //判断浏览器是否支持window.screen判断浏览器是否支持screen     
            var myw = screen.availWidth;   //定义一个myw，接受到当前全屏的宽     
            var myh = screen.availHeight;  //定义一个myw，接受到当前全屏的高     
            window.moveTo(0, 0);           //把window放在左上角     
            window.resizeTo(myw, myh);     //把当前窗体的长宽跳转为myw和myh     
        }
    }
    window.onload = ResizeWindow;
</script>
</head>
<body>
  <%
        MapDtl dtl = new MapDtl();
        dtl.setNo(fkMapDtl);

        if (dtl.RetrieveFromDBSources() == 0)
        {
            dtl.setFK_MapData(fkMapData);
            dtl.setName(fkMapData);
            dtl.Insert();
            dtl.IntMapAttrs();
        }
        String dtlNo = dtl.getNo();
        %>
		 <div class='easyui-layout' data-options='fit:true'>
        <div data-options="region:'north',noheader:true,split:false,border:false" style='height:30px;overflow-y:hidden'>
            <div style='float:left'>
                <a href="javascript:EditDtl('<%=fkMapData %>','<%=dtlNo %>')" class='easyui-linkbutton' data-options="iconCls:'icon-edit',plain:true"><%=dtl.getName() %></a>
            </div>
            <div style='float:right'>
                <a href="javascript:document.getElementById('F<%=dtlNo %>').contentWindow.AddF('<%=dtlNo %>');" class='easyui-linkbutton' data-options="iconCls:'icon-new',plain:true">插入列</a>
                <a href="javascript:document.getElementById('F<%=dtlNo %>').contentWindow.AddFGroup('<%=dtlNo %>');" class='easyui-linkbutton' data-options="iconCls:'icon-new',plain:true">插入列组</a>
                <a href="javascript:document.getElementById('F<%=dtlNo %>').contentWindow.CopyF('<%=dtlNo %>');" class='easyui-linkbutton' data-options="iconCls:'icon-add',plain:true">复制列</a>
                <a href="javascript:document.getElementById('F<%=dtlNo %>').contentWindow.HidAttr('<%=dtlNo %>');" class='easyui-linkbutton' data-options="iconCls:'icon-add',plain:true">隐藏列</a>
                <a href="javascript:document.getElementById('F<%=dtlNo %>').contentWindow.DtlMTR('<%=dtlNo %>');" class='easyui-linkbutton' data-options="iconCls:'icon-add',plain:true">多表头</a>
                <a href='Action.jsp?FK_MapData=<%=fkMapDtl %>' class='easyui-linkbutton' data-options="iconCls:'icon-add',plain:true">从表事件</a>
                <a href="javascript:DtlDoUp('<%=dtlNo %>')" class='easyui-linkbutton' data-options="iconCls:'icon-up',plain:true"></a>
                <a href="javascript:DtlDoDown('<%=dtlNo %>')" class='easyui-linkbutton' data-options="iconCls:'icon-down',plain:true"></a>
            </div>
            <div style='clear:both'></div>
        </div>
        <div data-options="region:'center',noheader:true,border:false" style="overflow-y:hidden">
            <iframe ID='F<%=dtlNo %>' frameborder='0' scrolling="auto" style='width:100%;height:100%' src='MapDtlDe.jsp?DoType=Edit&FK_MapData=<%=fkMapData %>&FK_MapDtl=<%=dtlNo %>'></iframe>
        </div>
    </div>
</body>
</html>
package BP.WF.HttpHandler;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HttpContext;

import BP.DA.DataRow;
import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.DA.Log;
import BP.En.QueryObject;
import BP.Port.Emp;
import BP.Sys.SysEnum;
import BP.Sys.SysEnumAttr;
import BP.Sys.SysEnums;
import BP.Sys.SystemConfig;
import BP.WF.DotNetToJavaStringHelper;
import BP.WF.Flow;
import BP.WF.FlowAppType;
import BP.WF.WFSta;
import BP.WF.Data.MyJoinFlows;
import BP.WF.Data.MyStartFlowAttr;
import BP.WF.Data.MyStartFlows;
import BP.WF.HttpHandler.Base.WebContralBase;
import BP.WF.Template.FlowSort;
import BP.WF.Template.FlowSorts;
import BP.Web.WebUser;

public class App extends WebContralBase{
	 /**
	  * 初始化函数
	  * @param mycontext
	  */
    public App(HttpContext mycontext)
    {
        this.context = mycontext;
    }
    
    public App()
    {
    }
    
    /**
     * 获得发起流程
     * @return
     */
    public String Start_Init()
    {
	    DataTable dt = BP.WF.Dev2Interface.DB_GenerCanStartFlowsOfDataTable(WebUser.getNo());
		return BP.Tools.Json.DataTableToJson(dt,false,false,true);
    }
    
    /**
     * 获得待办
     * @return 
     */
    public String Todolist_Init()
    {
    	String fk_node = this.GetRequestVal("FK_Node");
		DataTable dt = null;
		try {
			dt = BP.WF.Dev2Interface.DB_GenerEmpWorksOfDataTable(WebUser.getNo(), this.getFK_Node());
		} catch (Exception e) {
			Log.DebugWriteError("AppACEHandler Todolist_Init()"+e.getMessage());
			e.printStackTrace();
		}
		return BP.Tools.Json.DataTableToJson(dt,false,false,true);
    }
    
    /**
     * 运行
     *  @return 
     */
    public String Runing_Init()
    {
    	DataTable dt = null;
		dt = BP.WF.Dev2Interface.DB_GenerRuning();
		/*if (SystemConfig.getAppCenterDBType() == DBType.Oracle)
		{
			dt.Columns.get("WorkID").ColumnName = "WorkID";
		}*/
		return BP.Tools.Json.DataTableToJson(dt,false,false,true);
    }
    
    /**
     * 初始化赋值
     * @return
     */
    public String Home_Init()
    {
    	java.util.Hashtable ht = new java.util.Hashtable();
		ht.put("UserNo", BP.Web.WebUser.getNo());
		ht.put("UserName", BP.Web.WebUser.getName());

		//系统名称.
		ht.put("SysName", BP.Sys.SystemConfig.getSysName());


		ht.put("Todolist_EmpWorks", BP.WF.Dev2Interface.getTodolist_EmpWorks());
		ht.put("Todolist_Runing", BP.WF.Dev2Interface.getTodolist_Runing());
		ht.put("Todolist_Sharing", BP.WF.Dev2Interface.getTodolist_Sharing());
		ht.put("Todolist_Draft", BP.WF.Dev2Interface.getTodolist_Draft());
        //ht.put("Todolist_Apply", BP.WF.Dev2Interface.Todolist_Apply); //申请下来的任务个数.

		//我发起
		MyStartFlows myStartFlows = new MyStartFlows();
		QueryObject obj = new QueryObject(myStartFlows);
		obj.AddWhere(MyStartFlowAttr.Starter, WebUser.getNo());
		obj.addAnd();
		//运行中\已完成\挂起\退回\转发\加签\批处理\
		obj.addLeftBracket();
		obj.AddWhere("WFState=2 or WFState=3 or WFState=4 or WFState=5 or WFState=6 or WFState=8 or WFState=10");
		obj.addRightBracket();
		obj.DoQuery();
		ht.put("Todolist_MyStartFlow", myStartFlows.size());

		//我参与
		MyJoinFlows myFlows = new MyJoinFlows();
		obj = new QueryObject(myFlows);
		obj.AddWhere("Emps like '%" + WebUser.getNo() + "%'");
		obj.DoQuery();
		ht.put("Todolist_MyFlow", myFlows.size());
		
		
		
		
		return BP.Tools.Json.ToJsonEntityModel(ht);
    }
    
    /**
     * 转换成菜单
     *  @return 
     */
    public String Home_Menu()
    {
    	DataSet ds = new DataSet();

		BP.WF.XML.ClassicMenus menus = new BP.WF.XML.ClassicMenus();
		menus.RetrieveAll();

	   DataTable dtMain= menus.ToDataTable();
	   dtMain.TableName = "ClassicMenus";

	   ds.Tables.add(dtMain);

	   BP.WF.XML.ClassicMenuAdvFuncs advMenms = new BP.WF.XML.ClassicMenuAdvFuncs();
	   advMenms.RetrieveAll();

	   DataTable dtMenuAdv = advMenms.ToDataTable();
	   dtMenuAdv.TableName = "ClassicMenusAdv";
	   ds.Tables.add(dtMenuAdv);

	   return BP.Tools.Json.ToJson(ds);
    }
    
    /**
     * 控制台信息
     *  @return 
     */
    public String Index_Init()
    {
    	java.util.Hashtable ht = new java.util.Hashtable();
		ht.put("Todolist_Runing", BP.WF.Dev2Interface.getTodolist_Runing()); //运行中.
		ht.put("Todolist_EmpWorks", BP.WF.Dev2Interface.getTodolist_EmpWorks()); //待办
		ht.put("Todolist_CCWorks", BP.WF.Dev2Interface.getTodolist_CCWorks()); //抄送.

		//本周.
		ht.put("TodayNum", BP.WF.Dev2Interface.getTodolist_CCWorks()); //抄送.

		return BP.Tools.Json.ToJsonEntityModel(ht);
    }
    
    /**
     * 设置
     *  @return 
     */
    public String Setting_Init()
    {
    	return "";
    }
    
    /**
     * 登录
     *  @return 
     * @throws UnsupportedEncodingException 
     */
    public String Login_Submit() throws UnsupportedEncodingException
    {
    	String userNo = this.GetRequestVal("TB_UserNo");
		String pass = this.GetRequestVal("TB_Pass");

		BP.Port.Emp emp = new Emp();
		emp.setNo(userNo);
		if (emp.RetrieveFromDBSources() ==0)
		{
			return "err@用户名或者密码错误.";
		}

		if (!pass.equals(emp.getPass()))
		{
			return "err@用户名或者密码错误.";
		}

		//调用登录方法.
		try {
			BP.WF.Dev2Interface.Port_Login(emp.getNo(), emp.getName(), emp.getFK_Dept(), emp.getFK_DeptText(),null,null);
		} catch (UnsupportedEncodingException e) {
			Log.DebugWriteError("AppACEHandler Login_Submit():" + e.getMessage());
			e.printStackTrace();
		}

		return "登录成功.";
    }
    
    /**
     * 登录初始化
     *  @return 
     */
    public String Login_Init()
    {
    	java.util.Hashtable ht = new java.util.Hashtable();
		ht.put("SysName", SystemConfig.getSysName());
		ht.put("ServiceTel", SystemConfig.getServiceTel());
		ht.put("UserNo", WebUser.getNo());

		return BP.Tools.Json.ToJsonEntityModel(ht);
    }
    
    /**
     * 草稿
     *  @return 
     */
    public String Draft_Init()
    {
    	DataTable dt = BP.WF.Dev2Interface.DB_GenerDraftDataTable(this.getFK_Flow());
		return BP.Tools.Json.DataTableToJson(dt,false,false,true);
    }
    
    /**
     * 授权登录
     *  @return 
     */
    public String LoginAs()
    {
    	BP.WF.Port.WFEmp wfemp = new BP.WF.Port.WFEmp(this.getNo());
		if (wfemp.getAuthorIsOK() == false)
		{
			return "err@授权登录失败！";
		}
		BP.Port.Emp emp1 = new BP.Port.Emp(this.getNo());
		try {
			BP.Web.WebUser.SignInOfGener(emp1, "CH", false, false, BP.Web.WebUser.getNo(), BP.Web.WebUser.getName());
		} catch (UnsupportedEncodingException e) {
			Log.DebugWriteError("AppACEHandler LoginAs():"+e.getMessage());
			e.printStackTrace();
		}
		return "success@授权登录成功！";
    }
    
    /**
     * 获取当前授权处理人的工作
     *  @return 
     */
    public String Todolist_Author()
    {
    	DataTable dt = null;
		try {
			dt = BP.WF.Dev2Interface.DB_GenerEmpWorksOfDataTable(this.getNo(), this.getFK_Node());
		} catch (Exception e) {
			Log.DebugWriteError("AppACEHandler Todolist_Author():"+e.getMessage());
			e.printStackTrace();
		}
		String  a = BP.Tools.Json.ToJson(dt);
		//转化大写的toJson.
		return BP.Tools.Json.DataTableToJson(dt,false,false,true);
    }

    /**
     * 加载当前授权处理人
     *  @return 
     */
    public String Load_Author()
    {
    	DataTable dt = BP.DA.DBAccess.RunSQLReturnTable("SELECT * FROM WF_EMP WHERE AUTHOR='" + BP.Web.WebUser.getNo() + "'");
		return BP.Tools.Json.ToJson(dt);
    }
    
    /**
     * 抄送列表操作
     *  @return 
     */
    public String cc_Init()
    {
    	String sta = this.GetRequestVal("Sta");
		if (sta == null || sta.equals(""))
		{
			sta = "-1";
		}

		int pageSize = 6; // int.Parse(pageSizeStr);

		String pageIdxStr = this.GetRequestVal("PageIdx");
		if (pageIdxStr == null)
		{
			pageIdxStr = "1";
		}
		int pageIdx = Integer.parseInt(pageIdxStr);

		//实体查询.
		BP.WF.SMSs ss = new BP.WF.SMSs();
		BP.En.QueryObject qo = new BP.En.QueryObject(ss);

		DataTable dt = null;
		if (sta.equals("-1"))
		{
			dt = BP.WF.Dev2Interface.DB_CCList(BP.Web.WebUser.getNo());
		}
		if (sta.equals("0"))
		{
			dt = BP.WF.Dev2Interface.DB_CCList_UnRead(BP.Web.WebUser.getNo());
		}
		if (sta.equals("1"))
		{
			dt = BP.WF.Dev2Interface.DB_CCList_Read(BP.Web.WebUser.getNo());
		}
		if (sta.equals("2"))
		{
			dt = BP.WF.Dev2Interface.DB_CCList_Delete(BP.Web.WebUser.getNo());
		}

		int allNum = qo.GetCount();
		qo.DoQuery(BP.WF.SMSAttr.MyPK, pageSize, pageIdx);

		return BP.Tools.Json.DataTableToJson(dt,false,false,true);
    }
    
    /**
     * 加载关注
     *  @return 
     */
    public String Focus_Init()
    {
    	String flowNo = this.GetRequestVal("FK_Flow");

		int idx = 0;
		//获得关注的数据.
		DataTable dt = BP.WF.Dev2Interface.DB_Focus(flowNo, BP.Web.WebUser.getNo());
		SysEnums stas = new SysEnums("WFSta");
		String[] tempArr;
		for (DataRow dr : dt.Rows)
		{
			int wfsta = Integer.parseInt(dr.getValue("WFSta").toString());
			//edit by liuxc,2016-10-22,修复状态显示不正确问题
			String wfstaT  = ((SysEnum) stas.GetEntityByKey(SysEnumAttr.IntKey, wfsta)).getLab();
			String currEmp = "";

			if (wfsta != WFSta.Complete.getValue())
			{
				//edit by liuxc,2016-10-24,未完成时，处理当前处理人，只显示处理人姓名
				for (String emp : dr.getValue("ToDoEmps").toString().split("[;]", -1))
				{
					tempArr = emp.split("[,]", -1);

					currEmp += tempArr.length > 1 ? tempArr[1] : tempArr[0] + ",";
				}

				currEmp = DotNetToJavaStringHelper.trimEnd(currEmp, ',');

				//currEmp = dr["ToDoEmps"].ToString();
				//currEmp = currEmp.TrimEnd(';');
			}
			dr.setValue("ToDoEmps",currEmp);
			dr.setValue("FlowNote",wfstaT);
			dr.setValue("AtPara",(wfsta == BP.WF.WFSta.Complete.getValue() ? DotNetToJavaStringHelper.trimEnd(DotNetToJavaStringHelper.trimStart(dr.getValue("Sender").toString(), '('), ')').split("[,]", -1)[1] : ""));
		}
		return BP.Tools.Json.DataTableToJson(dt,false,false,true);
    }

    /**
     * 取消关注
     *  @return 
     */
    public String Focus_Delete()
    {
    	BP.WF.Dev2Interface.Flow_Focus(Long.parseLong(this.GetRequestVal("WorkID")));
		return "您已取消关注！";
    }

    /**
     * 
     *  @return 
     */
    public String FlowRpt_Init()
    {
    	StringBuilder Pub1 = new StringBuilder();
		BP.WF.Flows fls = new BP.WF.Flows();
		fls.RetrieveAll();

		FlowSorts ens = new FlowSorts();
		ens.RetrieveAll();

		DataTable dt = BP.WF.Dev2Interface.DB_GenerCanStartFlowsOfDataTable(BP.Web.WebUser.getNo());

		int cols = 3; //定义显示列数 从0开始。
		int widthCell = 100 / cols;
		Pub1.append("<Table width=100% border=0>");
		int idx = -1;
		boolean is1 = false;

		//string timeKey = "s" + this.Session.SessionID + DateTime.Now.ToString("yyMMddHHmmss");
		for (FlowSort en : ens.ToJavaListFs())
		{
			if (en.getParentNo().equals("0") || en.getParentNo().equals("") || en.getNo().equals(""))
			{
				continue;
			}

			idx++;
			if (idx == 0)
			{
				Pub1.append(AddTR(is1));
			}
				is1 = !is1;

			Pub1.append(AddTDBegin("width='" + widthCell+("%' border=0 valign=top")));
			//输出类别.
			//this.Pub1.AddFieldSet(en.Name);
			Pub1.append(AddB(en.getName()));
			Pub1.append(AddUL());

			for (Flow fl : fls.ToJavaList())
			{
				if (fl.getFlowAppType() == FlowAppType.DocFlow)
				{
					continue;
				}

				//如果该目录下流程数量为空就返回.
				if (fls.GetCountByKey(BP.WF.Template.FlowAttr.FK_FlowSort, en.getNo()) == 0)
				{
					continue;
				}

				if (!fl.getFK_FlowSort().equals(en.getNo()))
				{
					continue;
				}

				for (DataRow dr : dt.Rows)
				{
					if (!dr.getValue("No").equals(fl.getNo()))
					{
						continue;
					}
					break;
				}

				Pub1.append(AddLi(" <a  href=\"javascript:WinOpen('../WF/Rpt/Search.aspx?RptNo=ND" + Integer.parseInt(fl.getNo()) + "MyRpt&FK_Flow=" + fl.getNo() + "');\" >" + fl.getName() + "</a> "));
			}

			Pub1.append(AddULEnd());

			Pub1.append(AddTDEnd());
			if (idx == cols - 1)
			{
				idx = -1;
				Pub1.append(AddTREnd());
			}
		}

		while (idx != -1)
		{
			idx++;
			if (idx == cols - 1)
			{
				idx = -1;
				Pub1.append(AddTD());
				Pub1.append(AddTREnd());
			}
			else
			{
				Pub1.append(AddTD());
			}
		}
		Pub1.append(AddTableEnd());
		return Pub1.toString();
    }
    
	/**添加公共的字符串拼接方法table
	*/
	public final String AddTR(boolean item)
	{
		if (item)
		{
			return "\n<TR bgcolor=AliceBlue >";
		}
		else
		{
			return "\n<TR bgcolor=white class=TR>";
		}
	}

	public final String AddTDBegin(String attr)
	{
		return "\n<TD " + attr + " nowrap >";
	}

	public final String AddB(String s)
	{
		if (s == null || s.equals(""))
		{
			return "";
		}
		return "<B>" + s + "</B>";
	}
	public final String AddUL()
	{
		return "<ul>";
	}

	public final String AddLi(String html)
	{
		return "<li>" + html + "</li> \t\n";
	}

	public final String AddULEnd()
	{
		return "</ul>\t\n";
	}

	public final String AddTDEnd()
	{
		return "\n</TD>";
	}

	public final String AddTREnd()
	{
		return "\n</TR>";
	}

	public final String AddTD()
	{
		return "\n<TD >&nbsp;</TD>";
	}

	public final String AddTableEnd()
	{
		return "</Table>";
	}

}

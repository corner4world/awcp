package BP.WF.Data;

import BP.En.Entity;
import BP.En.Map;
import BP.En.QueryObject;
import BP.En.RefMethod;
import BP.En.UAC;
import BP.WF.Entity.TaskSta;
import BP.WF.Entity.WFSta;
import BP.WF.Template.FlowSorts;
import BP.WF.Template.Flows;
import BP.WF.Template.PubLib.WFState;

/** 
流程实例

*/
public class GenerWorkFlowView extends Entity
{

		///#region 基本属性
	/** 
	 主键
	 
	*/
	@Override
	public String getPK()
	{
		return GenerWorkFlowViewAttr.WorkID;
	}
	/** 
	 备注
	 
	*/
	public final String getFlowNote()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.FlowNote);
	}
	public final void setFlowNote(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.FlowNote, value);
	}
	/** 
	 工作流程编号
	 
	*/
	public final String getFK_Flow()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.FK_Flow);
	}
	public final void setFK_Flow(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.FK_Flow,value);
	}
	/** 
	 BillNo
	 
	*/
	public final String getBillNo()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.BillNo);
	}
	public final void setBillNo(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.BillNo, value);
	}
	/** 
	 流程名称
	 
	*/
	public final String getFlowName()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.FlowName);
	}
	public final void setFlowName(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.FlowName, value);
	}
	/** 
	 优先级
	 
	*/
	public final int getPRI()
	{
		return this.GetValIntByKey(GenerWorkFlowViewAttr.PRI);
	}
	public final void setPRI(int value)
	{
		SetValByKey(GenerWorkFlowViewAttr.PRI, value);
	}
	/** 
	 待办人员数量
	 
	*/
	public final int getTodoEmpsNum()
	{
		return this.GetValIntByKey(GenerWorkFlowViewAttr.TodoEmpsNum);
	}
	public final void setTodoEmpsNum(int value)
	{
		SetValByKey(GenerWorkFlowViewAttr.TodoEmpsNum, value);
	}
	/** 
	 待办人员列表
	 
	*/
	public final String getTodoEmps()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.TodoEmps);
	}
	public final void setTodoEmps(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.TodoEmps, value);
	}
	/** 
	 参与人
	 
	*/
	public final String getEmps()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.Emps);
	}
	public final void setEmps(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.Emps, value);
	}
	/** 
	 状态
	 
	*/
	public final TaskSta getTaskSta()
	{
		return TaskSta.forValue(this.GetValIntByKey(GenerWorkFlowViewAttr.TaskSta));
	}
	public final void setTaskSta(TaskSta value)
	{
		SetValByKey(GenerWorkFlowViewAttr.TaskSta, value.getValue());
	}
	/** 
	 类别编号
	 
	*/
	public final String getFK_FlowSort()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.FK_FlowSort);
	}
	public final void setFK_FlowSort(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.FK_FlowSort, value);
	}
	/** 
	 部门编号
	 
	*/
	public final String getFK_Dept()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.FK_Dept);
	}
	public final void setFK_Dept(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.FK_Dept,value);
	}
	/** 
	 标题
	 
	*/
	public final String getTitle()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.Title);
	}
	public final void setTitle(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.Title,value);
	}
	/** 
	 客户编号
	 
	*/
	public final String getGuestNo()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.GuestNo);
	}
	public final void setGuestNo(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.GuestNo, value);
	}
	/** 
	 客户名称
	 
	*/
	public final String getGuestName()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.GuestName);
	}
	public final void setGuestName(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.GuestName, value);
	}
	/** 
	 产生时间
	 
	*/
	public final String getRDT()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.RDT);
	}
	public final void setRDT(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.RDT,value);
	}
	/** 
	 节点应完成时间
	 
	*/
	public final String getSDTOfNode()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.SDTOfNode);
	}
	public final void setSDTOfNode(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.SDTOfNode, value);
	}
	/** 
	 流程应完成时间
	 
	*/
	public final String getSDTOfFlow()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.SDTOfFlow);
	}
	public final void setSDTOfFlow(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.SDTOfFlow, value);
	}
	/** 
	 流程ID
	 
	*/
	public final long getWorkID()
	{
		return this.GetValInt64ByKey(GenerWorkFlowViewAttr.WorkID);
	}
	public final void setWorkID(long value)
	{
		SetValByKey(GenerWorkFlowViewAttr.WorkID,value);
	}
	/** 
	 主线程ID
	 
	*/
	public final long getFID()
	{
		return this.GetValInt64ByKey(GenerWorkFlowViewAttr.FID);
	}
	public final void setFID(long value)
	{
		SetValByKey(GenerWorkFlowViewAttr.FID, value);
	}
	/** 
	 父节点ID 为或者-1.
	 
	*/
	public final long getCWorkID()
	{
		return this.GetValInt64ByKey(GenerWorkFlowViewAttr.CWorkID);
	}
	public final void setCWorkID(long value)
	{
		SetValByKey(GenerWorkFlowViewAttr.CWorkID, value);
	}
	/** 
	 PFlowNo
	 
	*/
	public final String getCFlowNo()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.CFlowNo);
	}
	public final void setCFlowNo(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.CFlowNo, value);
	}
	/** 
	 父节点流程编号.
	 
	*/
	public final long getPWorkID()
	{
		return this.GetValInt64ByKey(GenerWorkFlowViewAttr.PWorkID);
	}
	public final void setPWorkID(long value)
	{
		SetValByKey(GenerWorkFlowViewAttr.PWorkID, value);
	}
	/** 
	 父流程调用的节点
	 
	*/
	public final int getPNodeID()
	{
		return this.GetValIntByKey(GenerWorkFlowViewAttr.PNodeID);
	}
	public final void setPNodeID(int value)
	{
		SetValByKey(GenerWorkFlowViewAttr.PNodeID, value);
	}
	/** 
	 PFlowNo
	 
	*/
	public final String getPFlowNo()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.PFlowNo);
	}
	public final void setPFlowNo(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.PFlowNo, value);
	}
	/** 
	 吊起子流程的人员
	 
	*/
	public final String getPEmp()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.PEmp);
	}
	public final void setPEmp(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.PEmp, value);
	}
	/** 
	 发起人
	 
	*/
	public final String getStarter()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.Starter);
	}
	public final void setStarter(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.Starter, value);
	}
	/** 
	 发起人名称
	 
	*/
	public final String getStarterName()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.StarterName);
	}
	public final void setStarterName(String value)
	{
		this.SetValByKey(GenerWorkFlowViewAttr.StarterName, value);
	}
	/** 
	 发起人部门名称
	 
	*/
	public final String getDeptName()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.DeptName);
	}
	public final void setDeptName(String value)
	{
		this.SetValByKey(GenerWorkFlowViewAttr.DeptName, value);
	}
	/** 
	 当前节点名称
	 
	*/
	public final String getNodeName()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.NodeName);
	}
	public final void setNodeName(String value)
	{
		this.SetValByKey(GenerWorkFlowViewAttr.NodeName, value);
	}
	/** 
	 当前工作到的节点
	 
	*/
	public final int getFK_Node()
	{
		return this.GetValIntByKey(GenerWorkFlowViewAttr.FK_Node);
	}
	public final void setFK_Node(int value)
	{
		SetValByKey(GenerWorkFlowViewAttr.FK_Node, value);
	}
	/** 
	 工作流程状态
	 
	*/
	public final WFState getWFState()
	{
		return WFState.forValue(this.GetValIntByKey(GenerWorkFlowViewAttr.WFState));
	}
	public final void setWFState(WFState value)
	{
		if (value == WFState.Complete)
		{
			SetValByKey(GenerWorkFlowViewAttr.WFSta, WFSta.Complete.getValue());
		}
		else if (value == WFState.Delete)
		{
			SetValByKey(GenerWorkFlowViewAttr.WFSta, WFSta.Delete.getValue());
		}
		else
		{
			SetValByKey(GenerWorkFlowViewAttr.WFSta, WFSta.Runing.getValue());
		}

		SetValByKey(GenerWorkFlowViewAttr.WFState, value.getValue());
	}
	/** 
	 状态(简单)
	 
	*/
	public final WFSta getWFSta()
	{
		return WFSta.forValue(this.GetValIntByKey(GenerWorkFlowViewAttr.WFSta));
	}
	public final void setWFSta(WFSta value)
	{
		SetValByKey(GenerWorkFlowViewAttr.WFSta, value.getValue());
	}
	public final String getWFStateText()
	{
	
		switch((WFState)this.getWFState())
		{
			case Complete:
				return "已完成";
			case Runing:
				return "在运行";
			case HungUp:
				return "挂起";
			case Askfor:
				return "加签";
			default:
				return "未判断";
		}
	}
	/** 
	 GUID
	 
	*/
	public final String getGUID()
	{
		return this.GetValStrByKey(GenerWorkFlowViewAttr.GUID);
	}
	public final void setGUID(String value)
	{
		SetValByKey(GenerWorkFlowViewAttr.GUID, value);
	}

		///#endregion


		///#region 参数属性.

	public final String getParas_ToNodes()
	{
		return this.GetParaString("ToNodes");
	}

	public final void setParas_ToNodes(String value)
	{
		this.SetPara("ToNodes", value);
	}
	/** 
	 加签信息
	 
	*/

	public final String getParas_AskForReply()
	{
		return this.GetParaString("AskForReply");
	}

	public final void setParas_AskForReply(String value)
	{
		this.SetPara("AskForReply", value);
	}

		///#endregion 参数属性.


		///#region 构造函数
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.Readonly();
		return uac;
	}
	/** 
	 产生的工作流程
	 
	*/
	public GenerWorkFlowView()
	{
	}
	public GenerWorkFlowView(long workId)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(GenerWorkFlowViewAttr.WorkID, workId);
		if (qo.DoQuery() == 0)
		{
			throw new RuntimeException("工作 GenerWorkFlowView [" + workId + "]不存在。");
		}
	}
	/** 
	 执行修复
	 
	*/
	public final void DoRepair()
	{
	}
	/** 
	 重写基类方法
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("WF_GenerWorkFlow");
		map.setEnDesc("流程查询");
		map.AddTBIntPK(GenerWorkFlowViewAttr.WorkID, 0, "WorkID", true, true);


		map.AddTBString(GenerWorkFlowViewAttr.StarterName, null, "发起人", true, false, 0, 30, 10);
		map.AddTBString(GenerWorkFlowViewAttr.Title, null, "标题", true, false, 0, 100, 10,true);
		map.AddDDLSysEnum(GenerWorkFlowViewAttr.WFSta, 0, "流程状态", true, false, GenerWorkFlowViewAttr.WFSta, "@0=运行中@1=已完成@2=其他");
		map.AddTBString(GenerWorkFlowViewAttr.NodeName, null, "当前节点名称", true, false, 0, 100, 10);
		map.AddTBDateTime(GenerWorkFlowViewAttr.RDT, "记录日期", true, true);
		map.AddTBString(GenerWorkFlowViewAttr.BillNo, null, "单据编号", true, false, 0, 100, 10);
		map.AddTBStringDoc(GenerWorkFlowViewAttr.FlowNote, null, "备注", true, false,true);

		map.AddDDLEntities(GenerWorkFlowViewAttr.FK_FlowSort, null, "类别", new FlowSorts(), false);
		map.AddDDLEntities(GenerWorkFlowViewAttr.FK_Flow, null, "流程", new Flows(), false);
		map.AddDDLEntities(GenerWorkFlowViewAttr.FK_Dept, null, "部门", new BP.Port.Depts(), false);

		map.AddTBInt(GenerWorkFlowViewAttr.FID, 0, "FID", false, false);
		map.AddTBInt(GenerWorkFlowViewAttr.FK_Node, 0, "FK_Node", false, false);


		map.AddDDLEntities(GenerWorkFlowViewAttr.FK_NY, null, "月份", new GenerWorkFlowViewNYs(), false);

		map.AddTBMyNum();

			//map.AddSearchAttr(GenerWorkFlowViewAttr.FK_Dept);
		map.AddSearchAttr(GenerWorkFlowViewAttr.FK_Flow);
		map.AddSearchAttr(GenerWorkFlowViewAttr.WFSta);
		map.AddSearchAttr(GenerWorkFlowViewAttr.FK_NY);


		RefMethod rm = new RefMethod();
		rm.Title = "轨迹";
		rm.ClassMethodName = this.toString() + ".DoTrack";
		rm.Icon = "/WF/Img/Track.png";
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "删除";
		rm.ClassMethodName = this.toString() + ".DoDel";
		rm.Warning = "您确定要删除吗？";
		rm.Icon = "/WF/Img/Btn/Delete.gif";
		rm.IsForEns = false;
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Icon = BP.WF.Glo.getCCFlowAppPath() + "WF/Img/Btn/CC.gif";
		rm.Title = "移交";
		rm.IsForEns = false;
		rm.ClassMethodName = this.toString() + ".DoShift";
		rm.getHisAttrs().AddTBString("ToEmp", null, "移交给", true, false, 0, 300, 100);
		   // rm.HisAttrs.AddDDLEntities("ToEmp", null, "移交给:", new BP.WF.Flows(), true);
		rm.getHisAttrs().AddTBString("Note", null, "移交原因", true, false, 0, 300, 100);
		map.AddRefMethod(rm);

		this.set_enMap(map);
		return this.get_enMap();
	}

		///#endregion


}

package org.jflow.framework.common.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jflow.framework.system.ui.core.Button;
import org.jflow.framework.system.ui.core.CheckBox;

import BP.DA.DBAccess;
import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.En.Attr;
import BP.En.Attrs;
import BP.Port.WebUser;
import BP.Sys.PubClass;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;
import BP.WF.OSModel;
import BP.WF.WorkNode;
import BP.WF.Data.GERpt;
import BP.WF.Template.CondModel;
import BP.WF.Template.Node;
import BP.WF.Template.NodeStations;
import BP.WF.Template.Nodes;
import BP.WF.Template.Condition.Cond;
import BP.WF.Template.Condition.CondAttr;
import BP.WF.Template.Condition.TurnTo;
import BP.WF.Template.Condition.TurnTos;
import BP.WF.Template.Ext.Selector;
import BP.WF.Template.PubLib.DeliveryWay;
import BP.WF.Template.PubLib.NodeAttr;
import BP.WF.Template.WorkBase.Work;

public class AccepterModel extends BaseModel {
	/**
	 * 日志对象
	 */
	private static Log logger = LogFactory.getLog(AccepterModel.class);
	public StringBuilder Pub1 = new StringBuilder();

	public StringBuilder Left = new StringBuilder();

	public String title = "选择下一步骤接受的人员";

	public AccepterModel(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);

		// 判断是否需要转向。
		if (this.getToNode() == 0) {
			int num = 0;
			int tempToNodeID = 0;
			// 如果到达的点为空
			// 首先判断当前节点的ID，是否配置到了其他节点里面，
			// * * 如果有则需要转向高级的选择框中去，当前界面不能满足公文类的选择人需求。
			String sql = "SELECT COUNT(*) FROM WF_Node WHERE FK_Flow='" + this.getHisNode().getFK_Flow() + "' AND "
					+ NodeAttr.DeliveryWay + "=" + DeliveryWay.BySelected.getValue() + " AND " + NodeAttr.DeliveryParas
					+ " LIKE '%" + this.getHisNode().getNodeID() + "%' ";

			if (DBAccess.RunSQLReturnValInt(sql, 0) > 0) {
				// 说明以后的几个节点人员处理的选择
				String url = "AccepterAdv.jsp?1=3&" + get_request().getQueryString();
				try {
					get_response().sendRedirect(url);
				} catch (IOException e) {
					logger.info("ERROR", e);
				}
				return;
			}

			Nodes nds = this.getHisNode().getHisToNodes();
			if (nds.size() == 0) {
				appendPub1(AddFieldSetRed("提示", "当前点是最后的一个节点，不能使用此功能。"));
				return;
			} else if (nds.size() == 1) {
				Node toND = (Node) ((nds.get(0) instanceof Node) ? nds.get(0) : null);
				tempToNodeID = toND.getNodeID();
			} else {
				Node nd = new Node(this.getFK_Node());
				for (Node mynd : Nodes.convertNodes(nds)) {
					if (mynd.getHisDeliveryWay() != DeliveryWay.BySelected) {
						continue;
					}

					/// #region 过滤不能到达的节点.
					if (nd.getCondModel() == CondModel.ByLineCond) {
						Cond cond = new Cond();
						int i = cond.Retrieve(CondAttr.FK_Node, this.getHisNode().getNodeID(), CondAttr.ToNodeID,
								mynd.getNodeID());
						if (i == 0) {
							continue; // 没有设置方向条件，就让它跳过去。
						}
						cond.setWorkID(this.getWorkID());
						cond.en = getwk();
						if (cond.getIsPassed() == false) {
							continue;
						}
					}
					/// #endregion 过滤不能到达的节点.
					tempToNodeID = mynd.getNodeID();
					num++;
				}
			}

			if (tempToNodeID == 0) {
				this.winCloseWithMsg("@流程设计错误：\n\n 当前节点的所有分支节点没有一个接受人员规则为按照选择接受。");
				return;
			}

			try {
				get_response().sendRedirect(getBasePath() + "WF/WorkOpt/Accepter.jsp?FK_Flow=" + this.getFK_Flow()
						+ "&FK_Node=" + this.getFK_Node() + "&ToNode=" + tempToNodeID + "&FID=" + this.getFID()
						+ "&type=1&WorkID=" + this.getWorkID() + "&IsWinOpen=" + this.getIsWinOpen());
			} catch (IOException e) {
				logger.info("ERROR", e);
			}
			return;
		}

		try {
			// 首先判断是否有多个分支的情况。
			if (this.getIsMFZ() && getToNode() == 0) {
				IsMultiple = true;
				// this.BindMStations();
				return;
			}
			mySelector = new Selector(this.getToNode());
			switch (mySelector.getSelectorModel()) {
			case Station:
				// this.BindByStation();
				returnValue("BindByStation");
				break;
			case SQL:
				// this.BindBySQL();
				returnValue("BindBySQL");
				break;
			case Dept:
				// this.BindByDept();
				returnValue("BindByDept");
				break;
			case Emp:
				// this.BindByEmp();
				returnValue("BindByEmp");
				break;
			case Url:
				if (mySelector.getSelectorP1().contains("?")) {
					try {
						get_response().sendRedirect(mySelector.getSelectorP1() + "&WorkID=" + this.getWorkID()
								+ "&FK_Node=" + this.getFK_Node());
					} catch (IOException e) {
						logger.info("ERROR", e);
					}
				} else {
					try {
						get_response().sendRedirect(mySelector.getSelectorP1() + "?WorkID=" + this.getWorkID()
								+ "&FK_Node=" + this.getFK_Node());
					} catch (IOException e) {
						logger.info("ERROR", e);
					}
				}
				return;
			default:
				break;
			}
		} catch (RuntimeException ex) {
			this.Pub1 = new StringBuilder();
			if (!StringHelper.isNullOrEmpty(ex.getMessage())) {
				appendPub1(AddMsgOfWarning("错误", ex.getMessage()));
			}
			logger.info("ERROR", ex);

		}

	}

	private void appendPub1(String str) {
		this.Pub1.append(str);
	}

	private String initParameterValue(String param) {
		return get_request().getParameter(param);
	}

	/**
	 * 打开
	 * 
	 */
	public final int getIsWinOpen() {
		String str = initParameterValue("IsWinOpen");
		if (str == null || str.equals("1") || str.equals("")) {
			return 1;
		}
		return 0;
	}

	/**
	 * 到达的节点
	 * 
	 */
	public final int getToNode() {

		if (initParameterValue("ToNode") == null || "".equals(initParameterValue("ToNode"))) {
			return 0;
		}
		return Integer.parseInt(initParameterValue("ToNode"));
	}

	public final long getFID() {
		if (initParameterValue("FID") != null) {
			return Long.parseLong(initParameterValue("FID"));
		}

		return 0;
	}

	public final String getFK_Dept() {
		String s = initParameterValue("FK_Dept");
		if (s == null) {
			s = WebUser.getFK_Dept();
		}
		return s;
	}

	public final String getFK_Station() {
		return initParameterValue("FK_Station");
	}

	public final String getWorkIDs() {
		return initParameterValue("WorkIDs");
	}

	public final String getDoFunc() {
		return initParameterValue("DoFunc");
	}

	public final String getCFlowNo() {
		return initParameterValue("CFlowNo");
	}

	private boolean IsMultiple = false;
	/// #endregion 属性.

	// public DataTable GetTable()
	// {
	// if (this.ToNode == 0)
	// throw new Exception("@流程设计错误，没有转向的节点。举例说明:
	// 当前是A节点。如果您在A点的属性里启用了[接受人]按钮，那么他的转向节点集合中(就是A可以转到的节点集合比如:A到B，A到C,
	// 那么B,C节点就是转向节点集合)，必须有一个节点是的节点属性的[访问规则]设置为[由上一步发送人员选择]");

	// NodeStations stas = new NodeStations(this.ToNode);
	// if (stas.Count == 0)
	// {
	// Node toNd = new Node(this.ToNode);
	// throw new Exception("@流程设计错误：设计员没有设计节点[" + toNd.Name + "]，接受人的岗位范围。");
	// }

	// string sql = "";
	// if (initParameterValue("IsNextDept"] != null)
	// {
	// int len = this.FK_Dept.Length + 2;
	// string sqlDept = "SELECT No FROM Port_Dept WHERE " +
	// SystemConfig.AppCenterDBLengthStr + "(No)=" + len + " AND No LIKE '" +
	// this.FK_Dept + "%'";
	// sql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp
	// A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
	// sql += "SELECT FK_EMP FROM Port_EmpSTATION WHERE FK_STATION ";
	// sql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" +
	// this.ToNode + ") ";
	// sql += ") AND A.No IN( SELECT No FROM Port_Emp WHERE " +
	// SystemConfig.AppCenterDBLengthStr + "(FK_Dept)=" + len + " AND FK_Dept
	// LIKE '" + this.FK_Dept + "%')";
	// sql += " ORDER BY FK_DEPT ";
	// return BP.DA.DBAccess.RunSQLReturnTable(sql);
	// }

	// // 优先解决本部门的问题。
	// if (this.FK_Dept == WebUser.FK_Dept)
	// {
	// if (BP.WF.Glo.OSModel == OSModel.BPM)
	// {
	// sql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp
	// A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
	// sql += "SELECT FK_EMP FROM Port_DeptEmpStation WHERE FK_STATION ";
	// sql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" +
	// ToNode + ") ";
	// sql += ") AND a.No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept ='"
	// + WebUser.FK_Dept + "')";
	// sql += " ORDER BY FK_DEPT ";
	// }
	// else
	// {
	// sql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp
	// A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
	// sql += "SELECT FK_EMP FROM Port_EmpSTATION WHERE FK_STATION ";
	// sql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" +
	// ToNode + ") ";
	// sql += ") AND a.No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept ='"
	// + WebUser.FK_Dept + "')";
	// sql += " ORDER BY FK_DEPT ";
	// }

	// DataTable dt = BP.DA.DBAccess.RunSQLReturnTable(sql);
	// if (dt.Rows.Count != 0)
	// return dt;
	// }

	// sql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp
	// A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
	// sql += "SELECT FK_EMP FROM Port_EmpSTATION WHERE FK_STATION ";
	// sql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" +
	// ToNode + ") ";
	// sql += ") ORDER BY FK_DEPT ";
	// return BP.DA.DBAccess.RunSQLReturnTable(sql);
	// }
	public final DataTable GetTable() {
		if (this.getToNode() == 0) {
			throw new RuntimeException(
					"@流程设计错误，没有转向的节点。举例说明: 当前是A节点。如果您在A点的属性里启用了[接受人]按钮，那么他的转向节点集合中(就是A可以转到的节点集合比如:A到B，A到C, 那么B,C节点就是转向节点集合)，必须有一个节点是的节点属性的[访问规则]设置为[由上一步发送人员选择]");
		}

		NodeStations stas = new NodeStations(this.getToNode());
		if (stas.size() == 0) {
			Node toNd = new Node(this.getToNode());
			throw new RuntimeException("@流程设计错误：设计员没有设计节点[" + toNd.getName() + "]，接受人的岗位范围。");
		}

		String BindByStationSql = "";
		if (initParameterValue("IsNextDept") != null) {
			int len = this.getFK_Dept().length() + 2;
			String sqlDept = "SELECT No FROM Port_Dept WHERE " + SystemConfig.getAppCenterDBLengthStr() + "(No)=" + len
					+ " AND No LIKE '" + this.getFK_Dept() + "%'";
			BindByStationSql = "SELECT A.No,A.Name, A.FK_Dept, B.Name as DeptName FROM Port_Emp A,Port_Dept B WHERE A.FK_Dept=B.No AND a.NO IN ( ";
			BindByStationSql += "SELECT FK_EMP FROM Port_EmpSTATION WHERE FK_STATION ";
			BindByStationSql += "IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node=" + this.getToNode() + ") ";
			BindByStationSql += ") AND A.No IN( SELECT No FROM Port_Emp WHERE  "
					+ SystemConfig.getAppCenterDBLengthStr() + "(FK_Dept)=" + len + " AND FK_Dept LIKE '"
					+ this.getFK_Dept() + "%')";
			BindByStationSql += " ORDER BY FK_DEPT ";
			return BP.DA.DBAccess.RunSQLReturnTable(BindByStationSql);
		}

		String ParSql = "select No from Port_Dept where ParentNo='0'";
		DataTable ParDt = DBAccess.RunSQLReturnTable(ParSql);
		// if (ParDt.Rows.Count == 0)//错误的组织结构
		// {
		// }

		// 优先解决本部门的问题。
		BindByStationSql = String.format(
				"select No,Name,ParentNo,'1' IsParent from Port_Dept where ParentNo='0' union"
						+ " select No,Name,b.FK_Station as ParentNo,'0' IsParent  from Port_Emp a inner"
						+ " join Port_DeptEmpStation b on a.No=b.FK_Emp and b.FK_Station in"
						+ " (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s')  WHERE No in"
						+ "  (SELECT FK_EMP FROM Port_DeptEmpStation "
						+ " WHERE FK_STATION IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s'))"
						+ " AND No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept ='%2$s') "
						+ " union select No,Name,'%3$s' ParentNo,'1' IsParent  from Port_Station where no "
						+ "in(SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s')",
				getToNode(), WebUser.getFK_Dept(), ParDt.Rows.get(0).getValue(0).toString());
		DdlEmpSql = String.format(
				"select No,Name from Port_Emp a inner"
						+ " join Port_DeptEmpStation b on a.No=b.FK_Emp and b.FK_Station in"
						+ " (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s')  WHERE No in"
						+ "  (SELECT FK_EMP FROM Port_DeptEmpStation "
						+ " WHERE FK_STATION IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s'))"
						+ " AND No IN (SELECT FK_Emp FROM Port_EmpDept WHERE FK_Dept ='%2$s')",
				getToNode(), WebUser.getFK_Dept());

		if (this.getFK_Dept().equals(WebUser.getFK_Dept())) {

			if (BP.WF.Glo.getOSModel() == OSModel.BPM) {

			} else {
				BindByStationSql.replace("Port_DeptEmpStation", "Port_EmpSTATION");
				DdlEmpSql.replace("Port_DeptEmpStation", "Port_EmpSTATION");
			}
			return DBAccess.RunSQLReturnTable(BindByStationSql);
		}

		BindByStationSql = String.format(
				"select No,Name,ParentNo,'1' IsParent from Port_Dept where ParentNo='0' union"
						+ " select No,Name,b.FK_Station as ParentNo,'0' IsParent  from Port_Emp a inner"
						+ " join Port_EmpSTATION b on a.No=b.FK_Emp and b.FK_Station in"
						+ " (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s')  WHERE No in"
						+ "  (SELECT FK_EMP FROM Port_EmpSTATION "
						+ " WHERE FK_STATION IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s'))"
						+ " AND No IN (SELECT FK_Emp FROM Port_EmpDept) "
						+ " union select No,Name,'%3$s' ParentNo,'1' IsParent  from Port_Station where no "
						+ "in(SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s')",
				getToNode(), WebUser.getFK_Dept(), ParDt.Rows.get(0).getValue(0).toString());

		DdlEmpSql = String.format(
				"select No,Name,b.FK_Station as ParentNo,'0' IsParent  from Port_Emp a inner"
						+ " join Port_EmpSTATION b on a.No=b.FK_Emp and b.FK_Station in"
						+ " (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s')  WHERE No in"
						+ "  (SELECT FK_EMP FROM Port_EmpSTATION "
						+ " WHERE FK_STATION IN (SELECT FK_STATION FROM WF_NodeStation WHERE FK_Node='%1$s'))"
						+ " AND No IN (SELECT FK_Emp FROM Port_EmpDept) ",
				getToNode(), WebUser.getFK_Dept(), ParDt.Rows.get(0).getValue(0).toString());

		return DBAccess.RunSQLReturnTable(BindByStationSql);
	}

	private Node _HisNode = null;

	/**
	 * 它的节点
	 * 
	 */
	public final Node getHisNode() {
		if (_HisNode == null) {
			_HisNode = new Node(this.getFK_Node());
		}
		return _HisNode;
	}

	/**
	 * 是否多分支
	 * 
	 */
	public final boolean getIsMFZ() {
		Nodes nds = this.getHisNode().getHisToNodes();
		int num = 0;
		for (Node mynd : Nodes.convertNodes(nds)) {
			/// #region 过滤不能到达的节点.
			Cond cond = new Cond();
			int i = cond.Retrieve(CondAttr.FK_Node, this.getHisNode().getNodeID(), CondAttr.ToNodeID, mynd.getNodeID());
			if (i == 0) {
				continue; // 没有设置方向条件，就让它跳过去。
			}
			cond.setWorkID(this.getWorkID());
			cond.en = getwk();

			if (cond.getIsPassed() == false) {
				continue;
			}
			/// #endregion 过滤不能到达的节点.

			if (mynd.getHisDeliveryWay() == DeliveryWay.BySelected) {
				num++;
			}
		}
		if (num == 0) {
			return false;
		}
		if (num == 1) {
			return false;
		}
		return true;
	}

	/**
	 * 绑定多分支
	 * 
	 */
	public final void BindMStations() {

		this.BindByStation();

		Nodes mynds = this.getHisNode().getHisToNodes();
		this.Left.append("<fieldset><legend>&nbsp;选择方向:列出所选方向设置的人员&nbsp;</legend>");
		String str = "<p>";
		for (Node mynd : Nodes.convertNodes(mynds)) {
			if (mynd.getHisDeliveryWay() != DeliveryWay.BySelected) {
				continue;
			}

			/// #region 过滤不能到达的节点.
			Cond cond = new Cond();
			int i = cond.Retrieve(CondAttr.FK_Node, this.getHisNode().getNodeID(), CondAttr.ToNodeID, mynd.getNodeID());
			if (i == 0) {
				continue; // 没有设置方向条件，就让它跳过去。
			}

			cond.setWorkID(this.getWorkID());
			cond.en = getwk();
			if (cond.getIsPassed() == false) {
				continue;
			}
			/// #endregion 过滤不能到达的节点.

			if (this.getToNode() == mynd.getNodeID()) {
				str += "&nbsp;&nbsp;<b class='l-link'><font color='red' >" + mynd.getName() + "</font></b>";
			} else {
				str += "&nbsp;&nbsp;<b><a class='l-link' href='Accepter.aspx?FK_Node=" + this.getFK_Node()
						+ "&type=1&ToNode=" + mynd.getNodeID() + "&WorkID=" + this.getWorkID() + "' >" + mynd.getName()
						+ "</a></b>";
			}
		}
		this.Left.append(str + "</p>");
		this.Left.append(AddFieldSetEnd());
	}

	public Selector mySelector = null;
	public GERpt _wk = null;

	public final GERpt getwk() {
		if (_wk == null) {
			_wk = this.getHisNode().getHisFlow().getHisGERpt();
			_wk.setOID(this.getWorkID());
			_wk.Retrieve();
			_wk.ResetDefaultVal();
		}
		return _wk;
	}

	/**
	 * 按sql方式
	 * 
	 */
	public final String BindBySQL() {
		String sqlGroup = mySelector.getSelectorP1();
		sqlGroup = sqlGroup.replace("@WebUser.No", WebUser.getNo());
		sqlGroup = sqlGroup.replace("@WebUser.Name", WebUser.getName());
		sqlGroup = sqlGroup.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());

		String sqlDB = mySelector.getSelectorP2();
		sqlDB = sqlDB.replace("@WebUser.No", WebUser.getNo());
		sqlDB = sqlDB.replace("@WebUser.Name", WebUser.getName());
		sqlDB = sqlDB.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());

		// DataTable dtGroup = DBAccess.RunSQLReturnTable(sqlGroup);
		// DataTable dtDB = DBAccess.RunSQLReturnTable(sqlDB);

		// if (this.MySelector.SelectorDBShowWay == SelectorDBShowWay.Table)
		// this.BindBySQL_Table(dtGroup, dtDB);
		// else
		// this.BindBySQL_Tree(dtGroup, dtDB);
		DataTable ParDt = DBAccess.RunSQLReturnTable("select No from Port_Dept where ParentNo='0'");
		String BindBySQL = String.format(
				"select No,Name,FK_Dept as ParentNo,'0' IsParent from (%1$s) emp"
						+ " union  select No,Name,'%3$s' ParentNo,'1' IsParent from (%2$s) dept"
						+ " union  select No,Name,'0' ParentNo,'1' IsParent from Port_Dept where ParentNo='0'",
				sqlDB, sqlGroup, ParDt.Rows.get(0).getValue(0).toString());
		// ????????????
		// DataTable empDt = DBAccess.RunSQLReturnTable(sqlDB);
		// string empsStr = "";
		// string setChar = ",";
		// for (int i = 0; i < empDt.Rows.Count; i++)
		// {
		// if (i == empDt.Rows.Count - 1)
		// {
		// setChar = "";
		// }
		// empsStr += empDt.Rows[i]["No"].ToString() + setChar;
		// }

		// DdlEmpSql = string.Format("select No,Name from Port_Emp WHERE No in
		// ({0})", empsStr);

		DdlEmpSql = sqlDB; // No,Name没有的情况会报错
		DataTable BindBySQLDt = DBAccess.RunSQLReturnTable(BindBySQL);

		return GetTreeJsonByTable(BindBySQLDt, "NO", "NAME", "ParentNo", "0", "IsParent", "");

	}

	/**
	 * 按BindByEmp 方式
	 * 
	 */
	public final String BindByEmp() {
		// string sqlGroup = "SELECT No,Name FROM Port_Dept WHERE No IN (SELECT
		// FK_Dept FROM Port_Emp WHERE No in(SELECT FK_EMP FROM WF_NodeEmp WHERE
		// FK_Node='" + MySelector.NodeID + "'))";
		// string sqlDB = "SELECT No,Name,FK_Dept FROM Port_Emp WHERE No in
		// (SELECT FK_EMP FROM WF_NodeEmp WHERE FK_Node='" + MySelector.NodeID +
		// "')";

		// DataTable dtGroup = DBAccess.RunSQLReturnTable(sqlGroup);
		// DataTable dtDB = DBAccess.RunSQLReturnTable(sqlDB);

		// if (this.MySelector.SelectorDBShowWay == SelectorDBShowWay.Table)
		// this.BindBySQL_Table(dtGroup, dtDB);
		// else
		// this.BindBySQL_Tree(dtGroup, dtDB);

		String BindByEmpSql = String
				.format("select No,Name,ParentNo,'1' IsParent  from Port_Dept   WHERE No IN (SELECT FK_Dept FROM "
						+ "Port_Emp WHERE No in(SELECT FK_EMP FROM WF_NodeEmp WHERE FK_Node=%1$s)) or ParentNo=0 union "
						+ "select No,Name,FK_Dept as ParentNo,'0' IsParent  from Port_Emp  WHERE No in (SELECT FK_EMP "
						+ "FROM WF_NodeEmp WHERE FK_Node=%1$s)", mySelector.getNodeID());
		DdlEmpSql = String.format(
				"select No,Name from Port_Emp  WHERE No in (SELECT FK_EMP " + "FROM WF_NodeEmp WHERE FK_Node=%1$s)",
				mySelector.getNodeID());
		DataTable BindByEmpDt = DBAccess.RunSQLReturnTable(BindByEmpSql);
		DataTable ParDt = DBAccess.RunSQLReturnTable("select No from Port_Dept where ParentNo='0'");
		for (DataRow r : BindByEmpDt.Rows) {
			if (r.getValue("IsParent").toString().equals("1") && !r.getValue("ParentNo").toString().equals("0")) {
				r.setValue("ParentNo", ParDt.Rows.get(0).getValue(0).toString());
			}
		}
		return GetTreeJsonByTable(BindByEmpDt, "NO", "NAME", "ParentNo", "0", "IsParent", "");
	}

	public String DdlEmpSql = "";

	/**
	 * 返回值
	 * 
	 */
	private void returnValue(String whichMet) {
		String method = "";
		// 返回值
		String s_responsetext = "";

		if (StringHelper.isNullOrEmpty(initParameterValue("method"))) {
			return;
		}

		method = initParameterValue("method").toString();
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (method)
		// ORIGINAL LINE: case "getTreeDateMet":
		if (method.equals("getTreeDateMet")) // 获取数据
		{
			s_responsetext = getTreeDateMet(whichMet);
		}
		// ORIGINAL LINE: case "saveMet":
		else if (method.equals("saveMet")) {
			saveMet();
		}

		if (StringHelper.isNullOrEmpty(s_responsetext)) {
			s_responsetext = "";
		}
		s_responsetext = AppendJson(s_responsetext);
		s_responsetext = DdlValue(s_responsetext, DdlEmpSql);
		// 组装ajax字符串格式,返回调用客户端 树型
		printResult(s_responsetext);
	}

	private void printResult(String result) {
		get_response().setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		try {
			out = get_response().getWriter();
			out.write(result);

			out.flush();
		} catch (IOException e) {
			logger.info("ERROR", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public final String AppendJson(String json) {
		StringBuilder AppendJson = new StringBuilder();
		AppendJson.append(json);
		AppendJson.append(",CheId:");
		String alreadyHadEmps = String
				.format("select No, Name from Port_Emp where No in( select FK_Emp from WF_SelectAccper "
						+ "where FK_Node=%1$s and WorkID=%2$s)", this.getToNode(), this.getWorkID());
		DataTable dt = DBAccess.RunSQLReturnTable(alreadyHadEmps);
		AppendJson.append("[{\"id\":\"CheId\",\"iconCls\":\"icon-save\",\"text\":\"已选人员\",\"children\":[");
		for (int i = 0; i < dt.Rows.size(); i++) {
			AppendJson.append("{\"id\":\"" + dt.Rows.get(i).getValue(0).toString() + "\",iconCls:\"icon-user\""
					+ ",\"text\":\"" + dt.Rows.get(i).getValue(1).toString() + "\"");
			if (i == dt.Rows.size() - 1) {
				AppendJson.append("}");
				break;
			}
			AppendJson.append("},");
		}
		// AppendJson.Append("]}]}");

		AppendJson.append("]}]");

		AppendJson.insert(0, "{tt:");
		return AppendJson.toString();
	}

	public final String DdlValue(String StrJson, String Str) {
		StringBuilder SBuilder = new StringBuilder();
		SBuilder.append(StrJson);
		DataTable dt = DBAccess.RunSQLReturnTable(Str);

		SBuilder.append(",ddl:[");
		for (int i = 0; i < dt.Rows.size(); i++) {
			if (i == 0) {
				SBuilder.append("{\"id\":\"" + dt.Rows.get(i).getValue("No").toString() + "\",\"text\":\""
						+ dt.Rows.get(i).getValue("Name").toString() + "\",\"selected\":\"selected\"}");
			} else {
				SBuilder.append("{\"id\":\"" + dt.Rows.get(i).getValue("No").toString() + "\",\"text\":\""
						+ dt.Rows.get(i).getValue("Name").toString() + "\"}");
			}
			if (i == dt.Rows.size() - 1) {
				SBuilder.append("");
				continue;
			}
			SBuilder.append(",");
		}
		SBuilder.append("]}");
		return SBuilder.toString();
	}

	public final String getTreeDateMet(String Met) {
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (Met)
		// ORIGINAL LINE: case "BindByEmp":
		if (Met.equals("BindByEmp")) {
			return BindByEmp();
		}
		// ORIGINAL LINE: case "BindByDept":
		else if (Met.equals("BindByDept")) {
			return BindByDept();
		}
		// ORIGINAL LINE: case "BindByStation":
		else if (Met.equals("BindByStation")) {
			return BindByStation();
		}
		// ORIGINAL LINE: case "BindBySQL":
		else if (Met.equals("BindBySQL")) {
			return BindBySQL();
		} else {
			return "";
		}
	}

	public final String BindByDept() {
		// string sqlGroup = "SELECT No,Name FROM Port_Dept WHERE No IN (SELECT
		// FK_Dept FROM WF_NodeDept WHERE FK_Node='" + MySelector.NodeID + "')";
		// string sqlDB = "SELECT No,Name, FK_Dept FROM Port_Emp WHERE FK_Dept
		// IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node='" +
		// MySelector.NodeID + "')";

		// DataTable dtGroup = DBAccess.RunSQLReturnTable(sqlGroup);
		// DataTable dtDB = DBAccess.RunSQLReturnTable(sqlDB);

		// if (this.MySelector.SelectorDBShowWay == SelectorDBShowWay.Table)
		// this.BindBySQL_Table(dtGroup, dtDB);
		// else
		// this.BindBySQL_Tree(dtGroup, dtDB);

		String BindByDeptSql = String.format(
				"SELECT  No,Name,ParentNo,'1' IsParent  FROM Port_Dept WHERE No IN (SELECT "
						+ "FK_Dept FROM WF_NodeDept WHERE FK_Node=%1$s) or ParentNo=0 union SELECT No,Name,FK_Dept "
						+ "as ParentNo,'0' IsParent FROM Port_Emp WHERE FK_Dept IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node=%1$s)",
				mySelector.getNodeID());

		DdlEmpSql = String.format(
				"SELECT No,Name FROM Port_Emp WHERE FK_Dept IN (SELECT FK_Dept FROM WF_NodeDept WHERE FK_Node=%1$s)",
				mySelector.getNodeID());

		DataTable BindByDeptDt = DBAccess.RunSQLReturnTable(BindByDeptSql);
		DataTable ParDt = DBAccess.RunSQLReturnTable("select No from Port_Dept where ParentNo='0'");
		for (DataRow r : BindByDeptDt.Rows) {
			if (r.get("IsParent").toString().equals("1") && !r.get("ParentNo").toString().equals("0")) {
				r.setValue("ParentNo", ParDt.Rows.get(0).getValue(0).toString());
			}
		}
		return GetTreeJsonByTable(BindByDeptDt, "NO", "NAME", "ParentNo", "0", "IsParent", "");
	}

	/**
	 * 按table方式.
	 * 
	 */
	public final void BindBySQL_Table(DataTable dtGroup, DataTable dtObj) {
		int col = 4;
		appendPub1(AddTable("style='border:0px;width:100%'"));
		for (DataRow drGroup : dtGroup.Rows) {
			String ctlIDs = "";
			String groupNo = drGroup.getValue(0).toString();

			// 增加全部选择.
			appendPub1(AddTR());
			CheckBox cbx = new CheckBox();
			cbx.setId("CBs_" + drGroup.getValue(0).toString());
			cbx.setText(drGroup.getValue(1).toString());
			appendPub1(AddTDTitle("align=left", cbx.toString()));
			appendPub1(AddTREnd());

			appendPub1(AddTR());
			appendPub1(AddTDBegin("nowarp=false"));

			appendPub1(AddTable("style='border:0px;width:100%'"));
			int colIdx = -1;
			for (DataRow drObj : dtObj.Rows) {
				String no = drObj.getValue(0).toString();
				String name = drObj.getValue(1).toString();
				String group = drObj.getValue(2).toString();
				if (!group.trim().equals(groupNo.trim())) {
					continue;
				}

				colIdx++;
				if (colIdx == 0) {
					appendPub1(AddTR());
				}

				CheckBox cb = new CheckBox();
				cb.setId("CB_" + no);
				ctlIDs += cb.getId() + ",";
				cb.attributes.put("onclick", "isChange=true;");
				cb.setText(name);
				cb.setChecked(true);
				if (cb.getChecked()) {
					cb.setText("<font color=green>" + cb.getText() + "</font>");
				}
				appendPub1(AddTD(cb.toString()));
				if (col - 1 == colIdx) {
					appendPub1(AddTREnd());
					colIdx = -1;
				}
			}
			cbx.attributes.put("onclick", "SetSelected(this,'" + ctlIDs + "')");

			if (colIdx != -1) {
				while (colIdx != col - 1) {
					colIdx++;
					appendPub1(AddTD());
				}
				appendPub1(AddTREnd());
			}
			appendPub1(AddTableEnd());
			appendPub1(AddTDEnd());
			appendPub1(AddTREnd());
		}
		appendPub1(AddTableEnd());

		this.BindEnd();
	}

	public final void BindBySQL_Tree(DataTable dtGroup, DataTable dtDB) {
	}

	public final String BindByStation() {
		return GetTreeJsonByTable(this.GetTable(), "No", "Name", "ParentNo", "0", "IsParent", "");

		// DataTable dt = this.GetTable(); //获取人员列表。
		// SelectAccpers accps = new SelectAccpers();
		// accps.QueryAccepter(this.FK_Node, WebUser.No, this.WorkID);

		// Dept dept = new Dept();
		// string fk_dept = "";
		// string info = "";

		// if (IsMultiple)
		// this.Pub1.AddTable("width=400px");
		// else
		// this.Pub1.AddTable("width=100%");

		// if (WebUser.FK_Dept.Length > 2)
		// {
		// if (this.FK_Dept == WebUser.FK_Dept)
		// info = "<b><a href='Accepter.aspx?ToNode=" + this.ToNode + "&WorkID="
		// + this.WorkID + "&FK_Node=" + this.FK_Node + "&type=1&FK_Dept=" +
		// WebUser.FK_Dept.Substring(0, WebUser.FK_Dept.Length - 2) +
		// "'>上一级部门人员</b></a>|<b><a href='Accepter.aspx?ToNode=" + this.ToNode +
		// "&WorkID=" + this.WorkID + "&FK_Node=" + this.FK_Node +
		// "&type=1&FK_Dept=" + this.FK_Dept + "&IsNextDept=1'
		// >下一级部门人员</b></a>";
		// else
		// info = "<b><a href='Accepter.aspx?ToNode=" + this.ToNode + "&WorkID="
		// + this.WorkID + "&FK_Node=" + this.FK_Node + "&type=1&FK_Dept=" +
		// WebUser.FK_Dept + "'>本部门人员</a></b>";
		// }
		// else
		// {
		// info = "<b><a href='Accepter.aspx?ToNode=" + this.ToNode + "&WorkID="
		// + this.WorkID + "&FK_Node=" + this.FK_Node + "&type=1&FK_Dept=" +
		// WebUser.FK_Dept + "'>本部门人员</a> | <a href='Accepter.aspx?ToNode=" +
		// this.ToNode + "&WorkID=" + this.WorkID + "&FK_Node=" + this.FK_Node +
		// "&type=1&FK_Dept=" + this.FK_Dept + "&IsNextDept=1'
		// >下一级部门人员</b></a>";
		// }

		// Node toNode = new Node(this.ToNode);
		// this.Pub1.AddCaptionLeft("<span style='color:red'>到达节点:[" +
		// toNode.Name + "]</span>");
		// this.Pub1.AddCaptionLeft("可选择范围:" + dt.Rows.Count + " 位." + info);

		// if (dt.Rows.Count > 50)
		// {
		// /*多于一定的数，就显示导航。*/
		// this.Pub1.AddTRSum();
		// this.Pub1.Add("<TD class=BigDoc colspan=5>");
		// foreach (DataRow dr in dt.Rows)
		// {
		// if (fk_dept != dr["FK_Dept"].ToString())
		// {
		// fk_dept = dr["FK_Dept"].ToString();
		// dept = new Dept(fk_dept);
		// dr["DeptName"] = dept.Name;
		// this.Pub1.Add("<a href='#d" + dept.No + "' >" + dept.Name +
		// "</a>&nbsp;");
		// }
		// }
		// this.Pub1.AddTDEnd();
		// this.Pub1.AddTREnd();
		// }

		// int idx = -1;
		// bool is1 = false;
		// foreach (DataRow dr in dt.Rows)
		// {
		// idx++;
		// if (fk_dept != dr["FK_Dept"].ToString())
		// {
		// switch (idx)
		// {
		// case 0:
		// break;
		// case 1:
		// this.Pub1.AddTD();
		// this.Pub1.AddTD();
		// this.Pub1.AddTD();
		// this.Pub1.AddTD();
		// this.Pub1.AddTREnd();
		// break;
		// case 2:
		// this.Pub1.AddTD();
		// this.Pub1.AddTD();
		// this.Pub1.AddTD();
		// this.Pub1.AddTREnd();
		// break;
		// case 3:
		// this.Pub1.AddTD();
		// this.Pub1.AddTD();
		// this.Pub1.AddTREnd();
		// break;
		// case 4:
		// this.Pub1.AddTD();
		// this.Pub1.AddTREnd();
		// break;
		// default:
		// throw new Exception("error");
		// }

		// this.Pub1.AddTRSum();
		// fk_dept = dr["FK_Dept"].ToString();
		// string deptName = dr["DeptName"].ToString();
		// this.Pub1.AddTD("colspan=5 aligen=left class=FDesc ", "<a name='d" +
		// dept.No + "'>" + deptName + "</a>");
		// this.Pub1.AddTREnd();
		// is1 = false;
		// idx = 0;
		// }

		// string no = dr["No"].ToString();
		// string name = dr["Name"].ToString();

		// CheckBox cb = new CheckBox();
		// cb.Text = BP.WF.Glo.DealUserInfoShowModel(no, name);

		// cb.ID = "CB_" + no;
		// if (accps.Contains("FK_Emp", no))
		// cb.Checked = true;

		// switch (idx)
		// {
		// case 0:
		// is1 = this.Pub1.AddTR(is1);
		// this.Pub1.AddTD(cb);
		// break;
		// case 1:
		// case 2:
		// case 3:
		// this.Pub1.AddTD(cb);
		// break;
		// case 4:
		// this.Pub1.AddTD(cb);
		// this.Pub1.AddTREnd();
		// idx = -1;
		// break;
		// default:
		// throw new Exception("error");
		// }
		// this.Pub1.AddTREnd();
		// }
		// this.Pub1.AddTableEnd();

		// this.BindEnd();
	}

	/**
	 * 处理绑定结束
	 * 
	 */
	public final void BindEnd() {
		Button btn = new Button();
		if (this.getIsWinOpen() == 1) {
			btn.setText("确定并关闭");
			btn.setId("Btn_Save");
			btn.setCssClass("Btn");
			btn.setType("submit");
			appendPub1(btn.toString());
		} else {
			btn = new Button();
			btn.setText("确定并发送");
			btn.setId("Btn_Save");
			btn.setCssClass("Btn");
			btn.setType("submit");
			appendPub1(btn.toString());

			btn = new Button();
			btn.setText("取消并返回");
			btn.setId("Btn_Cancel");
			btn.setCssClass("Btn");
			// btn.Click += new EventHandler(btn_Save_Click);
			appendPub1(btn.toString());
		}

		CheckBox mycb = new CheckBox();
		mycb.setId("CB_IsSetNextTime");
		mycb.setText("以后发送都按照本次设置计算");
		appendPub1(mycb.toString());

		// CheckBox mycb = new CheckBox();
		// mycb.ID = "CB_IsSetNextTime";
		// mycb.Text = "以后发送都按照本次设置计算";
		// mycb.Checked = accps.IsSetNextTime;
		// this.Pub1.Add(mycb);

	}

	// 保存
	public final void saveMet() {
		String getSaveNo = initParameterValue("getSaveNo");

		// 此处做判断,删除checked的部门数据
		String[] getSaveNoArray = getSaveNo.split("[,]", -1);
		java.util.ArrayList<String> getSaveNoList = new java.util.ArrayList<String>();

		for (int i = 0; i < getSaveNoArray.length; i++) {
			getSaveNoList.add(getSaveNoArray[i]);
		}

		getSaveNo = "";
		String ziFu = ",";
		for (int i = 0; i < getSaveNoList.size(); i++) {
			if (i == getSaveNoList.size() - 1) {
				ziFu = "";
			}
			getSaveNo += (getSaveNoList.get(i) + ziFu);
		}

		// 设置人员.
		BP.WF.Dev2Interface.WorkOpt_SetAccepter(this.getToNode(), this.getWorkID(), this.getFID(), getSaveNo, false);

		if (this.getIsWinOpen() == 0) {
			/* 如果是 MyFlow.aspx 调用的, 就要调用发送逻辑. */
			// this.DoSend();
			return;
		}

		if (initParameterValue("IsEUI") == null) {
			// this.WinClose();
			PubClass.ResponseWriteScript("window.close();");
		} else {
			PubClass.ResponseWriteScript("window.parent.$('windowIfrem').window('close');");

		}

		/// #warning 刘文辉 保存收件人后调用发送按钮

		// BtnLab nd = new BtnLab(this.FK_Node);
		// if (nd.SelectAccepterEnable == 1)
		// {
		// if (initParameterValue("IsEUI"] == null)
		// {

		// /*如果是1不说明直接关闭它.*/
		// this.WinClose();
		// //ScriptManager.RegisterStartupScript(this.Page, typeof(Page), "",
		// "this.close();", true);
		// }
		// else
		// {
		// PubClass.ResponseWriteScript("window.parent.$('windowIfrem').window('close');");

		// }
		// }
		// else
		// {
		// ScriptManager.RegisterStartupScript(this.Page, typeof(Page), "",
		// "send();", true);
		// }
	}

	/**
	 * 保存
	 * 
	 * @param sender
	 * @param e
	 */
	// private void btn_Save_Click(Object sender, EventArgs e)
	// {
	// Button btn = (Button)sender;
	// if (btn.getId().equals("Btn_Cancel"))
	// {
	// String url = "../MyFlow.jsp?FK_Flow=" + this.getFK_Flow() + "&FK_Node=" +
	// this.getFK_Node() + "&WorkID=" + this.getWorkID() + "&FID=" +
	// this.getFID();
	// get_response().sendRedirect(url);
	// return;
	// }
	//
	// //DataTable dt = this.GetTable();
	// String emps = "";
	// for (Control ctl : this.Pub1.Controls)
	// {
	// CheckBox cb = (CheckBox)((ctl instanceof CheckBox) ? ctl : null);
	// if (cb == null || cb.ID == null || cb.ID.Contains("CBs_") ||
	// cb.ID.equals("CB_IsSetNextTime"))
	// {
	// continue;
	// }
	//
	// if (cb.Checked == false)
	// {
	// continue;
	// }
	// emps += cb.ID.Replace("CB_", "") + ",";
	// }
	//
	// if (emps.length() < 2)
	// {
	// this.Alert("您没有选择人员。");
	// return;
	// }
	//
	// //获取是否下次自动设置.
	// boolean isNextTime = this.Pub1.GetCBByID("CB_IsSetNextTime").Checked;
	//
	// //设置人员.
	// BP.WF.Dev2Interface.WorkOpt_SetAccepter(this.getToNode(),
	// this.getWorkID(), this.getFID(), emps, isNextTime);
	//
	// if (this.getIsWinOpen() == 0)
	// {
	// //如果是 MyFlow.aspx 调用的, 就要调用发送逻辑.
	// this.DoSend();
	// return;
	// }
	//
	//
	// if (initParameterValue("IsEUI"] == null)
	// {
	// this.WinClose();
	// }
	// else
	// {
	// PubClass.ResponseWriteScript("window.parent.$('windowIfrem').window('close');");
	// }
	//
	//// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///// #warning 刘文辉 保存收件人后调用发送按钮
	//
	// BtnLab nd = new BtnLab(this.getFK_Node());
	// if (nd.SelectAccepterEnable == 1)
	// {
	// if (initParameterValue("IsEUI"] == null)
	// {
	//
	// //如果是1不说明直接关闭它.
	// this.WinClose();
	// //ScriptManager.RegisterStartupScript(this.Page, typeof(Page), "",
	// "this.close();", true);
	// }
	// else
	// {
	// PubClass.ResponseWriteScript("window.parent.$('windowIfrem').window('close');");
	//
	// }
	// }
	// else
	// {
	// ScriptManager.RegisterStartupScript(this.Page, Page.class, "", "send();",
	// true);
	// }
	// }

	public final void DoSend() {
		// 以下代码是从 MyFlow.aspx Send 方法copy 过来的，需要保持业务逻辑的一致性，所以代码需要保持一致.

		Node nd = new Node(this.getFK_Node());
		Work wk = nd.getHisWork();
		wk.setOID(this.getWorkID());
		wk.Retrieve();

		WorkNode firstwn = new WorkNode(wk, nd);
		String msg = "";
		try {
			msg = firstwn.NodeSend().ToMsgOfHtml();
		} catch (RuntimeException exSend) {
			appendPub1(AddFieldSetGreen("错误"));
			appendPub1(exSend.getMessage().replace("@@", "@").replace("@", "<BR>@"));
			appendPub1(AddFieldSetEnd());
			return;
		}

		/// #region 处理通用的发送成功后的业务逻辑方法，此方法可能会抛出异常.
		try {
			// 处理通用的发送成功后的业务逻辑方法，此方法可能会抛出异常.
			BP.WF.Glo.DealBuinessAfterSendWork(this.getFK_Flow(), this.getWorkID(), this.getDoFunc(), getWorkIDs(),
					this.getCFlowNo(), 0, null);
		} catch (RuntimeException ex) {
			this.ToMsg(msg, ex.getMessage());
			return;
		}
		/// #endregion 处理通用的发送成功后的业务逻辑方法，此方法可能会抛出异常.

		// 处理转向问题.
		switch (firstwn.getHisNode().getHisTurnToDeal()) {
		case SpecUrl:
			String myurl = firstwn.getHisNode().getTurnToDealDoc().toString();
			if (myurl.contains("&") == false) {
				myurl += "?1=1";
			}
			Attrs myattrs = firstwn.getHisWork().getEnMap().getAttrs();
			Work hisWK = firstwn.getHisWork();
			for (Attr attr : Attrs.convertAttrs(myattrs)) {
				if (myurl.contains("@") == false) {
					break;
				}
				myurl = myurl.replace("@" + attr.getKey(), hisWK.GetValStrByKey(attr.getKey()));
			}
			if (myurl.contains("@")) {
				throw new RuntimeException("流程设计错误，在节点转向url中参数没有被替换下来。Url:" + myurl);
			}

			myurl += "&FromFlow=" + this.getFK_Flow() + "&FromNode=" + this.getFK_Node() + "&PWorkID="
					+ this.getWorkID() + "&UserNo=" + WebUser.getNo() + "&SID=" + WebUser.getSID();
			try {
				get_response().sendRedirect(myurl);
			} catch (IOException e1) {
				logger.info("ERROR", e1);
			}
			return;
		case TurnToByCond:
			TurnTos tts = new TurnTos(this.getFK_Flow());
			if (tts.size() == 0) {
				throw new RuntimeException("@您没有设置节点完成后的转向条件。");
			}
			for (TurnTo tt : TurnTos.convertTurnTos(tts)) {
				tt.HisWork = firstwn.getHisWork();
				if (tt.getIsPassed() == true) {
					String url = tt.getTurnToURL().toString();
					if (url.contains("&") == false) {
						url += "?1=1";
					}
					Attrs attrs = firstwn.getHisWork().getEnMap().getAttrs();
					Work hisWK1 = firstwn.getHisWork();
					for (Attr attr : attrs) {
						if (url.contains("@") == false) {
							break;
						}
						url = url.replace("@" + attr.getKey(), hisWK1.GetValStrByKey(attr.getKey()));
					}
					if (url.contains("@")) {
						throw new RuntimeException("流程设计错误，在节点转向url中参数没有被替换下来。Url:" + url);
					}

					url += "&PFlowNo=" + this.getFK_Flow() + "&FromNode=" + this.getFK_Node() + "&PWorkID="
							+ this.getWorkID() + "&UserNo=" + WebUser.getNo() + "&SID=" + WebUser.getSID();
					try {
						get_response().sendRedirect(url);
					} catch (IOException e) {
						logger.info("ERROR", e);
					}
					return;
				}
			}
			/// #warning 为上海修改了如果找不到路径就让它按系统的信息提示。
			this.ToMsg(msg, "info");
			// throw new Exception("您定义的转向条件不成立，没有出口。");
			break;
		default:
			this.ToMsg(msg, "info");
			break;
		}
		return;
	}

	public final void ToMsg(String msg, String type) {
		get_request().setAttribute("info", msg);
		// this.Application["info" + WebUser.No] = msg;

		try {
			BP.WF.Glo.setSessionMsg(msg);
		} catch (Exception e) {
			logger.info("ERROR", e);
		}
		try {
			get_response().sendRedirect("./../MyFlowInfo.jsp?FK_Flow=" + this.getFK_Flow() + "&FK_Type=" + type
					+ "&FK_Node=" + this.getFK_Node() + "&WorkID=" + this.getWorkID());
		} catch (IOException e) {
			logger.info("ERROR", e);
		}
	}

	/**
	 * 根据DataTable生成Json树结构
	 * 
	 * @param tabel
	 *            数据源
	 * @param idCol
	 *            ID列
	 * @param txtCol
	 *            Text列
	 * @param rela
	 *            关系字段
	 * @param pId
	 *            父ID
	 * @return easyui tree json格式
	 */
	private StringBuilder treeResult = new StringBuilder();
	private StringBuilder treesb = new StringBuilder();

	public final String GetTreeJsonByTable(DataTable tabel, String idCol, String txtCol, String rela, Object pId,
			String IsParent, String CheckedString) {
		String treeJson = "";
		treeResult.append(treesb.toString());

		treesb = new StringBuilder();
		if (tabel.Rows.size() > 0) {
			treesb.append("[");
			Map<String, Object> filer = new HashMap<String, Object>();
			filer.put(rela, pId);

			if (pId.toString().equals("")) {
				// filer = String.format("%1$s is null", rela);
				filer.put(rela, null);
			} else {
				// filer = String.format("%1$s='%2$s'", rela, pId);
				filer.put(rela, pId);
			}
			try {
				List<DataRow> rows = tabel.Select(filer);
				// DataRow[] rows = tabel.Select(filer);
				if (rows.size() > 0) // 修改
				{

					for (DataRow row : rows) {
						String deptNo = (String) (row.getValue(idCol) instanceof String ? row.getValue(idCol) : null);
						HashMap<String, Object> filerMap = new HashMap<String, Object>();
						filerMap.put(rela, row.getValue(idCol));

						if (treeResult.length() == 0) {
							treesb.append("{\"id\":\"" + row.getValue(idCol) + "\",\"text\":\"" + row.getValue(txtCol)
									+ "\",\"attributes\":{\"IsParent\":\"" + row.getValue(IsParent) + "\"}"
									+ ",\"checked\":" + CheckedString.contains("," + row.getValue(idCol) + ",")
									+ ",\"state\":\"open\"");
						} else if (tabel.Select(filerMap).size() > 0) {
							treesb.append("{\"id\":\"" + row.getValue(idCol) + "\",\"text\":\"" + row.getValue(txtCol)
									+ "\",\"attributes\":{\"IsParent\":\"" + row.getValue(IsParent) + "\"}"
									+ ",\"checked\":" + CheckedString.contains("," + row.getValue(idCol) + ",")
									+ ",\"state\":\"open\"");
						} else {
							treesb.append("{\"id\":\"" + row.getValue(idCol) + "\",\"text\":\"" + row.getValue(txtCol)
									+ "\",\"attributes\":{\"IsParent\":\"" + row.getValue(IsParent) + "\"}"
									+ ",\"checked\":" + CheckedString.contains("," + row.getValue(idCol) + ","));
						}

						if (tabel.Select(filerMap).size() > 0) {
							treesb.append(",\"children\":");
							GetTreeJsonByTable(tabel, idCol, txtCol, rela, row.getValue(idCol), IsParent,
									CheckedString);
							treeResult.append(treesb.toString());
							treesb = new StringBuilder();
						}
						treeResult.append(treesb.toString());
						treesb = new StringBuilder();
						treesb.append("},");
					}
					treesb = treesb.deleteCharAt(treesb.length() - 1);
				}
				treesb.append("]");
				treeResult.append(treesb.toString());
				treeJson = treeResult.toString();
				treesb = new StringBuilder();
			} catch (Exception e) {
				logger.info("ERROR", e);
			}
		}
		return treeJson;
	}
}

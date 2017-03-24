package BP.WF.DTS;

import BP.DA.*;

import BP.Port.*;
import BP.En.*;
import BP.Sys.*;
import BP.Sys.Frm.FrmLab;
import BP.Sys.Frm.FrmLabs;
import BP.Sys.Frm.FrmLine;
import BP.Sys.Frm.FrmLines;

/** 
 重新生成标题 的摘要说明
 
*/
public class ClearRepLineLab extends Method
{
	/** 
	 不带有参数的方法
	 
	*/
	public ClearRepLineLab()
	{
		this.Title = "清除重复的表单中的Line Lab 数据";
		this.Help = "由于表单模板以前的Bug导致的标签与线重复数据。";
	}
	/** 
	 设置执行变量
	 
	 @return 
	*/
	@Override
	public void Init()
	{

	}
	/** 
	 当前的操纵员是否可以执行这个方法
	 
	*/
	@Override
	public boolean getIsCanDo()
	{
		return true;
	}
	/** 
	 执行
	 
	 @return 返回执行结果
	*/
	@Override
	public Object Do()
	{
		FrmLines ens = new FrmLines();
		ens.RetrieveAllFromDBSource();
		String sql = "";
		for (FrmLine item : FrmLines.convertFrmLines(ens))
		{
			sql = "DELETE FROM " + item.getEnMap().getPhysicsTable() + " WHERE FK_MapData='" + item.getFK_MapData() + "' AND X=" + item.getX() + " AND Y=" + item.getY() + " and x1=" + item.getX1() + " and x2=" + item.getX2() + " and y1=" + item.getY1() + " and y2=" + item.getY2();
			DBAccess.RunSQL(sql);
			item.setMyPK(String.valueOf(DBAccess.GenerOIDByGUID())) ;
			item.Insert();
		}


		FrmLabs labs = new FrmLabs();
		labs.RetrieveAllFromDBSource();
		for (FrmLab item : FrmLabs.convertFrmLabs(labs))
		{
			sql = "DELETE FROM " + item.getEnMap().getPhysicsTable() + " WHERE FK_MapData='" + item.getFK_MapData() + "' and x=" + item.getX() + " and y=" + item.getY() + " and Text='" + item.getText() + "'";
			DBAccess.RunSQL(sql);
			item.setMyPK (String.valueOf(DBAccess.GenerOIDByGUID()));
			item.Insert();
		}
		return "删除成功";
	}
}
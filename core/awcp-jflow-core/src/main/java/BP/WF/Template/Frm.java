package BP.WF.Template;

import BP.En.EntityNoName;
import BP.En.Map;
import BP.Sys.FrmType;
import BP.Sys.MapDataAttr;
import BP.WF.FormRunType;

/** 
 表单
 
*/
public class Frm extends EntityNoName
{

		
	public FrmNode HisFrmNode = null;
	public final String getPTable()
	{
		return this.GetValStringByKey(FrmAttr.PTable);
	}
	public final void setPTable(String value)
	{
		this.SetValByKey(FrmAttr.PTable, value);
	}
	public final String getFK_Flow11()
	{
		return this.GetValStringByKey(FrmAttr.FK_Flow);
	}
	public final void setFK_Flow11(String value)
	{
		this.SetValByKey(FrmAttr.FK_Flow, value);
	}
	public final String getURL()
	{
		return this.GetValStringByKey(FrmAttr.URL);
	}
	public final void setURL(String value)
	{
		this.SetValByKey(FrmAttr.URL, value);
	}
	public final FrmType getHisFrmType()
	{
		return FrmType.forValue(this.GetValIntByKey(FrmAttr.FrmType));
	}
	public final void setHisFrmType(FrmType value)
	{
		this.SetValByKey(FrmAttr.FrmType, value);
	}
	public final void setHisFormRunType(FormRunType value)
	{
		this.SetValByKey(FrmAttr.FrmType, value.getValue());
	}
	/** 
	 Frm
	 
	*/
	public Frm()
	{
	}
	/** 
	 Frm
	 
	 @param no
	*/
	public Frm(String no)
	{
		super(no);

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

		Map map = new Map("Sys_MapData", "表单库");
		map.Java_SetCodeStruct("4");

		map.AddTBStringPK(FrmAttr.No, null, null, true, true, 1, 200, 4);
		map.AddTBString(FrmAttr.Name, null, null, true, false, 0, 50, 10);
		map.AddTBString(FrmAttr.FK_Flow, null, "独立表单属性:FK_Flow", true, false, 0, 50, 10);
		 //   map.AddDDLSysEnum(FrmAttr.FrmType, 0, "独立表单属性:运行类型", true, false, FrmAttr.FrmType);

			//表单的运行类型.
		map.AddDDLSysEnum(FrmAttr.FrmType, BP.Sys.FrmType.FreeFrm.getValue(), "表单类型",true, false, FrmAttr.FrmType);

		map.AddTBString(FrmAttr.PTable, null, "物理表", true, false, 0, 50, 10);
		map.AddTBInt(FrmAttr.DBURL, 0, "DBURL", true, false);

			// 如果是个嵌入式表单.
		map.AddTBString(FrmAttr.URL, null, "Url", true, false, 0, 50, 10);

			//表单类别.
		map.AddTBString(MapDataAttr.FK_FrmSort, "01", "表单类别", true, false, 0, 500, 20);

		map.AddTBInt(BP.Sys.MapDataAttr.FrmW, 900, "表单宽度", true, false);
		map.AddTBInt(BP.Sys.MapDataAttr.FrmH, 1200, "表单高度", true, false);

		this.set_enMap(map);
		return this.get_enMap();
	}
	public final int getFrmW()
	{
		return this.GetValIntByKey(MapDataAttr.FrmW);
	}
	public final int getFrmH()
	{
		return this.GetValIntByKey(BP.Sys.MapDataAttr.FrmH);
	}


		///#endregion
}
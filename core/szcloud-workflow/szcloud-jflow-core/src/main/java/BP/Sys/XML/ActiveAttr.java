package BP.Sys.XML;

import BP.XML.XmlEn;
import BP.XML.XmlEns;

/** 
 ActiveAttr 的摘要说明。
 考核过错行为的数据元素
 1，它是 ActiveAttr 的一个明细。
 2，它表示一个数据元素。
 
*/
public class ActiveAttr extends XmlEn
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 选择这个属性时间需要的条件
	 
	*/
	public final String getCondition()
	{
		return this.GetValStringByKey(ActiveAttrAttr.Condition);
	}
	public final String getAttrKey()
	{
		return this.GetValStringByKey(ActiveAttrAttr.AttrKey);
	}
	public final String getAttrName()
	{
		return this.GetValStringByKey(ActiveAttrAttr.AttrName);
	}
	public final String getExp()
	{
		return this.GetValStringByKey(ActiveAttrAttr.Exp);
	}
	public final String getExpApp()
	{
		return this.GetValStringByKey(ActiveAttrAttr.ExpApp);
	}
	public final String getFor()
	{
		return this.GetValStringByKey(ActiveAttrAttr.For);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	public ActiveAttr()
	{
	}
	/** 
	 获取一个实例
	 
	*/
	@Override
	public XmlEns getGetNewEntities()
	{
		return new ActiveAttrs();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
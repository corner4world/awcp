package BP.En;

import java.util.Date;

import BP.DA.Cash;
import BP.DA.DBAccess;
import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.DataColumn;
import BP.DA.DataRow;
import BP.DA.DataSet;
import BP.DA.DataTable;
import BP.DA.DataType;
import BP.DA.Depositary;
import BP.Sys.MapAttr;
import BP.Sys.MapAttrAttr;
import BP.Sys.MapAttrs;
import BP.Sys.MapData;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;
import BP.Web.WebUser;

/**
 * EnMap 的摘要说明。
 */
public class Map
{
	public static final String AttrsOfOneVSM = null;

	public static final Object DTSearchLable = "日期从:";

	// /#region 帮助.
	/**
	 * 增加帮助
	 * 
	 * @param key
	 *            字段
	 * @param url
	 */
	public final void SetHelperUrl(String key, String url)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.HelperUrl = url;
	}
	
	/**
	 * 增加帮助
	 * 
	 * @param key
	 *            字段
	 */
	public final void SetHelperBaidu(String key)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.HelperUrl = "http://www.baidu.com/s?word=ccflow " + attr.getDesc();
	}
	
	/**
	 * 增加帮助
	 * 
	 * @param key
	 *            字段
	 * @param keyword
	 *            关键字
	 */
	public final void SetHelperBaidu(String key, String keyword)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.HelperUrl = "http://www.baidu.com/s?word=" + keyword;
	}
	
	/**
	 * 增加帮助
	 * 
	 * @param key
	 *            字段
	 * @param context
	 *            连接
	 */
	public final void SetHelperAlert(String key, String context)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.HelperUrl = "javascript:alert('" + context + "')";
	}
	
	// 帮助.
	
	// 与xml 文件操作有关系
	/**
	 * xml 文件的位置
	 */
	public String XmlFile = null;
	// 与xml 文件操作有关系
	
	private boolean _IsAllowRepeatNo;
	
	public final boolean getIsAllowRepeatNo()
	{
		return _IsAllowRepeatNo;
	}
	
	public final void setIsAllowRepeatNo(boolean value)
	{
		_IsAllowRepeatNo = value;
	}
	
	// chuli
	/**
	 * 查询语句(为了避免过多的资源浪费,一次性生成多次使用)
	 */
	public String SelectSQL = null;
	/**
	 * 是否是简单的属性集合 这里是处理外键的问题，在系统的批量运行过程中太多的外键就会影响效率。
	 */
	public boolean IsSimpleAttrs = false;
	
	/**
	 * 设置为简单的
	 */
	public final Attrs SetToSimple()
	{
		Attrs attrs = new Attrs();
		for (Attr attr : this._attrs)
		{
			if (attr.getMyFieldType() == FieldType.PK
					|| attr.getMyFieldType() == FieldType.PKEnum
					|| attr.getMyFieldType() == FieldType.PKFK)
			{
				attrs.Add(new Attr(attr.getKey(), attr.getField(), attr
						.getDefaultVal(), attr.getMyDataType(), true, attr
						.getDesc()));
			} else
			{
				attrs.Add(new Attr(attr.getKey(), attr.getField(), attr
						.getDefaultVal(), attr.getMyDataType(), false, attr
						.getDesc()));
			}
		}
		return attrs;
	}
	
	// 关于缓存问题
	public String _FK_MapData = null;
	
	public final String getFK_MapData()
	{
		if (_FK_MapData == null)
		{
			return this.getPhysicsTable();
		}
		return _FK_MapData;
	}
	
	public final void setFK_MapData(String value)
	{
		_FK_MapData = value;
	}
	
	/**
	 * 显示方式
	 */
	private FormShowType _FormShowType = FormShowType.NotSet;
	
	/**
	 * 存放位置OfEntity
	 */
	public final FormShowType getFormShowType()
	{
		return _FormShowType;
	}
	
	public final void setFormShowType(FormShowType value)
	{
		_FormShowType = value;
	}
	
	/**
	 * 存放位置
	 */
	private Depositary _DepositaryOfEntity = Depositary.None;
	
	/**
	 * 存放位置OfEntity
	 */
	public final Depositary getDepositaryOfEntity()
	{
		return _DepositaryOfEntity;
	}
	
	public final void setDepositaryOfEntity(Depositary value)
	{
		_DepositaryOfEntity = value;
	}
	
	/** 
	 
	 		
	*/
	private Depositary _DepositaryOfMap = Depositary.Application;
	
	/**
	 * 存放位置
	 */
	public final Depositary getDepositaryOfMap()
	{
		return _DepositaryOfMap;
	}
	
	public final void setDepositaryOfMap(Depositary value)
	{
		_DepositaryOfMap = value;
	}
	
	public final void Java_SetEnType(EnType value)
	{
		this._EnType = value;
	}
	public Map(String physicsTable,String dbDesc )
	{
		//this.setEnDesc(dbDesc);
		//this.setPhysicsTable(physicsTable);
		this._EnDesc = dbDesc;
		this._PhysicsTable = physicsTable;
	}
	public final void Java_SetDepositaryOfMap(Depositary value)
	{
		_DepositaryOfMap = value;
	}
	public final void Java_SetDepositaryOfEntity(Depositary value)
	{
		_DepositaryOfEntity = value;
	}
	public final void Java_SetCodeStruct(String value)
	{
		this._CodeStruct = value;
		this.setIsAutoGenerNo(true);
	}
	// 查询属性处理
	
	// 非枚举值与外键条件查询
	private AttrsOfSearch _attrsOfSearch = null;
	
	/**
	 * 查找属性
	 */
	public final AttrsOfSearch getAttrsOfSearch()
	{
		if (this._attrsOfSearch == null)
		{
			this._attrsOfSearch = new AttrsOfSearch();
		}
		return this._attrsOfSearch;
	}
	
	/**
	 * 得到全部的Attrs
	 * 
	 * @return
	 */
	public final Attrs GetChoseAttrs(Entity en)
	{
		return BP.Sys.CField.GetMyAttrs(en.getGetNewEntities(), en.getEnMap());
	}
	
	public final Attrs GetChoseAttrs(Entities ens)
	{
		return BP.Sys.CField.GetMyAttrs(ens, this);
	}
	
	// 关于枚举值与外键查找条件
	/**
	 * 查找的attrs
	 */
	private AttrSearchs _SearchAttrs = null;
	
	/**
	 * 查找的attrs
	 */
	public final AttrSearchs getSearchAttrs()
	{
		if (this._SearchAttrs == null)
		{
			this._SearchAttrs = new AttrSearchs();
		}
		return this._SearchAttrs;
	}
	
	public final void AddHidden(String refKey, String symbol, String val)
	{
		AttrOfSearch aos = new AttrOfSearch("K"
				+ this.getAttrsOfSearch().size(), refKey, refKey, symbol, val,
				0, true);
		this.getAttrsOfSearch().Add(aos);
	}
	
	public final void AddSearchOfAttr(String refKey, String symbol, String val,
			int tbwidth)
	{
		Attr attr = this.GetAttrByKey(refKey);
		AttrOfSearch aos = new AttrOfSearch(attr, symbol, val, tbwidth, false);
		this.getAttrsOfSearch().Add(aos);
	}
	
	/**
	 * 加入查找属性.必须是外键盘/枚举类型/boolen.
	 * 
	 * @param key
	 *            key
	 */
	public final void AddSearchAttr(String key)
	{
		Attr attr = this.GetAttrByKey(key);
		if (attr.getKey().equals("FK_Dept"))
		{
			this.getSearchAttrs().Add(attr, false, null);
		} else
		{
			this.getSearchAttrs().Add(attr, true, null);
		}
	}
	
	/**
	 * 加入查找属性.必须是外键盘/枚举类型/boolen.
	 * 
	 * @param key
	 *            键值
	 * @param isShowSelectedAll
	 *            是否显示全部
	 * @param relationalDtlKey
	 *            级联子菜单字段
	 */
	public final void AddSearchAttr(String key, boolean isShowSelectedAll,
			String relationalDtlKey)
	{
		Attr attr = this.GetAttrByKey(key);
		this.getSearchAttrs().Add(attr, isShowSelectedAll, relationalDtlKey);
	}
	
	public final void AddSearchAttrChild(String key, boolean isShowSelectedAll,
			String reationChildKey)
	{
		Attr attr = this.GetAttrByKey(key);
		this.getSearchAttrs()
				.addChild(attr, isShowSelectedAll, reationChildKey);
	}
	
	public final void AddSearchAttrParent(String key,
			boolean isShowSelectedAll, String reationParentKey,
			String refParentKey)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.set_UIRefParentKeyValue(refParentKey);
		this.getSearchAttrs().addParent(attr, isShowSelectedAll,
				reationParentKey);
	}
	
	public final void AddSearchAttrGrandChild(String key,
			boolean isShowSelectedAll, String reationChildKey,
			String reationGrandChildKey)
	{
		Attr attr = this.GetAttrByKey(key);
		this.getSearchAttrs().addGrandChild(attr, isShowSelectedAll,
				reationChildKey, reationGrandChildKey);
	}
	
	public final void AddSearchAttrParentAndChild(String key,
			boolean isShowSelectedAll, String reationParentKey,
			String reationChildKey, String refParentKey)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.set_UIRefParentKeyValue(refParentKey);
		this.getSearchAttrs().addParentAndChild(attr, isShowSelectedAll,
				reationParentKey, reationChildKey);
	}
	
	/**
	 * 加入查找属性.
	 * 
	 * @param attr
	 *            属性
	 */
	public final void AddSearchAttr_del(Attr attr)
	{
		// if (attr.MyFieldType == FieldType.Enum || attr.MyFieldType ==
		// FieldType.PKEnum
		// || attr.MyFieldType == FieldType.FK || attr.MyFieldType ==
		// FieldType.PKFK
		// || attr.MyDataType == DataType.AppBoolean
		// || attr.MyDataType == DataType.AppDate
		// || attr.MyDataType == DataType.AppDateTime)
		// {
		// this.SearchAttrs.Add(attr, true, this.IsAddRefName);
		// }
		// else
		// {
		// throw new Exception("@属性[" + attr.Key + "," + attr.Desc +
		// "]不能添加到查找集合里.因为他不是枚举与外键类型.");
		// }
	}
	
	// 公共方法
	/**
	 * 取得字段
	 * 
	 * @param key
	 *            key
	 * @return field name
	 */
	public final String GetFieldByKey(String key)
	{
		return GetAttrByKey(key).getField();
	}
	
	/**
	 * 取得描述
	 * 
	 * @param key
	 *            key
	 * @return val
	 */
	public final String GetDescByKey(String key)
	{
		return GetAttrByKey(key).getDesc();
	}
	
	/**
	 * 通过一个key 得到它的属性值。
	 * 
	 * @param key
	 *            key
	 * @return attr
	 */
	public final Attr GetAttrByKey(String key)
	{
		for (Attr attr : this.getAttrs())
		{
			if (attr.getKey().toUpperCase().equals(key.toUpperCase()))
			{
				return attr;
			}
		}
		
		if (key == null)
		{
			throw new RuntimeException("@[" + this.getEnDesc()
					+ "] 获取属性key 值不能为空.");
		}
		
		if (this.toString().contains("."))
		{
			System.err.println("@["
					+ this.getEnDesc()
					+ ","
					+ this.getPhysicsTable()
					+ "] 没有找到 key=["
					+ key
					+ "]的属性，请检查Map文件。此问题出错的原因之一是，在设置系统中的一个实体的属性关联这个实体，你在给实体设置信息时没有按照规则书写reftext, refvalue。请核实。");
			throw new RuntimeException(
					"@["
							+ this.getEnDesc()
							+ ","
							+ this.getPhysicsTable()
							+ "] 没有找到 key=["
							+ key
							+ "]的属性，请检查Map文件。此问题出错的原因之一是，在设置系统中的一个实体的属性关联这个实体，你在给实体设置信息时没有按照规则书写reftext, refvalue。请核实。");
		} else
		{
			throw new RuntimeException(
					"@["
							+ this.getEnDesc()
							+ ","
							+ this.getPhysicsTable()
							+ "] 没有找到 key=["
							+ key
							+ "]的属性，请检查Sys_MapAttr表是否有该数据,用SQL执行: SELECT * FROM Sys_MapAttr WHERE FK_MapData='"
							+ this.toString() + "' AND KeyOfEn='" + key
							+ "' 是否可以查询到数据，如果没有可能该字段属性丢失。");
		}
	}
	
	/**
	 * 获得属性.
	 * 
	 * @param key
	 * @return
	 */
	public final Attr GetAttrByBindKey(String key)
	{
		for (Attr attr : this.getAttrs())
		{
			if (attr.getUIBindKey().equals(key))
			{
				return attr;
			}
		}
		if (key == null)
		{
			throw new RuntimeException("@[" + this.getEnDesc()
					+ "] 获取属性key 值不能为空.");
		}
		
		if (this.toString().contains("."))
		{
			throw new RuntimeException(
					"@["
							+ this.getEnDesc()
							+ ","
							+ this.toString()
							+ "] 没有找到 key=["
							+ key
							+ "]的属性，请检查Map文件。此问题出错的原因之一是，在设置系统中的一个实体的属性关联这个实体，你在给实体设置信息时没有按照规则书写reftext, refvalue。请核实。");
		} else
		{
			throw new RuntimeException(
					"@["
							+ this.getEnDesc()
							+ ","
							+ this.toString()
							+ "] 没有找到 key=["
							+ key
							+ "]的属性，请检查Sys_MapAttr表是否有该数据,用SQL执行: SELECT * FROM Sys_MapAttr WHERE FK_MapData='"
							+ this.toString() + "' AND KeyOfEn='" + key
							+ "' 是否可以查询到数据，如果没有可能该字段属性丢失。");
		}
	}
	
	/**
	 * 通过一个key 得到它的属性值。
	 * 
	 * @param key
	 *            key
	 * @return attr
	 */
	public final Attr GetAttrByDesc(String desc)
	{
		for (Attr attr : this.getAttrs())
		{
			if (attr.getDesc().equals(desc))
			{
				return attr;
			}
		}
		if (desc == null)
		{
			throw new RuntimeException("@[" + this.getEnDesc()
					+ "] 获取属性 desc  值不能为空.");
		}
		
		throw new RuntimeException(
				"@["
						+ this.getEnDesc()
						+ "] 没有找到 desc=["
						+ desc
						+ "]的属性，请检查Map文件。此问题出错的原因之一是，在设置系统中的一个实体的属性关联这个实体，你在给实体设置信息时没有按照规则书写reftext, refvalue。请核实。");
	}
	
	// 计算属性
	/**
	 * 取道最大的TB宽度。
	 */
	private int _MaxTBLength = 0;
	
	/**
	 * 最大的TB宽度。
	 */
	public final float getMaxTBLength()
	{
		if (_MaxTBLength == 0)
		{
			for (Attr attr : this.getAttrs())
			{
				if (attr.getUIWidth() > _MaxTBLength)
				{
					_MaxTBLength = (int) attr.getUIWidth();
				}
			}
		}
		return _MaxTBLength;
	}
	
	/**
	 * 物理键盘集合
	 */
	private Attrs _HisPhysicsAttrs = null;
	
	/**
	 * 物理键盘集合
	 */
	public final Attrs getHisPhysicsAttrs()
	{
		if (_HisPhysicsAttrs == null)
		{
			_HisPhysicsAttrs = new Attrs();
			for (Attr attr : this.getAttrs())
			{
				if (attr.getMyFieldType() == FieldType.NormalVirtual
						|| attr.getMyFieldType() == FieldType.RefText)
				{
					continue;
				}
				_HisPhysicsAttrs.Add(attr, false, this.IsAddRefName);
			}
		}
		return _HisPhysicsAttrs;
	}
	
	/**
	 * 他的外键集合
	 */
	private Attrs _HisFKAttrs = null;
	
	/**
	 * 他的外键集合
	 */
	public final Attrs getHisFKAttrs()
	{
		if (_HisFKAttrs == null)
		{
			_HisFKAttrs = new Attrs();
			for (Attr attr : this.getAttrs())
			{
				if (attr.getMyFieldType() == FieldType.FK
						|| attr.getMyFieldType() == FieldType.PKFK)
				{
					_HisFKAttrs.Add(attr, false, false);
				}
			}
		}
		return _HisFKAttrs;
	}
	
	private int _isFull = -1;
	
	/**
	 * 是否有自动计算
	 */
	public final boolean getIsHaveAutoFull()
	{
		if (_isFull == -1)
		{
			for (Attr attr : _attrs)
			{
				if (attr.AutoFullDoc != null)
				{
					_isFull = 1;
				}
			}
			if (_isFull == -1)
			{
				_isFull = 0;
			}
		}
		if (_isFull == 0)
		{
			return false;
		}
		return true;
	}
	
	public boolean IsHaveFJ = false;
	/**
	 * 移动到显示方式
	 */
	public String TitleExt = "";
	private int _isJs = -1;
	
	public final boolean getIsHaveJS()
	{
		if (_isJs == -1)
		{
			for (Attr attr : _attrs)
			{
				if (attr.AutoFullDoc == null)
				{
					continue;
				}
				// if (attr.AutoFullWay == AutoFullWay.Way1_JS)
				// {
				// _isJs = 1;
				// break;
				// }
			}
			
			if (_isJs == -1)
			{
				_isJs = 0;
			}
		}
		
		if (_isJs == 0)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 是否加入相关联的名称 AttrKey - AttrKeyName
	 */
	public boolean IsAddRefName = false;
	/**
	 * 他的外键Enum集合
	 */
	private Attrs _HisEnumAttrs = null;
	
	/**
	 * 他的外键Enum集合
	 */
	public final Attrs getHisEnumAttrs()
	{
		if (_HisEnumAttrs == null)
		{
			_HisEnumAttrs = new Attrs();
			for (Attr attr : this.getAttrs())
			{
				if (attr.getMyFieldType() == FieldType.Enum
						|| attr.getMyFieldType() == FieldType.PKEnum)
				{
					_HisEnumAttrs.Add(attr, true, false);
				}
			}
		}
		return _HisEnumAttrs;
	}
	
	/**
	 * 他的外键EnumandPk集合
	 */
	private Attrs _HisFKEnumAttrs = null;
	
	/**
	 * 他的外键EnumandPk集合
	 */
	public final Attrs getHisFKEnumAttrs()
	{
		if (_HisFKEnumAttrs == null)
		{
			_HisFKEnumAttrs = new Attrs();
			for (Attr attr : this.getAttrs())
			{
				if (attr.getMyFieldType() == FieldType.Enum
						|| attr.getMyFieldType() == FieldType.PKEnum
						|| attr.getMyFieldType() == FieldType.FK
						|| attr.getMyFieldType() == FieldType.PKFK)
				{
					_HisFKEnumAttrs.Add(attr);
				}
			}
		}
		return _HisFKEnumAttrs;
	}
	
	// 他的实体配置信息
	private Attrs _HisCfgAttrs = null;
	
	public final Attrs getHisCfgAttrs()
	{
		if (this._HisCfgAttrs == null)
		{
			this._HisCfgAttrs = new Attrs();
			if (WebUser.getNo().equals("admin"))
			{
				
				this._HisCfgAttrs.AddDDLSysEnum("UIRowStyleGlo", 2,
						"表格数据行风格(应用全局)", true, false, "UIRowStyleGlo",
						"@0=无风格@1=交替风格@2=鼠标移动@3=交替并鼠标移动");
				
				this._HisCfgAttrs.AddBoolen("IsEnableDouclickGlo", true,
						"是否启动双击打开(应用全局)");
				
				this._HisCfgAttrs.AddBoolen("IsEnableFocusField", true,
						"是否启用焦点字段");
				this._HisCfgAttrs.AddTBString("FocusField", null,
						"焦点字段(用于显示点击打开的列)", true, false, 0, 20, 20);
				this._HisCfgAttrs.AddBoolen("IsEnableRefFunc", true,
						"是否启用相关功能列");
				this._HisCfgAttrs.AddBoolen("IsEnableOpenICON", true,
						"是否启用打开图标");
				this._HisCfgAttrs.AddDDLSysEnum("MoveToShowWay", 0, "移动到显示方式",
						true, false, "MoveToShowWay", "@0=不显示@1=下拉列表@2=平铺");
				this._HisCfgAttrs.AddTBString("MoveTo", null, "移动到字段", true,
						false, 0, 20, 20);
				this._HisCfgAttrs.AddTBInt("WinCardW", 820, "弹出窗口宽度", true,
						false);
				this._HisCfgAttrs.AddTBInt("WinCardH", 480, "弹出窗口高度", true,
						false);
				this._HisCfgAttrs.AddDDLSysEnum("EditerType", 0, "大块文本编辑器",
						true, false, "EditerType",
						"@0=无@1=sina编辑器@2=FKCEditer@3=KindEditor@4=UEditor");
				
				// this._HisCfgAttrs.AddDDLSysEnum("UIRowStyleGlo", 2,
				// "表格数据行风格(应用全局)", true, false, "UIRowStyleGlo",
				// "@0=无风格@1=交替风格@2=鼠标移动@3=交替并鼠标移动");
			}
		}
		return _HisCfgAttrs;
	}
	
	// 他的关连信息.
	private Attrs _HisRefAttrs = null;
	
	public final Attrs getHisRefAttrs()
	{
		if (this._HisRefAttrs == null)
		{
			this._HisRefAttrs = new Attrs();
			
			for (Attr attr : this.getAttrs())
			{
				if (attr.getMyFieldType() == FieldType.FK
						|| attr.getMyFieldType() == FieldType.PKFK)
				{
					_HisRefAttrs.Add(attr);
				}
			}
		}
		return _HisRefAttrs;
	}
	
	// 关于相关功能
	/**
	 * 增加一个相关功能
	 * 
	 * @param title
	 *            标题
	 * @param classMethodName
	 *            连接
	 * @param icon
	 *            图标
	 * @param tooltip
	 *            提示信息
	 * @param target
	 *            连接到
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 */
	public final void AddRefMethod(String title, String classMethodName,
			Attrs attrs, String warning, String icon, String tooltip,
			String target, int width, int height)
	{
		RefMethod func = new RefMethod();
		func.Title = title;
		func.Warning = warning;
		func.ClassMethodName = classMethodName;
		func.Icon = icon;
		func.ToolTip = tooltip;
		// func.setWidth(width);
		// func.setHeight(height);
		func.setHisAttrs(attrs);
		this.getHisRefMethods().Add(func);
	}
	
	public final void AddRefMethodOpen()
	{
		RefMethod func = new RefMethod();
		func.Title = "打开";
		func.ClassMethodName = this.toString() + ".DoOpenCard";
		func.Icon = "../Img/Btn/Edit.gif";
		this.getHisRefMethods().Add(func);
	}
	
	/**
	 * 增加
	 * 
	 * @param func
	 */
	public final void AddRefMethod(RefMethod rm)
	{
		this.getHisRefMethods().Add(rm);
	}
	
	// 关于他的明细信息
	/**
	 * 增加明细
	 * 
	 * @param ens
	 *            集合信息
	 * @param refKey
	 *            属性
	 */
	public final void AddDtl(Entities ens, String refKey)
	{
		EnDtl dtl = new EnDtl();
		dtl.setEns(ens);
		dtl.setRefKey(refKey);
		this.getDtls().Add(dtl);
	}
	
	/**
	 * 相关功能s
	 */
	private RefMethods _RefMethods = null;
	
	/**
	 * 相关功能
	 */
	public final RefMethods getHisRefMethods()
	{
		if (this._RefMethods == null)
		{
			_RefMethods = new RefMethods();
		}
		
		return _RefMethods;
	}
	
	/**
	 * 明细s
	 */
	private EnDtls _Dtls = null;
	
	/**
	 * 他的明细
	 */
	public final EnDtls getDtls()
	{
		if (this._Dtls == null)
		{
			_Dtls = new EnDtls();
		}
		
		return _Dtls;
	}
	
	/**
	 * 所有的明细
	 */
	private EnDtls _DtlsAll = null;
	
	/**
	 * 所有的明细
	 */
	public final EnDtls getDtlsAll()
	{
		if (this._DtlsAll == null)
		{
			_DtlsAll = this.getDtls();
			
			// 加入他的多选。
			for (AttrOfOneVSM en : this.getAttrsOfOneVSM())
			{
				EnDtl dtl = new EnDtl();
				dtl.setEns(en.getEnsOfMM());
				dtl.setRefKey(en.getAttrOfOneInMM());
				// dtl.Desc =en.Desc;
				// dtl.Desc = en.Desc ;
				_DtlsAll.Add(dtl);
			}
			
		}
		return _DtlsAll;
	}
	
	// 构造涵数
	/**
	 * 构造涵数
	 * 
	 * @param dburl
	 *            数据库连接
	 * @param physicsTable
	 *            物理table.
	 */
	public Map(DBUrl dburl, String physicsTable)
	{
		this.setEnDBUrl(dburl);
		this.setPhysicsTable(physicsTable);
	}
	
	/**
	 * 构造涵数
	 * 
	 * @param physicsTable
	 *            物理table
	 */
	public Map(String physicsTable)
	{
		this.setPhysicsTable(physicsTable);
	}
	
	/**
	 * 构造涵数
	 * 
	 * @param DBUrlKeyList
	 *            连接的Key 你可以用 DBUrlKeyList 得到
	 * @param physicsTable
	 *            物理表
	 */
	public Map(DBUrlType dburltype, String physicsTable)
	{
		this.setEnDBUrl(new DBUrl(dburltype));
		this.setPhysicsTable(physicsTable);
	}
	
	/**
	 * 构造涵数
	 */
	public Map()
	{
	}
	
	// 属性
	/**
	 * 多对多的关联
	 */
	private AttrsOfOneVSM _AttrsOfOneVSM = new AttrsOfOneVSM();
	
	/**
	 * 点对多的关联
	 */
	public final AttrsOfOneVSM getAttrsOfOneVSM()
	{
		if (this._AttrsOfOneVSM == null)
		{
			this._AttrsOfOneVSM = new AttrsOfOneVSM();
		}
		return this._AttrsOfOneVSM;
	}
	
	public final void setAttrsOfOneVSM(AttrsOfOneVSM value)
	{
		this._AttrsOfOneVSM = value;
	}
	
	/**
	 * 通过多实体的类名称取出他的OneVSM属性.
	 * 
	 * @param ensOfMMclassName
	 * @return
	 */
	public final AttrOfOneVSM GetAttrOfOneVSM(String ensOfMMclassName)
	{
		for (AttrOfOneVSM attr : this.getAttrsOfOneVSM())
		{
			if (attr.getEnsOfMM().toString().equals(ensOfMMclassName))
			{
				return attr;
			}
		}
		throw new RuntimeException("error param:  " + ensOfMMclassName);
	}
	
	/**
	 * 文件类型
	 */
	private AdjunctType _AdjunctType = AdjunctType.None;
	
	/**
	 * 文件类型
	 */
	public final AdjunctType getAdjunctType()
	{
		return this._AdjunctType;
	}
	
	public final void setAdjunctType(AdjunctType value)
	{
		this._AdjunctType = value;
	}
	
	public String MoveTo = null;
	/**
	 * 实体描述
	 */
	private String _EnDesc = "";
	
	public final String getEnDesc()
	{
		return this._EnDesc;
	}
	
	public final void setEnDesc(String value)
	{
		this._EnDesc = value;
	}
	public boolean IsEnableVer = false;
	public boolean IsCount = false;// 是否统计
	public boolean IsShowSearchKey = true;
	public BP.Sys.DTSearchWay DTSearchWay = BP.Sys.DTSearchWay.None;
	public String DTSearchKey = "";
	/**
	 * 图片DefaultImageUrl
	 */
	public String Icon = "../../Images/En/Default.gif";
	/**
	 * 实体类型
	 */
	private EnType _EnType = EnType.App;
	
	/**
	 * 实体类型 默认为0(用户应用).
	 */
	public final EnType getEnType()
	{
		return this._EnType;
	}
	
	public final void setEnType(EnType value)
	{
		this._EnType = value;
	}
	
	// 生成属性根据xml.
	private String PKs = "";
	
	public final void GenerMap(String xml)
	{
		DataSet ds = new DataSet("");
		ds.readXml(xml);
		for (DataTable dt : ds.Tables)
		{
			if (dt.TableName.equals("Base"))
			{
				this.DealDT_Base(dt);
			} else if (dt.TableName.equals("Attr"))
			{
				this.DealDT_Attr(dt);
			} else if (dt.TableName.equals("SearchAttr"))
			{
				this.DealDT_SearchAttr(dt);
			} else if (dt.TableName.equals("Dtl"))
			{
				this.DealDT_SearchAttr(dt);
			} else if (dt.TableName.equals("Dot2Dot"))
			{
				this.DealDT_Dot2Dot(dt);
			} else
			{
				throw new RuntimeException("XML 配置信息错误，没有约定的标记:" + dt.TableName);
			}
		}
		// 检查配置的完整性。
	}
	
	private void DealDT_Base(DataTable dt)
	{
		if (dt.Rows.size() != 1)
		{
			throw new RuntimeException("基础信息配置错误，不能多于或者少于1行记录。");
		}
		for (DataColumn dc : dt.Columns)
		{
			String val = dt.Rows.get(0).getValue(dc.ColumnName).toString();
			/*
			 * warning String val = dt.Rows[0][dc.ColumnName].toString();
			 */
			if (val == null)
			{
				continue;
			}
			/*
			 * warning if (dt.Rows[0][dc.ColumnName] == DBNull.getValue())
			 */
			if (dt.Rows.get(0).getValue(dc.ColumnName) == null)
			{
				continue;
			}
			
			if (dc.ColumnName.equals("EnDesc"))
			{
				this.setEnDesc(val);
			} else if (dc.ColumnName.equals("Table"))
			{
				this.setPhysicsTable(val);
			} else if (dc.ColumnName.equals("DBUrl"))
			{
				this.setEnDBUrl(new DBUrl(DataType.GetDBUrlByString(val)));
			} else if (dc.ColumnName.equals("ICON"))
			{
				this.Icon = val;
			} else if (dc.ColumnName.equals("CodeStruct"))
			{
				this.setCodeStruct(val);
			} else if (dc.ColumnName.equals("AdjunctType"))
			{
				// this.PhysicsTable=val;
			} else if (dc.ColumnName.equals("EnType"))
			{
				if (val.equals("Admin"))
				{
					this.setEnType(EnType.Admin);
				} else if (val.equals("App"))
				{
					this.setEnType(EnType.App);
				} else if (val.equals("Dot2Dot"))
				{
					this.setEnType(EnType.Dot2Dot);
				} else if (val.equals("Dtl"))
				{
					this.setEnType(EnType.Dtl);
				} else if (val.equals("Etc"))
				{
					this.setEnType(EnType.Etc);
				} else if (val.equals("PowerAble"))
				{
					this.setEnType(EnType.PowerAble);
				} else if (val.equals("Sys"))
				{
					this.setEnType(EnType.Sys);
				} else if (val.equals("View"))
				{
					this.setEnType(EnType.View);
				} else if (val.equals("XML"))
				{
					this.setEnType(EnType.XML);
				} else
				{
					throw new RuntimeException("没有约定的标记:EnType =  " + val);
				}
			} else if (dc.ColumnName.equals("DepositaryOfEntity"))
			{
				if (val.equals("Application"))
				{
					this.setDepositaryOfEntity(Depositary.Application);
				} else if (val.equals("None"))
				{
					this.setDepositaryOfEntity(Depositary.None);
				} else if (val.equals("Session"))
				{
					this.setDepositaryOfEntity(Depositary.Application);
				} else
				{
					throw new RuntimeException("没有约定的标记:DepositaryOfEntity=["
							+ val + "] 应该选择为,Application, None, Session ");
				}
			} else if (dc.ColumnName.equals("DepositaryOfMap"))
			{
				if (val.equals("Application") || val.equals("Session"))
				{
					this.setDepositaryOfMap(Depositary.Application);
				} else if (val.equals("None"))
				{
					this.setDepositaryOfMap(Depositary.None);
				} else
				{
					throw new RuntimeException("没有约定的标记:DepositaryOfMap=["
							+ val + "] 应该选择为,Application, None, Session ");
				}
			} else if (dc.ColumnName.equals("PKs"))
			{
				this.PKs = val;
			} else
			{
				throw new RuntimeException("基础信息中没有约定的标记:" + val);
			}
		}
	}
	
	private void DealDT_Attr(DataTable dt)
	{
		for (DataRow dr : dt.Rows)
		{
			Attr attr = new Attr();
			for (DataColumn dc : dt.Columns)
			{
				String val = dr.getValue(dc.ColumnName).toString();
				/*
				 * warning String val = dr.getValue(dc.ColumnName).toString();
				 */
				if (dc.ColumnName.equals("Key"))
				{
					attr.setKey(val);
				} else if (dc.ColumnName.equals("Field"))
				{
					attr.setField(val);
				} else if (dc.ColumnName.equals("DefVal"))
				{
					attr.setDefaultVal(val);
				} else if (dc.ColumnName.equals("DT"))
				{
					attr.setMyDataType(DataType.GetDataTypeByString(val));
				} else if (dc.ColumnName.equals("UIBindKey"))
				{
					attr.setUIBindKey(val);
				} else if (dc.ColumnName.equals("UIIsReadonly"))
				{
					if (val.equals("1") || val.toUpperCase().equals("TRUE"))
					{
						attr.setUIIsReadonly(true);
					} else
					{
						attr.setUIIsReadonly(false);
					}
				} else if (dc.ColumnName.equals("MinLen"))
				{
					attr.setMinLength(Integer.parseInt(val));
				} else if (dc.ColumnName.equals("MaxLen"))
				{
					attr.setMaxLength(Integer.parseInt(val));
				} else if (dc.ColumnName.equals("TBLen"))
				{
					attr.setUIWidth(Integer.parseInt(val));
				} else
				{
					throw new RuntimeException("没有约定的标记:" + val);
				}
			}
			
			// 判断属性.
			if (attr.getUIBindKey() == null)
			{
				// 说明没有设置外键或者枚举类型。
				// if (attr.MyDataType
			} else
			{
				if (attr.getUIBindKey().indexOf(".") != -1)
				{
					// 说明它是一个类。
					Entities ens = attr.getHisFKEns();
					EntitiesNoName ensNoName = (EntitiesNoName) ((ens instanceof EntitiesNoName) ? ens
							: null);
					if (ensNoName == null)
					{
						// 没有转换成功的情况。
					} else
					{
						// 已经转换成功, 说明它是EntityNoName 类型。
						if (this.PKs.indexOf(attr.getKey()) != -1)
						{
							// 如果是一个主键
							if (attr.getField().equals(""))
							{
								attr.setField(attr.getKey());
							}
							this.AddDDLEntitiesPK(attr.getKey(), attr
									.getField(), attr.getDefaultVal()
									.toString(), attr.getDesc(), ensNoName,
									attr.getUIIsReadonly());
						} else
						{
							this.AddDDLEntities(attr.getKey(), attr.getField(),
									attr.getDefaultVal().toString(),
									attr.getDesc(), ensNoName,
									attr.getUIIsReadonly());
						}
					}
					
				} else
				{
				}
				
			}
			
		}
	}
	
	private void DealDT_SearchAttr(DataTable dt)
	{
	}
	
	private void DealDT_Dtl(DataTable dt)
	{
	}
	
	private void DealDT_Dot2Dot(DataTable dt)
	{
	}
	
	// 与生成No字串有关
	/**
	 * 生成字串的字段的长度。
	 */
	private int _GenerNoLength = 0;
	
	public final int getGenerNoLength()
	{
		if (this._GenerNoLength == 0)
		{
			throw new RuntimeException("@没有指定生成字串的字段长度。");
		}
		return this._GenerNoLength;
	}
	
	public final void setGenerNoLength(int value)
	{
		this._GenerNoLength = value;
	}
	
	/**
	 * 编码结构 例如： 0， 2322;
	 */
	private String _CodeStruct = "2";
	
	/**
	 * 编码的结构
	 */
	public final String getCodeStruct()
	{
		return this._CodeStruct;
	}
	
	public final void setCodeStruct(String value)
	{
		this._CodeStruct = value;
		this.setIsAutoGenerNo(true);
	}
	
	/**
	 * 编号的总长度。
	 */
	public final int getCodeLength()
	{
		int i = 0;
		if (getCodeStruct().length() == 0)
		{
			i = Integer.parseInt(this.getCodeStruct());
		} else
		{
			char[] s = this.getCodeStruct().toCharArray();
			for (char c : s)
			{
				i = i + Integer.parseInt((new Character(c)).toString());
			}
		}
		
		return i;
	}
	
	/**
	 * 是否允许重复的名称(默认不允许重复。)
	 */
	private boolean _IsAllowRepeatName = true;
	
	/**
	 * 是否允许重复的名称. 在insert，update 前检查。
	 */
	public final boolean getIsAllowRepeatName()
	{
		return _IsAllowRepeatName;
	}
	
	public final void setIsAllowRepeatName(boolean value)
	{
		_IsAllowRepeatName = value;
	}
	
	/**
	 * 是否自动编号
	 */
	private boolean _IsAutoGenerNo = false;
	
	/**
	 * 是否自动编号.
	 */
	public final boolean getIsAutoGenerNo()
	{
		return _IsAutoGenerNo;
	}
	
	public final void setIsAutoGenerNo(boolean value)
	{
		_IsAutoGenerNo = value;
	}
	
	/**
	 * 是否检查编号长度。（默认的false）
	 */
	private boolean _IsCheckNoLength = false;
	
	/**
	 * 是否检查编号长度. 在insert 前检查。
	 */
	public final boolean getIsCheckNoLength()
	{
		return _IsCheckNoLength;
	}
	
	public final void setIsCheckNoLength(boolean value)
	{
		_IsCheckNoLength = value;
	}
	
	// 与连接有关系。
	
	private DBUrl _EnDBUrl = null;
	
	/**
	 * 数据库连接
	 */
	public final DBUrl getEnDBUrl()
	{
		if (this._EnDBUrl == null)
		{
			_EnDBUrl = new DBUrl();
		}
		return this._EnDBUrl;
	}
	
	public final void setEnDBUrl(DBUrl value)
	{
		this._EnDBUrl = value;
	}
	
	private String _PhysicsTable = null;
	
	public final boolean getIsView()
	{
		String sql = "";
		switch (this.getEnDBUrl().getDBType())
		{
			case Oracle:
				// 查询.
				sql = "SELECT TABTYPE  FROM TAB WHERE UPPER(TNAME)=:v";
				break;
			case Access:
				sql = "select   Type   from   msysobjects   WHERE   UCASE(name)='"
						+ this.getPhysicsTableExt().toUpperCase() + "'";
				DataTable dtw = DBAccess.RunSQLReturnTable(sql);
				if (dtw.Rows.size() == 0)
				{
					throw new RuntimeException("@表不存在["
							+ this.getPhysicsTableExt() + "]");
				}
				/*
				 * warning if (dtw.Rows[0][0].toString().trim().equals("5"))
				 */
				if (dtw.Rows.get(0).getValue(0).toString().trim().equals("5"))
				{
					return true;
				} else
				{
					return false;
				}
			case MSSQL:
				sql = "select xtype from sysobjects WHERE name =:v";
				DataTable dt1 = null;
				try
				{
					dt1 = DBAccess.RunSQLReturnTable(sql, "v",
							this.getPhysicsTableExt());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				if (dt1.Rows.size() == 0)
				{
					throw new RuntimeException("@表不存在["
							+ this.getPhysicsTableExt() + "]");
				}
				
				/*
				 * warning if
				 * (dt1.Rows[0][0].toString().toUpperCase().trim().equals("V"))
				 */
				if (dt1.Rows.get(0).getValue(0).toString().toUpperCase().trim()
						.equals("V"))
				{
					return true;
				} else
				{
					return false;
				}
			case Informix:
				sql = "select tabtype from systables where tabname = '"
						+ this.getPhysicsTableExt().toLowerCase() + "'";
				DataTable dtaa = DBAccess.RunSQLReturnTable(sql);
				if (dtaa.Rows.size() == 0)
				{
					throw new RuntimeException("@表不存在["
							+ this.getPhysicsTableExt() + "]");
				}
				
				/*
				 * warning if
				 * (dtaa.Rows[0][0].toString().toUpperCase().trim().equals("V"))
				 */
				if (dtaa.Rows.get(0).getValue(0).toString().toUpperCase()
						.trim().equals("V"))
				{
					return true;
				} else
				{
					return false;
				}
			case MySQL:
				sql = "SELECT Table_Type FROM information_schema.TABLES WHERE table_name=:v and table_schema='"
						+ SystemConfig.getAppCenterDBDatabase() + "'";
				DataTable dt2 = null;
				//增加物理表非空判断
				if(null == this.getPhysicsTableExt() || this.getPhysicsTableExt().equals(""))
				{
					return false;
				}
				try
				{
					dt2 = DBAccess.RunSQLReturnTable(sql, "v",
							this.getPhysicsTableExt());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				if (dt2.Rows.size() == 0)
				{
					throw new RuntimeException("@表不存在["
							+ this.getPhysicsTableExt() + "]");
				}
				
				/*
				 * warning if
				 * (dt2.Rows[0][0].toString().toUpperCase().trim().equals
				 * ("VIEW"))
				 */
				if (dt2.Rows.get(0).getValue(0).toString().toUpperCase().trim()
						.equals("VIEW"))
				{
					return true;
				} else
				{
					return false;
				}
			default:
				throw new RuntimeException("@没有做的判断。");
		}
		
		DataTable dt = null;
		try
		{
			dt = DBAccess.RunSQLReturnTable(sql, "v", this.getPhysicsTableExt()
					.toUpperCase());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (dt.Rows.size() == 0)
		{
			throw new RuntimeException("@表不存在[" + this.getPhysicsTableExt()
					+ "]");
		}
		
		/*
		 * warning if (dt.Rows[0][0].toString().equals("VIEW"))
		 */
		if (dt.Rows.get(0).getValue(0).toString().equals("VIEW"))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public final String getPhysicsTableExt()
	{
		if (this.getPhysicsTable().indexOf(".") != -1)
		{
			String[] str = this.getPhysicsTable().split("[.]", -1);
			return str[1];
		} else
		{
			return this.getPhysicsTable();
		}
	}
	
	/**
	 * 物理表名称
	 * 
	 * @return Table name
	 */
	public final String getPhysicsTable()
	{
		return this._PhysicsTable;
		//
		// if (DBAccess.AppCenterDBType==DBType.Oracle)
		// {
		// return ""+this._PhysicsTable+"";
		// }
		// else
		// {
		// return this._PhysicsTable;
		// }
		//
	}
	
	public final void setPhysicsTable(String value)
	{
		// 因为组成的select 语句放入了内存,修改它的时间也要修改内存的数据。
		// DA.Cash.AddObj(this.ToString()+"SQL",Depositary.Application,null);
		
		Cash.RemoveObj(this.toString() + "SQL", Depositary.Application);
		Cash.RemoveObj("MapOf" + this.toString(), this.getDepositaryOfMap()); // RemoveObj
		
		// DA.Cash.setObj(en.ToString()+"SQL",en.EnMap.DepositaryOfMap) as
		// string;
		this._PhysicsTable = value;
	}
	
	private Attrs _attrs = null;
	
	public final Attrs getAttrs()
	{
		if (this._attrs == null)
		{
			this._attrs = new Attrs();
		}
		return this._attrs;
	}
	
	public final void setAttrs(Attrs value)
	{
		if (this._attrs == null)
		{
			this._attrs = new Attrs();
		}
		
		Attrs myattrs = value;
		for (Attr item : myattrs)
		{
			this._attrs.Add(item);
		}
	}
	
	// 于属性相关的操作
	
	// 于帮定 固定 枚举类型有关系的操作。
	public final void AddDDLFixEnum(String key, String field, int defaultVal,
			boolean IsPK, String desc, DDLShowType showtype, boolean isReadonly)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppInt);
		
		if (IsPK)
		{
			attr.setMyFieldType(FieldType.PK);
		} else
		{
			attr.setMyFieldType(FieldType.Normal);
		}
		
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.DDL);
		attr.setUIDDLShowType(showtype);
		attr.setUIIsReadonly(isReadonly);
		this.getAttrs().Add(attr);
	}
	
	public final void AddDDLFixEnumPK(String key, int defaultVal, String desc,
			DDLShowType showtype, boolean isReadonly)
	{
		this.AddDDLFixEnum(key, key, defaultVal, true, desc, showtype,
				isReadonly);
	}
	
	public final void AddDDLFixEnumPK(String key, String field, int defaultVal,
			String desc, DDLShowType showtype, boolean isReadonly)
	{
		this.AddDDLFixEnumPK(key, field, defaultVal, desc, showtype, isReadonly);
	}
	
	public final void AddDDLFixEnum(String key, int defaultVal, String desc,
			DDLShowType showtype, boolean isReadonly)
	{
		this.AddDDLFixEnum(key, key, defaultVal, false, desc, showtype,
				isReadonly);
	}
	
	public final void AddBoolean_del(String key, int defaultVal, String desc,
			boolean isReadonly)
	{
		this.AddDDLFixEnum(key, key, defaultVal, false, desc,
				DDLShowType.Boolean, isReadonly);
	}
	
	public final void AddBoolean_del(String key, String field, int defaultVal,
			String desc, boolean isReadonly)
	{
		this.AddDDLFixEnum(key, field, defaultVal, false, desc,
				DDLShowType.Boolean, isReadonly);
	}
	
	// 与boolen 有关系的操作.
	/**
	 * 增加与boolen 有关系的操作.
	 * 
	 * @param key
	 *            key
	 * @param field
	 *            field
	 * @param defaultVal
	 *            defaultVal
	 * @param desc
	 *            desc
	 * @param isUIEnable
	 *            isUIEnable
	 * @param isUIVisable
	 *            isUIVisable
	 */
	public final void AddBoolean(String key, String field, boolean defaultVal,
			String desc, boolean isUIVisable, boolean isUIEnable, boolean isLine)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		
		if (defaultVal)
		{
			attr.setDefaultVal(1);
		} else
		{
			attr.setDefaultVal(0);
		}
		
		attr.setMyDataType(DataType.AppBoolean);
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.CheckBok);
		attr.setUIIsReadonly(isUIEnable);
		attr.setUIVisible(isUIVisable);
		attr.UIIsLine = isLine;
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 增加与boolen 有关系的操作.
	 * 
	 * @param key
	 *            key
	 * @param field
	 *            field
	 * @param defaultVal
	 *            defaultVal
	 * @param desc
	 *            desc
	 * @param isUIEnable
	 *            isUIEnable
	 * @param isUIVisable
	 *            isUIVisable
	 */
	public final void AddBoolean(String key, boolean defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable)
	{
		AddBoolean(key, key, defaultVal, desc, isUIVisable, isUIEnable, false);
	}
	
	/**
	 * 增加与boolen 有关系的操作.
	 * 
	 * @param key
	 *            key
	 * @param field
	 *            field
	 * @param defaultVal
	 *            defaultVal
	 * @param desc
	 *            desc
	 * @param isUIEnable
	 *            isUIEnable
	 * @param isUIVisable
	 *            isUIVisable
	 */
	public final void AddBoolean(String key, boolean defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable, boolean isLine)
	{
		AddBoolean(key, key, defaultVal, desc, isUIVisable, isUIEnable, isLine);
	}
	
	public final void AddBoolean(String key, boolean defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable, boolean isLine,
			String helpUrl)
	{
		AddBoolean(key, key, defaultVal, desc, isUIVisable, isUIEnable, isLine,
				null);
	}
	
	/**
	 * 增加与boolen 有关系的操作.
	 * 
	 * @param key
	 *            key
	 * @param field
	 *            field
	 * @param defaultVal
	 *            defaultVal
	 * @param desc
	 *            desc
	 * @param isUIEnable
	 *            isUIEnable
	 * @param isUIVisable
	 *            isUIVisable
	 */
	public final void AddBoolean(String key, String field, boolean defaultVal,
			String desc, boolean isUIVisable, boolean isUIEnable,
			boolean isLine, String helpUrl)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.HelperUrl = helpUrl;
		if (defaultVal)
		{
			attr.setDefaultVal(1);
		} else
		{
			attr.setDefaultVal(0);
		}
		attr.setMyDataType(DataType.AppBoolean);
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.CheckBok);
		attr.setUIIsReadonly(isUIEnable);
		attr.setUIVisible(isUIVisable);
		attr.UIIsLine = isLine;
		this.getAttrs().Add(attr);
	}
	
	// 于帮定自定义,枚举类型有关系的操作。
	public final void AddDDLSysEnumPK(String key, String field, int defaultVal,
			String desc, boolean isUIVisable, boolean isUIEnable,
			String sysEnumKey)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppInt);
		attr.setMyFieldType(FieldType.PKEnum);
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.DDL);
		attr.setUIDDLShowType(DDLShowType.SysEnum);
		attr.setUIBindKey(sysEnumKey);
		attr.setUIVisible(isUIVisable);
		attr.setUIIsReadonly(isUIEnable);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 自定义枚举类型
	 * 
	 * @param key
	 *            键
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认
	 * @param desc
	 *            描述
	 * @param sysEnumKey
	 *            Key
	 */
	public final void AddDDLSysEnum(String key, String field, int defaultVal,
			String desc, boolean isUIVisable, boolean isUIEnable,
			String sysEnumKey, String cfgVal, boolean isLine)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppInt);
		attr.setMyFieldType(FieldType.Enum);
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.DDL);
		attr.setUIDDLShowType(DDLShowType.SysEnum);
		attr.setUIBindKey(sysEnumKey);
		attr.UITag = cfgVal;
		attr.setUIVisible(isUIVisable);
		attr.setUIIsReadonly(isUIEnable);
		attr.UIIsLine = isLine;
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 自定义枚举类型
	 * 
	 * @param key
	 *            键
	 * @param defaultVal
	 *            默认
	 * @param desc
	 *            描述
	 * @param sysEnumKey
	 *            Key
	 */
	public final void AddDDLSysEnum(String key, int defaultVal, String desc,boolean isUIVisable, boolean isUIEnable, String sysEnumKey)
	{
		AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable,
				sysEnumKey, null, false);
	}
	
	/**
	 * @param key
	 * @param defaultVal
	 * @param desc
	 * @param isUIVisable
	 * @param isUIEnable
	 * @param sysEnumKey
	 * @param cfgVal
	 */
	public final void AddDDLSysEnum(String key, int defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable, String sysEnumKey,
			String cfgVal, boolean isLine)
	{
		AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable,
				sysEnumKey, cfgVal, isLine);
	}
	
	public final void AddDDLSysEnum(String key, int defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable, String sysEnumKey,
			String cfgVal)
	{
		AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable,
				sysEnumKey, cfgVal, false);
	}
	
	public final void AddDDLSysEnum(String key, int defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable)
	{
		AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable, key,
				null, false);
	}
	
	// 于帮定自定义,枚举类型有关系的操作。
	/**
	 * 自定义枚举类型
	 * 
	 * @param key
	 *            键
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认
	 * @param desc
	 *            描述
	 * @param sysEnumKey
	 *            Key
	 */
	public final void AddRadioBtnSysEnum(String key, String field,
			int defaultVal, String desc, boolean isUIVisable,
			boolean isUIEnable, String sysEnumKey)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppInt);
		attr.setMyFieldType(FieldType.Enum);
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.RadioBtn);
		attr.setUIDDLShowType(DDLShowType.Self);
		attr.setUIBindKey(sysEnumKey);
		attr.setUIVisible(isUIVisable);
		attr.setUIIsReadonly(isUIEnable);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 自定义枚举类型
	 * 
	 * @param key
	 *            键
	 * @param defaultVal
	 *            默认
	 * @param desc
	 *            描述
	 * @param sysEnumKey
	 *            Key
	 */
	public final void AddRadioBtnSysEnum(String key, int defaultVal,
			String desc, boolean isUIVisable, boolean isUIEnable,
			String sysEnumKey)
	{
		AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable,
				sysEnumKey, null, false);
	}
	
	public final void AddDDLBindTable(String key, String defaultVal,
			String desc, String refKey, String refText, boolean isUIVisable,
			boolean isReadonly, String bindTable)
	{
		AddDDLBindTable(key, key, defaultVal, desc, refKey, refText,
				isUIVisable, isReadonly, bindTable, false);
	}
	
	public final void AddDDLBindTable(String key, String field,
			String defaultVal, String desc, String refKey, String refText,
			boolean uiVisable, boolean isReadonly, String bindTable,
			boolean isLine)
	{
		
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setMyFieldType(FieldType.BindTable);
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.DDL);
		attr.setUIDDLShowType(DDLShowType.BindTable);
		attr.setUIBindKey(bindTable);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		attr.setUIRefKeyValue(refKey);
		attr.setUIRefKeyText(refText);
		attr.UIIsLine = isLine;
		this.getAttrs().Add(attr);
	}
	
	public final void AddDDLGrandChild(String key, String childKey,
			String grandChildKey)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.setChildKey(childKey);
		attr.setGrandChildKey(grandChildKey);
	}
	
	public final void AddDDLParentAndChild(String key, String parentKey,
			String childKey, String refParentCol)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.setChildKey(childKey);
		attr.setParentKey(parentKey);
		attr.setRefParentCol(refParentCol);
	}
	
	public final void AddDDLParent(String key, String parentKey,
			String refParentCol)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.setParentKey(parentKey);
		attr.setRefParentCol(refParentCol);
	}
	
	public final void AddDDLChild(String key, String childKey)
	{
		Attr attr = this.GetAttrByKey(key);
		attr.setChildKey(childKey);
	}
	
	// entityNoName
	public final void AddDDLEntities(String key, String defaultVal,
			String desc, EntitiesSimpleTree ens, boolean uiIsEnable)
	{
		this.AddDDLEntities(key, key, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntities(String key, String defaultVal,
			String desc, EntitiesTree ens, boolean uiIsEnable)
	{
		this.AddDDLEntities(key, key, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntities(String key, String defaultVal,
			String desc, EntitiesNoName ens, boolean uiIsEnable)
	{
		this.AddDDLEntities(key, key, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntities(String key, String field,
			String defaultVal, String desc, EntitiesNoName ens,
			boolean uiIsEnable)
	{
		this.AddDDLEntities(key, field, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	// EntitiesOIDName
	public final void AddDDLEntities(String key, int defaultVal, String desc,
			EntitiesOIDName ens, boolean uiIsEnable)
	{
		this.AddDDLEntities(key, key, defaultVal, DataType.AppInt, desc, ens,
				"OID", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntities(String key, String field,
			Object defaultVal, String desc, EntitiesOIDName ens,
			boolean uiIsEnable)
	{
		this.AddDDLEntities(key, field, defaultVal, DataType.AppInt, desc, ens,
				"OID", "Name", uiIsEnable);
	}
	
	/**
	 * 于实体有关系的操作。
	 * 
	 * @param key
	 *            健值
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param dataType
	 *            DataType类型
	 * @param desc
	 *            描述
	 * @param ens
	 *            实体集合
	 * @param refKey
	 *            关联的建
	 * @param refText
	 *            关联的Text
	 */
	private void AddDDLEntities(String key, String field, Object defaultVal,
			int dataType, FieldType _fildType, String desc, Entities ens,
			String refKey, String refText, boolean uiIsEnable)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(dataType);
		attr.setMyFieldType(_fildType);
		attr.setMaxLength(50);
		
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.DDL);
		attr.setUIDDLShowType(DDLShowType.Ens);
		attr.setUIBindKey(ens.toString());
		// attr.UIBindKeyOfEn = ens.GetNewEntity.ToString();
		
		attr.setHisFKEns(ens);
		
		attr.setUIRefKeyText(refText);
		attr.setUIRefKeyValue(refKey);
		attr.setUIIsReadonly(uiIsEnable);
		
		this.getAttrs().Add(attr, true, this.IsAddRefName);
	}
	
	public final void AddDDLEntities(String key, String field,
			Object defaultVal, int dataType, String desc, Entities ens,
			String refKey, String refText, boolean uiIsEnable)
	{
		AddDDLEntities(key, field, defaultVal, dataType, FieldType.FK, desc,
				ens, refKey, refText, uiIsEnable);
	}
	
	/**
	 * 于实体有关系的操作。字段与属性名称相同。
	 * 
	 * @param key
	 *            健值
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param dataType
	 *            DataType类型
	 * @param desc
	 *            描述
	 * @param ens
	 *            实体集合
	 * @param refKey
	 *            关联的建
	 * @param refText
	 *            关联的Text
	 */
	public final void AddDDLEntities(String key, Object defaultVal,
			int dataType, String desc, Entities ens, String refKey,
			String refText, boolean uiIsEnable)
	{
		AddDDLEntities(key, key, defaultVal, dataType, desc, ens, refKey,
				refText, uiIsEnable);
	}
	
	public final void AddDDLEntitiesPK(String key, Object defaultVal,
			int dataType, String desc, EntitiesTree ens, boolean uiIsEnable)
	{
		AddDDLEntities(key, key, defaultVal, dataType, FieldType.PKFK, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntitiesPK(String key, Object defaultVal,
			int dataType, String desc, Entities ens, String refKey,
			String refText, boolean uiIsEnable)
	{
		AddDDLEntities(key, key, defaultVal, dataType, FieldType.PKFK, desc,
				ens, refKey, refText, uiIsEnable);
	}
	
	public final void AddDDLEntitiesPK(String key, String field,
			Object defaultVal, int dataType, String desc, Entities ens,
			String refKey, String refText, boolean uiIsEnable)
	{
		AddDDLEntities(key, field, defaultVal, dataType, FieldType.PKFK, desc,
				ens, refKey, refText, uiIsEnable);
	}
	
	public void AddDDLSQL(String key, String defaultVal, String desc, String sql, boolean uiIsEnable)
    {
        Attr attr = new Attr();
        attr.setKey(key);
        attr.setField(key);
        attr.setDefaultVal(defaultVal);
        attr.setMyDataType(DataType.AppString);
        attr.setMyFieldType(FieldType.Normal);
        attr.setMaxLength(50);

        attr.setDesc(desc);
        attr.setUIContralType(UIContralType.DDL);
        attr.setUIDDLShowType(DDLShowType.BindTable);
        attr.setUIBindKey(sql);
        attr.setHisFKEns(null);
        attr.setUIIsReadonly(uiIsEnable);
        this.getAttrs().Add(attr);
        //他的名称列.
        attr = new Attr();
        attr.setKey(key + "Text");
        attr.setField(key + "Text");
        attr.setDefaultVal(defaultVal);
        attr.setMyDataType(DataType.AppString);
        attr.setMyFieldType(FieldType.Normal);
        attr.setMaxLength(50);
        attr.setDesc(desc);
        attr.setUIContralType(UIContralType.TB);
        attr.setUIBindKey(sql);
        attr.setUIIsReadonly(true);
        attr.setUIVisible(false);
        this.getAttrs().Add(attr);
    }
	/**
	 * 关于EntitiesNoName 有关系的操作
	 * @param key
	 * @param field
	 * @param defaultVal
	 * @param desc
	 * @param ens
	 * @param uiIsEnable
	 */
	public final void AddDDLEntitiesPK(String key, String field,
			String defaultVal, String desc, EntitiesTree ens, boolean uiIsEnable)
	{
		AddDDLEntities(key, field, (Object) defaultVal, DataType.AppString,
				FieldType.PKFK, desc, ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntitiesPK(String key, String field,
			String defaultVal, String desc, EntitiesNoName ens,
			boolean uiIsEnable)
	{
		AddDDLEntities(key, field, (Object) defaultVal, DataType.AppString,
				FieldType.PKFK, desc, ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntitiesPK(String key, String defaultVal,
			String desc, EntitiesNoName ens, boolean uiIsEnable)
	{
		AddDDLEntitiesPK(key, key, defaultVal, desc, ens, uiIsEnable);
	}
	
	public final void AddDDLEntitiesPK(String key, String defaultVal,
			String desc, EntitiesTree ens, boolean uiIsEnable)
	{
		AddDDLEntitiesPK(key, key, defaultVal, desc, ens, uiIsEnable);
	}
	
	public final void AddDDLEntitiesPK(String key, String defaultVal,
			String desc, EntitiesSimpleTree ens, boolean uiIsEnable)
	{
		AddDDLEntitiesPK(key, key, defaultVal, DataType.AppString, desc, ens,
				"No", "Name", uiIsEnable);
	}
	
	// TB
	
	// string 有关系的操作。
	
	// 关于
	protected final void AddTBString(String key, String field,
			Object defaultVal, FieldType _FieldType, TBType tbType,
			String desc, boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith, boolean isUILine)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setDesc(desc);
		attr.setUITBShowType(tbType);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(tbWith);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setMyFieldType(_FieldType);
		attr.UIIsLine = isUILine;
		this.getAttrs().Add(attr);
	}
	
	// 公共的。
	/**
	 * 同步两个实体属性.
	 */
	public final void AddAttrsFromMapData()
	{
		if (StringHelper.isNullOrEmpty(this.getFK_MapData()))
		{
			throw new RuntimeException("@您没有为map的 FK_MapData 赋值.");
		}
		
		MapData md = null;
		md = new MapData();
		md.setNo(this.getFK_MapData());
		if (md.RetrieveFromDBSources() == 0)
		{
			md.setName(this.getFK_MapData());
			md.setPTable(this.getPhysicsTable());
			md.setEnPK(this.PKs);
			md.Insert();
			md.RepairMap();
		}
		md.Retrieve();
		MapAttrs attrs = new MapAttrs(this.getFK_MapData());
		
		// 把 手工编写的attr 放入 mapattrs里面去.
		for (Attr attr : this.getAttrs())
		{
			if (attrs.Contains(MapAttrAttr.KeyOfEn, attr.getKey()))
			{
				continue;
			}
			
			if (attr.getIsRefAttr())
			{
				continue;
			}
			
			// 把文件实体类的属性放入关系实体类中去。
			MapAttr mapattrN = attr.getToMapAttr();
			mapattrN.setFK_MapData(this.getFK_MapData());
			if (mapattrN.getUIHeight() == 0)
			{
				mapattrN.setUIHeight(23);
			}
			mapattrN.Insert();
			attrs.AddEntity(mapattrN);
		}
		
		// 把关系实体类的属性放入文件实体类中去。
		for (Object attr : attrs)
		{
			if (this.getAttrs().Contains(((MapAttr) attr).getKeyOfEn()))
			{
				continue;
			}
			this.AddAttr(((MapAttr) attr).getHisAttr());
		}
	}
	
	public final void AddAttrs(Attrs attrs)
	{
		for (Attr attr : attrs)
		{
			if (attr.getIsRefAttr())
			{
				continue;
			}
			this.getAttrs().Add(attr);
		}
	}
	
	public final void AddAttr(Attr attr)
	{
		// if(attr.getKey()==null|| attr.getKey().equals(""))
		// return;
		this.getAttrs().Add(attr);
	}
	
	public final void AddAttr(String key, Object defaultVal, int dbtype,
			boolean isPk, String desc)
	{
		if (isPk)
		{
			AddTBStringPK(key, key, desc, true, false, 0, 1000, 100);
		} else
		{
			AddTBString(key, key, defaultVal.toString(), FieldType.Normal,
					TBType.TB, desc, true, false, 0, 1000, 100, false);
		}
	}
	
	/**
	 * 增加一个textbox 类型的属性。
	 * 
	 * @param key
	 *            健值
	 * @param field
	 *            字段值
	 * @param defaultVal
	 *            默认值
	 * @param _FieldType
	 *            字段类型
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param uiVisable
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBString(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		AddTBString(key, key, defaultVal, FieldType.Normal, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith, false);
	}
	
	public final void AddTBString(String key, String field, Object defaultVal,
			String desc, boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		AddTBString(key, field, defaultVal, FieldType.Normal, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith, false);
	}
	
	public final void AddTBString(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith, boolean isUILine)
	{
		AddTBString(key, key, defaultVal, FieldType.Normal, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith, isUILine);
	}
	
	public final void AddTBString(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith, boolean isUILine, String helpUrl)
	{
		AddTBString(key, key, defaultVal, FieldType.Normal, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith, isUILine,
				helpUrl);
	}
	
	protected final void AddTBString(String key, String field,
			Object defaultVal, FieldType _FieldType, TBType tbType,
			String desc, boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith, boolean isUILine, String helpUrl)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.HelperUrl = helpUrl;
		
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setDesc(desc);
		attr.setUITBShowType(tbType);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(tbWith);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setMyFieldType(_FieldType);
		attr.UIIsLine = isUILine;
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 附件集合
	 */
	public final void AddMyFileS()
	{
		this.AddTBInt(EntityNoMyFileAttr.MyFileNum, 0, "附件", false, false);
		this.IsHaveFJ = true;
	}
	
	/**
	 * 附件集合
	 * 
	 * @param desc
	 */
	public final void AddMyFileS(String desc)
	{
		this.AddTBInt(EntityNoMyFileAttr.MyFileNum, 0, desc, false, false);
		this.IsHaveFJ = true;
	}
	
	/**
	 * 增加一个附件
	 */
	public final void AddMyFile()
	{
		this.AddTBString(EntityNoMyFileAttr.MyFileName, null, "附件或图片", false,
				false, 0, 100, 200);
		this.AddTBString(EntityNoMyFileAttr.MyFilePath, null, "MyFilePath",
				false, false, 0, 100, 200);
		this.AddTBString(EntityNoMyFileAttr.MyFileExt, null, "MyFileExt",
				false, false, 0, 10, 10);
		this.AddTBString(EntityNoMyFileAttr.WebPath, null, "WebPath", false,
				false, 0, 200, 10);
		
		this.AddTBInt(EntityNoMyFileAttr.MyFileH, 0, "MyFileH", false, false);
		this.AddTBInt(EntityNoMyFileAttr.MyFileW, 0, "MyFileW", false, false);
		this.AddTBFloat("MyFileSize", 0, "MyFileSize", false, false);
		this.IsHaveFJ = true;
	}
	
	/**
	 * 增加一个附件
	 * 
	 * @param fileDesc
	 *            描述
	 */
	public final void AddMyFile(String fileDesc)
	{
		this.AddTBString(EntityNoMyFileAttr.MyFileName, null, fileDesc, false,
				false, 0, 100, 200);
		this.AddTBString(EntityNoMyFileAttr.MyFilePath, null, "MyFilePath",
				false, false, 0, 100, 200);
		this.AddTBString(EntityNoMyFileAttr.MyFileExt, null, "MyFileExt",
				false, false, 0, 10, 10);
		this.AddTBString(EntityNoMyFileAttr.WebPath, null, "WebPath", false,
				false, 0, 200, 10);
		this.AddTBInt(EntityNoMyFileAttr.MyFileH, 0, "MyFileH", false, false);
		this.AddTBInt(EntityNoMyFileAttr.MyFileW, 0, "MyFileW", false, false);
		this.AddTBFloat("MyFileSize", 0, "MyFileSize", false, false);
		this.IsHaveFJ = true;
	}
	
	private AttrFiles _HisAttrFiles = null;
	
	public final AttrFiles getHisAttrFiles()
	{
		if (_HisAttrFiles == null)
		{
			_HisAttrFiles = new AttrFiles();
		}
		return _HisAttrFiles;
	}
	
	/**
	 * 增加一个特定的附件,可以利用它增加多个？ 比如：增加简历，增加论文。
	 * 
	 * @param fileDesc
	 * @param fExt
	 */
	public final void AddMyFile(String fileDesc, String fExt)
	{
		getHisAttrFiles().Add(fExt, fileDesc);
		this.IsHaveFJ = true;
	}
	
	// 增加大块文本输入
	public final void AddTBStringDoc()
	{
		AddTBStringDoc("Doc", "Doc", null, "内容", true, false, 0, 4000, 300,
				300, true);
	}
	
	public final void AddTBStringDoc(String key, String defaultVal,
			String desc, boolean uiVisable, boolean isReadonly, boolean isUILine)
	{
		AddTBStringDoc(key, key, defaultVal, desc, uiVisable, isReadonly, 0,
				4000, 300, 300, isUILine);
	}
	
	public final void AddTBStringDoc(String key, String defaultVal,
			String desc, boolean uiVisable, boolean isReadonly)
	{
		AddTBStringDoc(key, key, defaultVal, desc, uiVisable, isReadonly, 0,
				4000, 300, 300, false);
	}
	
	public final void AddTBStringDoc(String key, String defaultVal,
			String desc, boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith, int rows)
	{
		AddTBStringDoc(key, key, defaultVal, desc, uiVisable, isReadonly,
				minLength, maxLength, tbWith, rows, false);
	}
	
	public final void AddTBStringDoc(String key, String field,
			String defaultVal, String desc, boolean uiVisable,
			boolean isReadonly, int minLength, int maxLength, int tbWith,
			int rows, boolean isUILine)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.TB);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(300);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setMyFieldType(FieldType.Normal);
		attr.setUIHeight(rows);
		attr.UIIsLine = isUILine;
		this.getAttrs().Add(attr);
	}
	
	// PK
	public final void AddTBStringPK(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		this.PKs = key;
		AddTBString(key, key, defaultVal, FieldType.PK, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith, false);
	}
	
	public final void AddTBStringPK(String key, String field,
			Object defaultVal, String desc, boolean uiVisable,
			boolean isReadonly, int minLength, int maxLength, int tbWith)
	{
		this.PKs = key;
		AddTBString(key, field, defaultVal, FieldType.PK, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith, false);
	}
	
	// PKNo
	
	// 外键于 Ens 有关系的操作。
	/**
	 * 外键于 Ens 有关系的操作。
	 * 
	 * @param key
	 *            属性
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param ens
	 *            实体
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringFKEns(String key, String field,
			String defaultVal, String desc, Entities ens, String refKey,
			String refText, boolean uiVisable, boolean isReadonly,
			int minLength, int maxLength, int tbWith)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setUIBindKey(ens.toString());
		attr.setHisFKEns(ens);
		// attr.UIBindKeyOfEn = ens.GetNewEntity.ToString();
		
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Ens);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(tbWith);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setUIRefKeyValue(refKey);
		attr.setUIRefKeyText(refText);
		attr.setMyFieldType(FieldType.FK);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 外键于 Ens 有关系的操作。
	 * 
	 * @param key
	 *            属性
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param ens
	 *            实体
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringFKEns(String key, String defaultVal,
			String desc, Entities ens, String refKey, String refText,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		this.AddTBStringFKEns(key, key, defaultVal, desc, ens, refKey, refText,
				uiVisable, isReadonly, minLength, maxLength, tbWith);
	}
	
	// 于多值有关系的操作
	/**
	 * 于多值有关系的操作
	 * 
	 * @param key
	 * @param field
	 * @param defaultVal
	 * @param desc
	 * @param ens
	 * @param uiVisable
	 * @param isReadonly
	 * @param minLength
	 * @param maxLength
	 * @param tbWith
	 */
	public final void AddTBMultiValues(String key, String field,
			Object defaultVal, String desc, Entities ens, String refValue,
			String refText, boolean uiVisable, boolean isReadonly,
			int minLength, int maxLength, int tbWith)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setUIBindKey(ens.toString());
		attr.setHisFKEns(ens);
		
		// attr.UIBindKeyOfEn = ens.GetNewEntity.ToString();
		
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Ens);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(tbWith);
		attr.setUIIsReadonly(isReadonly);
		attr.setUIRefKeyText(refText);
		attr.setUIRefKeyValue(refValue);
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setMyFieldType(FieldType.MultiValues);
		
		this.getAttrs().Add(attr);
	}
	
	// 主键于 Ens 有关系的操作。
	/**
	 * 外键于 Ens 有关系的操作。 主键
	 * 
	 * @param key
	 *            属性
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param ens
	 *            实体
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringPKEns(String key, String field,
			Object defaultVal, String desc, Entities ens, String refVal,
			String refText, boolean uiVisable, boolean isReadonly,
			int minLength, int maxLength, int tbWith)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setUIBindKey(ens.toString());
		attr.setHisFKEns(attr.getHisFKEns());
		// attr.UIBindKeyOfEn = ens.GetNewEntity.ToString();
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Ens);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(tbWith);
		attr.setUIIsReadonly(isReadonly);
		
		attr.setUIRefKeyText(refText);
		attr.setUIRefKeyValue(refVal);
		
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setMyFieldType(FieldType.PKFK);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 外键于 Ens 有关系的操作。
	 * 
	 * @param key
	 *            属性
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param ens
	 *            实体
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringPKEns(String key, String defaultVal,
			String desc, Entities ens, String refKey, String refText,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		this.AddTBStringPKEns(key, key, defaultVal, desc, ens, refKey, refText,
				uiVisable, isReadonly, minLength, maxLength, tbWith);
	}
	
	// 主键于 DataHelpKey 有关系的操作。
	/**
	 * 外键于 DataHelpKey 有关系的操作, 用与自定义的右键帮助系统.
	 * 
	 * @param key
	 *            属性
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param DataHelpKey
	 *            在TB 里定义的右健帮助Key </param>
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringPKSelf(String key, String field,
			Object defaultVal, String desc, String DataHelpKey,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setUIBindKey(DataHelpKey);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Self);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(tbWith);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setMyFieldType(FieldType.PK);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 外键于 Ens 有关系的操作。用与自定义的右键帮助系统.
	 * 
	 * @param key
	 *            属性
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param DataHelpKey
	 *            在TB 里定义的右健帮助Key </param>
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringPKSelf(String key, Object defaultVal,
			String desc, String DataHelpKey, boolean uiVisable,
			boolean isReadonly, int minLength, int maxLength, int tbWith)
	{
		this.AddTBStringPKSelf(key, key, defaultVal, desc, DataHelpKey,
				uiVisable, isReadonly, minLength, maxLength, tbWith);
	}
	
	// 外键于 DataHelpKey 有关系的操作。
	/**
	 * 外键于 DataHelpKey 有关系的操作。用与自定义的右键帮助系统.
	 * 
	 * @param key
	 *            属性
	 * @param field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param DataHelpKey
	 *            在TB 里定义的右健帮助Key </param>
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringFKSelf(String key, String field,
			Object defaultVal, String desc, String DataHelpKey,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppString);
		attr.setUIBindKey(DataHelpKey);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Self);
		attr.setUIVisible(uiVisable);
		attr.setUIWidth(tbWith);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(maxLength);
		attr.setMinLength(minLength);
		attr.setMyFieldType(FieldType.Normal);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 外键于 Ens 有关系的操作。用与 Ens 右键帮助系统.
	 * 
	 * @param key
	 *            属性
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param DataHelpKey
	 *            在TB 里定义的右健帮助Key </param>
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param tbWith
	 *            宽度
	 */
	public final void AddTBStringFKSelf(String key, Object defaultVal,
			String desc, String DataHelpKey, boolean uiVisable,
			boolean isReadonly, int minLength, int maxLength, int tbWith)
	{
		this.AddTBStringFKSelf(key, key, defaultVal, desc, DataHelpKey,
				uiVisable, isReadonly, minLength, maxLength, tbWith);
	}
	
	// 增加外键植
	public final void AddTBStringFKValue(String refKey, String key,
			String desc, boolean IsVisable, int with)
	{
		
	}
	
	// 日期类型
	public final void AddTBDate(String key)
	{
		if (key.equals("RDT"))
		{
			AddTBDate("RDT", "记录日期", true, true);
		} else if (key.equals("UDT"))
		{
			AddTBDate("UDT", "更新日期", true, true);
		} else
		{
			AddTBDate(key, key, true, true);
		}
	}
	
	/**
	 * 增加日期类型的控健
	 * 
	 * @param key
	 *            健值
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 */
	public final void AddTBDate(String key, String field, String defaultVal,
			String desc, boolean uiVisable, boolean isReadonly)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppDate);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Date);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(50);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 增加日期类型的控健
	 * 
	 * @param key
	 *            key
	 * @param defaultVal
	 *            defaultVal/如果你想用当天的信息,请选择后面的方法加入
	 * @param desc
	 *            desc
	 * @param uiVisable
	 *            uiVisable
	 * @param isReadonly
	 *            isReadonly
	 */
	public final void AddTBDate(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		AddTBDate(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
	/**
	 * 增加日期类型的控健(默认日期是当前日期)
	 * 
	 * @param key
	 *            key
	 * @param desc
	 *            desc
	 * @param uiVisable
	 *            uiVisable
	 * @param isReadonly
	 *            isReadonly
	 */
	public final void AddTBDate(String key, String desc, boolean uiVisable,
			boolean isReadonly)
	{
		AddTBDate(key, key,
				DataType.dateToStr(new Date(), DataType.getSysDataFormat()),
				desc, uiVisable, isReadonly);
	}
	
	// 日期时间类型。
	/**
	 * 增加日期类型的控健
	 * 
	 * @param key
	 *            健值
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 */
	public final void AddTBDateTime(String key, String field,
			String defaultVal, String desc, boolean uiVisable,
			boolean isReadonly)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppDateTime);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.DateTime);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		attr.setMaxLength(50);
		attr.setUIWidth(100);
		this.getAttrs().Add(attr);
	}
	
	public final void AddTBDateTime(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBDateTime(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
	public final void AddTBDateTime(String key, String desc, boolean uiVisable,
			boolean isReadonly)
	{
		this.AddTBDateTime(
				key,
				key,
				DataType.dateToStr(new Date(), DataType.getSysDataTimeFormat()),
				desc, uiVisable, isReadonly);
	}
	
	// 资金类型
	public final void AddTBMoney(String key, String field, float defaultVal,
			String desc, boolean uiVisable, boolean isReadonly)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppMoney);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Moneny);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		this.getAttrs().Add(attr);
	}
	
	public final void AddTBMoney(String key, float defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBMoney(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
	// Int类型
	/**
	 * 增加一个普通的类型。
	 * 
	 * @param key
	 *            键
	 * @param _Field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 */
	public final void AddTBInt(String key, String _Field, int defaultVal,
			String desc, boolean uiVisable, boolean isReadonly)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(_Field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppInt);
		attr.setMyFieldType(FieldType.Normal);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Int);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 增加一个普通的类型。字段值与属性相同。
	 * 
	 * @param key
	 *            键
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 */
	public final void AddTBInt(String key, int defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBInt(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
	/**
	 * 增加一个PK的类型。
	 * 
	 * @param key
	 *            键
	 * @param _Field
	 *            字段
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 */
	public final void AddTBIntPK(String key, String _Field, int defaultVal,
			String desc, boolean uiVisable, boolean isReadonly,
			boolean identityKey)
	{
		this.PKs = key;
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(_Field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppInt);
		attr.setMyFieldType(FieldType.PK);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Int);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		if (identityKey)
		{
			attr.setUIBindKey("1"); // 特殊标记此值，让它可以自动生成自增长的列.
		}
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 增加一个PK的类型。字段值与属性相同。
	 * 
	 * @param key
	 *            键
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 */
	public final void AddTBIntPKOID(String _field, String desc)
	{
		this.AddTBIntPK("OID", _field, 0, "OID", false, true, false);
	}
	
	/**
	 * 增加一个MID
	 */
	public final void AddTBMID()
	{
		Attr attr = new Attr();
		attr.setKey("MID");
		attr.setField("MID");
		attr.setDefaultVal(0);
		attr.setMyDataType(DataType.AppInt);
		attr.setMyFieldType(FieldType.Normal);
		attr.setDesc("MID");
		attr.setUITBShowType(TBType.Int);
		attr.setUIVisible(false);
		attr.setUIIsReadonly(true);
		this.getAttrs().Add(attr);
	}
	
	public final void AddTBIntPKOID()
	{
		this.AddTBIntPKOID("OID", "OID");
	}
	
	public final void AddTBMyNum(String desc)
	{
		this.AddTBInt("MyNum", 1, desc, true, true);
	}
	
	public final void AddTBMyNum()
	{
		this.AddTBInt("MyNum", 1, "个数", true, true);
	}
	
	/**
	 * 增加 AtParas字段.
	 * 
	 * @param fieldLength
	 */
	public final void AddTBAtParas(int fieldLength)
	{
		this.AddTBString("AtPara", null, "AtPara", false, true, 0, fieldLength,
				10);
	}
	
	/**
	 * 主键
	 */
	public final void AddMyPK()
	{
		this.PKs = "MyPK";
		this.AddTBStringPK("MyPK", null, "MyPK", true, true, 1, 100, 10);
		
		// Attr attr = new Attr();
		// attr.Key = "MyPK";
		// attr.Field = "MyPK";
		// attr.DefaultVal = null;
		// attr.MyDataType = DataType.AppString;
		// attr.MyFieldType = FieldType.PK;
		// attr.Desc = "MyPK";
		// attr.UITBShowType = TBType.TB;
		// attr.UIVisible = false;
		// attr.UIIsReadonly = true;
		// attr.MinLength = 1;
		// attr.MaxLength = 100;
		// this.Attrs.Add(attr);
	}
	
	public final void AddMyPKNoVisable()
	{
		this.AddTBStringPK("MyPK", null, "MyPK", false, false, 1, 100, 10);
	}
	
	/**
	 * 增加自动增长列
	 */
	public final void AddAID()
	{
		Attr attr = new Attr();
		attr.setKey("AID");
		attr.setField("AID");
		attr.setDefaultVal(null);
		attr.setMyDataType(DataType.AppInt);
		attr.setMyFieldType(FieldType.PK);
		attr.setDesc("AID");
		attr.setUITBShowType(TBType.TB);
		attr.setUIVisible(false);
		attr.setUIIsReadonly(true);
		this.getAttrs().Add(attr);
	}
	
	/**
	 * 增加一个PK的类型。字段值与属性相同。
	 * 
	 * @param key
	 *            键
	 * @param defaultVal
	 *            默认值
	 * @param desc
	 *            描述
	 * @param uiVisable
	 *            是不是可见
	 * @param isReadonly
	 *            是不是只读
	 */
	public final void AddTBIntPK(String key, int defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBIntPK(key, key, defaultVal, desc, uiVisable, isReadonly,
				false);
	}
	
	public final void AddTBIntPK(String key, int defaultVal, String desc,
			boolean uiVisable, boolean isReadonly, boolean identityKey)
	{
		this.AddTBIntPK(key, key, defaultVal, desc, uiVisable, isReadonly,
				identityKey);
	}
	
	public final void AddTBIntMyNum()
	{
		this.AddTBInt("MyNum", "MyNum", 1, "个数", true, true);
	}
	
	// Float类型
	public final void AddTBFloat(String key, String _Field, float defaultVal,
			String desc, boolean uiVisable, boolean isReadonly)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(_Field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppFloat);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Num);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		this.getAttrs().Add(attr);
	}
	
	public final void AddTBFloat(String key, float defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBFloat(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
	// Decimal类型
	public final void AddTBDecimal(String key, String _Field,
			double defaultVal, String desc, boolean uiVisable,
			boolean isReadonly)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(_Field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(DataType.AppDouble);
		attr.setDesc(desc);
		attr.setUITBShowType(TBType.Decimal);
		attr.setUIVisible(uiVisable);
		attr.setUIIsReadonly(isReadonly);
		this.getAttrs().Add(attr);
	}
	
	public final void AddTBDecimal(String key, double defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBDecimal(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
}
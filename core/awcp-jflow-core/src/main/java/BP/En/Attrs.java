package BP.En;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BP.DA.AtPara;
import BP.DA.DataType;

/**
 * 属性集合
 */
public class Attrs extends ArrayList<Attr>
{
	public static ArrayList<Attr> convertAttrs(Object obj)
	{
		return (ArrayList<Attr>) obj;
	}
	public List<Attr> ToJavaList()
	{
		return (List<Attr>)(Object)this;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 关于属性的增加 String
	protected final void AddTBString(String key, String field,
			Object defaultVal, FieldType _FieldType, TBType tbType,
			String desc, boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
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
		this.Add(attr);
	}
	
	public final void AddTBString(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		AddTBString(key, key, defaultVal, FieldType.Normal, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith);
	}
	
	public final void AddTBString(String key, String field, Object defaultVal,
			String desc, boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith)
	{
		AddTBString(key, field, defaultVal, FieldType.Normal, TBType.TB, desc,
				uiVisable, isReadonly, minLength, maxLength, tbWith);
	}
	
	public final void AddTBStringDoc(String key, String defaultVal,
			String desc, boolean uiVisable, boolean isReadonly)
	{
		AddTBStringDoc(key, key, defaultVal, desc, uiVisable, isReadonly, 0,
				2000, 300, 300);
	}
	
	public final void AddTBStringDoc(String key, String defaultVal,
			String desc, boolean uiVisable, boolean isReadonly, int minLength,
			int maxLength, int tbWith, int rows)
	{
		AddTBStringDoc(key, key, defaultVal, desc, uiVisable, isReadonly,
				minLength, maxLength, tbWith, rows);
	}
	
	public final void AddTBStringDoc(String key, String field,
			String defaultVal, String desc, boolean uiVisable,
			boolean isReadonly, int minLength, int maxLength, int tbWith,
			int rows)
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
		this.Add(attr);
	}
	
	/**
	 * 增加附件
	 * 
	 * @param fileDesc
	 */
	public final void AddMyFile(String fileDesc)
	{
		this.AddTBString(EntityNoMyFileAttr.MyFileName, null, fileDesc, false,
				false, 0, 100, 200);
		this.AddTBString(EntityNoMyFileAttr.MyFilePath, null, "MyFilePath",
				false, false, 0, 100, 200);
		this.AddTBString(EntityNoMyFileAttr.MyFileExt, null, "MyFileExt",
				false, false, 0, 10, 10);
		// this.AddTBInt(EntityNoMyFileAttr.MyFileNum, 0, "MyFileNum", false,
		// false);
		this.AddTBInt(EntityNoMyFileAttr.MyFileH, 0, "MyFileH", false, false);
		this.AddTBInt(EntityNoMyFileAttr.MyFileW, 0, "MyFileW", false, false);
		this.AddTBInt("MyFileSize", 0, "MyFileSize", false, false);
		
		// this.IsHaveFJ = true;
	}
	
	// 关于属性的增加 String
	
	// 关于属性的增加 Int
	
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
		this.Add(attr);
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
	
	public final void AddBoolen(String key, boolean defaultVal, String desc)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(key);
		
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
		attr.setUIIsReadonly(true);
		attr.setUIVisible(true);
		this.Add(attr);
	}
	
	// 关于属性的增加 Int
	
	// 关于属性的增加 Float类型
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
		this.Add(attr);
	}
	
	public final void AddTBFloat(String key, float defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBFloat(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
	// 关于属性的增加 Float
	
	// Decimal类型
	public final void AddTBDecimal(String key, String _Field,
			java.math.BigDecimal defaultVal, String desc, boolean uiVisable,
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
		this.Add(attr);
	}
	
	public final void AddTBDecimal(String key, java.math.BigDecimal defaultVal,
			String desc, boolean uiVisable, boolean isReadonly)
	{
		this.AddTBDecimal(key, key, defaultVal, desc, uiVisable, isReadonly);
	}
	
	// 日期
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
		attr.setMaxLength(20);
		this.Add(attr);
	}
	
	public final void AddTBDate(String key, String defaultVal, String desc,
			boolean uiVisable, boolean isReadonly)
	{
		this.AddTBDate(key, key, defaultVal, desc, uiVisable, isReadonly);
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
		this.AddTBDate(key, key,
				DataType.dateToStr(new Date(), DataType.getSysDataFormat()),
				desc, uiVisable, isReadonly);
		
		/*
		 * warning this.AddTBDate(key, key, new
		 * java.util.Date().ToString(DataType.getSysDataFormat()), desc,
		 * uiVisable, isReadonly);
		 */
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
		attr.setMaxLength(30);
		attr.setMinLength(0);
		attr.setUIWidth(100);
		this.Add(attr);
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
		/*
		 * warning this.AddTBDateTime(key, key, new
		 * java.util.Date().ToString(DataType.getSysDataTimeFormat()), desc,
		 * uiVisable, isReadonly);
		 */
	}
	
	// 于帮定自定义,枚举类型有关系的操作。
	public final void AddDDLSysEnum(String key, int defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable, String sysEnumKey)
	{
		this.AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable,
				sysEnumKey, null);
	}
	
	/**
	 * /
	 * 
	 * @param key
	 * @param field
	 * @param defaultVal
	 * @param desc
	 * @param isUIVisable
	 * @param isUIEnable
	 * @param sysEnumKey
	 */
	public final void AddDDLSysEnum(String key, String field, int defaultVal,
			String desc, boolean isUIVisable, boolean isUIEnable,
			String sysEnumKey)
	{
		this.AddDDLSysEnum(key, field, defaultVal, desc, isUIVisable,
				isUIEnable, sysEnumKey, null);
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
			String sysEnumKey, String cfgVal)
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
		this.Add(attr);
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
	public final void AddDDLSysEnum(String key, int defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable, String sysEnumKey,
			String cfgVals)
	{
		AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable,
				sysEnumKey, cfgVals);
	}
	
	public final void AddDDLSysEnum(String key, int defaultVal, String desc,
			boolean isUIVisable, boolean isUIEnable)
	{
		AddDDLSysEnum(key, key, defaultVal, desc, isUIVisable, isUIEnable, key);
	}
	
	// entities
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
	public final void AddDDLEntities(String key, String field,
			Object defaultVal, int dataType, FieldType _fildType, String desc,
			Entities ens, String refKey, String refText, boolean uiIsEnable)
	{
		Attr attr = new Attr();
		attr.setKey(key);
		attr.setField(field);
		attr.setDefaultVal(defaultVal);
		attr.setMyDataType(dataType);
		attr.setMyFieldType(_fildType);
		
		attr.setDesc(desc);
		attr.setUIContralType(UIContralType.DDL);
		attr.setUIDDLShowType(DDLShowType.Ens);
		attr.setUIBindKey(ens.toString());
		// attr.UIBindKeyOfEn = ens.GetNewEntity.ToString();
		attr.setHisFKEns(ens);
		
		attr.setHisFKEns(ens);
		attr.setUIRefKeyText(refText);
		attr.setUIRefKeyValue(refKey);
		attr.setUIIsReadonly(uiIsEnable);
		this.Add(attr, true, false);
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
	
	// entityNoName
	public final void AddDDLEntities(String key, Object defaultVal,
			String desc, EntitiesNoName ens, boolean uiIsEnable)
	{
		this.AddDDLEntities(key, key, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntities(String key, String field,
			Object defaultVal, String desc, EntitiesNoName ens,
			boolean uiIsEnable)
	{
		this.AddDDLEntities(key, field, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	// EntitiesSimpleTree
	public final void AddDDLEntities(String key, Object defaultVal,
			String desc, EntitiesSimpleTree ens, boolean uiIsEnable)
	{
		this.AddDDLEntities(key, key, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	public final void AddDDLEntities(String key, Object defaultVal,
			String desc, EntitiesTree ens, boolean uiIsEnable)
	{
		this.AddDDLEntities(key, key, defaultVal, DataType.AppString, desc,
				ens, "No", "Name", uiIsEnable);
	}
	
	// EntitiesOIDName
	public final void AddDDLEntities(String key, Object defaultVal,
			String desc, EntitiesOIDName ens, boolean uiIsEnable)
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
	
	public final Attrs clone()
	{
		Attrs attrs = new Attrs();
		for (Attr attr : this)
		{
			attrs.Add(attr);
		}
		return attrs;
	}
	
	/**
	 * 下一个Attr 是否是 Doc 类型.
	 * 
	 * @param key
	 * @return
	 */
	public final Attr NextAttr(String CurrentKey)
	{
		int i = this.GetIndexByKey(CurrentKey);
		
		if (this.size() > i)
		{
			return null;
		}
		
		Attr tempVar = this.getItem(i + 1);
		return (Attr) ((tempVar instanceof Attr) ? tempVar : null);
	}
	
	public final Attr PrvAttr(String CurrentKey)
	{
		int i = this.GetIndexByKey(CurrentKey);
		
		if (this.size() < i)
		{
			return null;
		}
		
		Attr tempVar = this.getItem(i - 1);
		return (Attr) ((tempVar instanceof Attr) ? tempVar : null);
	}
	
	/**
	 * 是否包含属性key。
	 * 
	 * @param key
	 * @return
	 */
	public final boolean Contains(String key)
	{
		for (Attr attr : this)
		{
			if (attr.getKey().equals(key))
			{
				return true;
			}
		}
		return false;
	}
	
	public final boolean ContainsUpper(String key)
	{
		for (Attr attr : this)
		{
			if (attr.getKey().toUpperCase().equals(key.toUpperCase()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 物理字段Num
	 */
	public final int getConutOfPhysicsFields()
	{
		int i = 0;
		for (Attr attr : this)
		{
			if (attr.getMyFieldType() != FieldType.RefText)
			{
				i++;
			}
		}
		return i;
	}
	
	/*
	 * warning @Override protected void OnInsertComplete(int index, Object
	 * value) { super.OnInsertComplete(index, value); }
	 */
	
	/**
	 * 通过Key ， 取出他的Index.
	 * 
	 * @param key
	 *            Key
	 * @return index
	 */
	public final int GetIndexByKey(String key)
	{
		for (int i = 0; i < this.size(); i++)
		{
			if (this.getItem(i).getKey().equals(key))
			{
				return i;
			}
		}
		return -1;
	}
	
	public final Attr GetAttrByKey(String key)
	{
		for (Attr item : this)
		{
			if (item.getKey().equals(key))
			{
				return item;
			}
		}
		return null;
	}
	/** 属性集合
	 
	 @param cfgKeys 约定的字段格式
*/
//ORIGINAL LINE: public Attrs(string cfgKeys)
	public Attrs(String cfgKeys)
	{

//       *
//        *  根据约定的格式的字符串生成集合.
//        *  
//@Key=MyStringField;Name=我的中文字段;Type=String;DefVal=默认值;AppType=Normal;IsLine=1             
//@Key=MyIntField;Name=我的数字字段;Type=Int;DefVal=12;AppType=Normal;IsLine=false
//@Key=MyFloatField;Name=我的浮点字段;Type=Float;DefVal=12.0;AppType=Normal;IsLine=false
//@Key=MyEnumField;Name=我的枚举字段;Type=Int;DefVal=0;AppType=Enum;IsLine=false;BindKey=[0=Yes,1=No,2=Unhnow]
//@Key=MyFKField;Name=我的外键字段;Type=String;DefVal=01;AppType=FK;IsLine=false;BindKey=BP.Port.Depts
//        * 

		String[] strs = cfgKeys.split("[@]", -1);
		for (String str : strs)
		{
			AtPara ap = new AtPara(str.replace(";", "@"));
			FieldTypeS ft = FieldTypeS.forValue(ap.GetValIntByKey("AppType"));//
			switch (ft)
			{
				case Enum:
					this.AddDDLSysEnum(ap.GetValStrByKey("Key"), ap.GetValStrByKey("Key"), ap.GetValIntByKey("DefVal"), ap.GetValStrByKey("Name"), true, true, ap.GetValStrByKey("Key"), "@"+ap.GetValStrByKey("BindKey").replace(",","@"));
					break;
				case FK:
					EntitiesNoName ens = (EntitiesNoName)BP.En.ClassFactory.GetEns(ap.GetValStrByKey("BindKey"));
					this.AddDDLEntities(ap.GetValStrByKey("Key"), ap.GetValStrByKey("DefVal"), ap.GetValStrByKey("Name"), ens,true);
					break;
				default:
//					switch (ap.GetValStrByKey("Type"))
//ORIGINAL LINE: case "String":
					if (ap.GetValStrByKey("Type").equals("String"))
					{
							this.AddTBString(ap.GetValStrByKey("Key"), ap.GetValStrByKey("DefVal"), ap.GetValStrByKey("Name"), true, false, 0, 1000, 500);
					}
//ORIGINAL LINE: case "Int":
					else if (ap.GetValStrByKey("Type").equals("Int"))
					{
							this.AddTBInt(ap.GetValStrByKey("Key"), ap.GetValIntByKey("DefVal"), ap.GetValStrByKey("Name"), true, false);
					}
//ORIGINAL LINE: case "Float":
					else if (ap.GetValStrByKey("Type").equals("Float"))
					{
							this.AddTBFloat(ap.GetValStrByKey("Key"), ap.GetValFloatByKey("DefVal"), ap.GetValStrByKey("Name"), true, false);
					}
					else
					{
					}
					break;
			}
		}
	}
	/**
	 * 属性集合
	 */
	public Attrs()
	{
	}
	
	public final void Add(Attr attr)
	{
		if (attr.getField() == null || attr.getField().equals(""))
		{
			throw new RuntimeException("属性设置错误：您不能设置 key='" + attr.getKey()
					+ "',得字段值为空");
		}
		
		boolean k = attr.getIsKeyEqualField();
		this.Add(attr, true, false);
	}
	
	/**
	 * 加入一个属性。
	 * 
	 * @param attr
	 *            attr
	 * @param isAddHisRefText
	 *            isAddHisRefText
	 */
	public final void Add(Attr attr, boolean isAddHisRefText,
			boolean isAddHisRefName)
	{
		for (Attr myattr : this)
		{
			if (myattr.getKey().equals(attr.getKey()))
			{
				return;
			}
		}
		
		this.add(attr);
		/*
		 * warning this.add(attr);
		 */
		
		if (isAddHisRefText)
		{
			this.AddRefAttrText(attr);
		}
		
		if (isAddHisRefName)
		{
			this.AddRefAttrName(attr);
		}
	}
	
	private void AddRefAttrText(Attr attr)
	{
		if (attr.getMyFieldType() == FieldType.FK
				|| attr.getMyFieldType() == FieldType.Enum
				|| attr.getMyFieldType() == FieldType.PKEnum
				|| attr.getMyFieldType() == FieldType.PKFK
				|| attr.getMyFieldType() == FieldType.BindTable)
		{
			
			Attr myattr = new Attr();
			myattr.setMyFieldType(FieldType.RefText);
			myattr.setMyDataType(DataType.AppString);
			myattr.setUIContralType(UIContralType.TB);
			myattr.setUIWidth(attr.getUIWidth() * 2);
			myattr.setKey(attr.getKey() + "Text");
			
			myattr.setUIIsReadonly(true);
			myattr.setUIBindKey(attr.getUIBindKey());
			// myattr.UIBindKeyOfEn = attr.UIBindKeyOfEn;
			myattr.setHisFKEns(attr.getHisFKEns());
			
			// myattr.Desc=attr.Desc+"名称";
			
			String desc = "名称";
			myattr.setDesc(desc);
			/*
			 * warning string desc=myattr.Desc="名称";
			 */
			if (desc.indexOf("编号") >= 0)
			{
				myattr.setDesc(attr.getDesc().replace("编号", "名称"));
			} else
			{
				myattr.setDesc(attr.getDesc() + "名称");
			}
			
			if (attr.getUIContralType() == UIContralType.DDL)
			{
				myattr.setUIVisible(false);
			}
			
			this.add(myattr);
			/*
			 * warning this.add(myattr);
			 */
			
			// this.Add(myattr,true);
		}
	}
	
	private void AddRefAttrName(Attr attr)
	{
		if (attr.getMyFieldType() == FieldType.FK
				|| attr.getMyFieldType() == FieldType.Enum
				|| attr.getMyFieldType() == FieldType.PKEnum
				|| attr.getMyFieldType() == FieldType.PKFK)
		{
			
			Attr myattr = new Attr();
			myattr.setMyFieldType(FieldType.Normal);
			myattr.setMyDataType(DataType.AppString);
			myattr.setUIContralType(UIContralType.TB);
			myattr.setUIWidth(attr.getUIWidth() * 2);
			
			myattr.setKey(attr.getKey() + "Name");
			myattr.setField(attr.getKey() + "Name");
			
			myattr.setMaxLength(200);
			myattr.setMinLength(0);
			
			myattr.setUIVisible(false);
			myattr.setUIIsReadonly(true);
			
			myattr.setDesc("Name");
			myattr.setDesc(myattr.getDesc());
			this.add(myattr);
			/*
			 * warning this.add(myattr);
			 */
		}
	}
	
	/**
	 * 根据索引访问集合内的元素Attr。
	 */
	public final Attr getItem(int index)
	{
		return (Attr) this.get(index);
		/*
		 * warning return (Attr)this.get(index);
		 */
	}

	public BP.Sys.MapAttrs ToMapAttrs() {
		BP.Sys.MapAttrs mapAttrs = new BP.Sys.MapAttrs();
		for (Attr item : this) {
			if (item.getMyFieldType() == FieldType.RefText)
				continue;
			BP.Sys.MapAttr mattr = new BP.Sys.MapAttr();
			mattr.setKeyOfEn(item.getKey());
			mattr.setName(item.getDesc());
			mattr.setMyDataType(item.getMyDataType());
			mattr.setUIContralType(item.getUIContralType());
			mattr.setUIBindKey(item.getUIBindKey());
			mattr.setMaxLen(item.getMaxLength());
			mattr.setMinLen(item.getMinLength());
			mattr.setDefValReal(item.getDefaultValOfReal());
			if (item.getMyFieldType() == FieldType.Enum)
				mattr.setLGType(FieldTypeS.Enum);
			if (item.getMyFieldType() == FieldType.FK)
				mattr.setLGType(FieldTypeS.FK);
			mapAttrs.AddEntity(mattr);
		}
		return mapAttrs;
	}

}
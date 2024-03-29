package BP.En;

/**
 * 编辑类型
 */
public enum EditType
{
	/**
	 * 可编辑
	 */
	Edit,
	/**
	 * 不可删除
	 */
	UnDel,
	/**
	 * 只读,不可删除。
	 */
	Readonly;
	
	public int getValue()
	{
		return this.ordinal();
	}
	
	public static EditType forValue(int value)
	{
		return values()[value];
	}
}
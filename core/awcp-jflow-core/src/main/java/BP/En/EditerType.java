package BP.En;

/**
 * 编辑器类型
 */
public enum EditerType
{
	/**
	 * 无编辑器
	 */
	None,
	/**
	 * Sina编辑器
	 */
	Sina,
	/**
	 * FKEditer
	 */
	FKEditer,
	/**
	 * KindEditor
	 */
	KindEditor,
	/**
	 * 百度的UEditor
	 */
	UEditor;
	
	public int getValue()
	{
		return this.ordinal();
	}
	
	public static EditerType forValue(int value)
	{
		return values()[value];
	}
}
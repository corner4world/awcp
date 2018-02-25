package BP.Sys;


/** 
 棫行处理
 
*/
public enum WhenOverSize
{
	/** 
	 不处理
	 
	*/
	None,
	/** 
	 增加一行
	 
	*/
	AddRow,
	/** 
	 翻页
	 
	*/
	TurnPage;

	public int getValue()
	{
		return this.ordinal();
	}

	public static WhenOverSize forValue(int value)
	{
		return values()[value];
	}
}
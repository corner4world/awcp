package BP.WF;

/** 
 运行模式
 
*/
public enum RunModel
{
	/** 
	 普通
	 
	*/
	Ordinary(0),
	/** 
	 合流
	 
	*/
	HL(1),
	/** 
	 分流
	 
	*/
	FL(2),
	/** 
	 分合流
	 
	*/
	FHL(3),
	/** 
	 子线程
	 
	*/
	SubThread(4);

	private int intValue;
	private static java.util.HashMap<Integer, RunModel> mappings;
	private synchronized static java.util.HashMap<Integer, RunModel> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, RunModel>();
		}
		return mappings;
	}

	private RunModel(int value)
	{
		intValue = value;
		RunModel.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static RunModel forValue(int value)
	{
		return getMappings().get(value);
	}
}
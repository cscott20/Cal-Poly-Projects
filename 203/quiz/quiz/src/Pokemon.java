public class Pokemon
{
	private String id;
	private String name;
	private String type;

	public Pokemon(String id, String name, String type)
	{
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getID()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

}
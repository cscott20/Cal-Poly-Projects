public class Character
{
	private String name;
	private int age;
	private double importanceFactor;
	private boolean magic;

	public Character(String name, int age, double importanceFactor, boolean magic)
	{
		this.name = name;
		this.age = age;
		this.importanceFactor = importanceFactor;
		this.magic = magic;
	}

	public String getName() { return name; }
	public int getAge() { return age; }
	public double getImportanceFactor() { return importanceFactor; }
	public void setImportanceFactor(double importanceFactor) { this.importanceFactor = importanceFactor; }
	public boolean isMagic() { return magic; }

}

import java.util.function.*;
import java.util.stream.*;
import java.util.List;

public class CharacterClassifier
{
  /**
    * TODO (2 points): make a public static Function (see attached javadocs) variable
    * named updateImportance.
    * It should take in a Character and returns a Double.  
    * You must use a lambda expression in your answer.  
    * The Function should return the character's importanceFactor + 5.
    */
    public static Function<T, R> updateImportance()  

	/**
	 * TODO (5 points): complete the following method.  Given a list of 
	 * Characters, use a stream to return the average importanceFactor of all 
	 * the Characters that are younger than a given age.  
	 *
	 * FOR FULL CREDIT: you must use one lambda expression and
	 * one key extractor.
	 */
	public double aveImportanceByAge(List<Character> characters, int maxAge)
	{
        double avg = characters.stream()
                                        .filter(c -> c.age < maxAge)
                                        .average()
                                        .toDouble();
        return avg;
	}
	
	/**
	 * TODO (2 points): complete the following method that takes in a Function
	 * (which creates a Double given a Character) and a List of Characters and
	 * returns a list of Characters of equal length.
	 * The method should take the Function and apply it to only the magical 
	 * characters in the list.  The non-magical characters should be in the list
	 * but have the same importanceFactor as before.
	 *
	 * You may use a loop or a Stream to do it. 
	 */
	public List<Character> increaseImportance(List<Character> characters, Function<Character, Double> f)
	{
   
        Listi newlis = new List<int>();
        for (Character c : List<Character> characters)
        {
           if (c.magical)
            { 
	            c.setImportanceFactor(Function<c, c.length()>);
            }
            newlis.add(c);
}
}

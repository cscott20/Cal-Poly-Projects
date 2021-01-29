import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

/** 
 * TO COMPLILE
javac -cp /home/akeen/public/junit-4.12.jar:/home/akeen/public/hamcrest-core-1.3.jar:. *.java

java -cp /home/akeen/public/junit-4.12.jar:/home/akeen/public/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore TestCases
*/

public class TestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testAveImportanceByAge()
   {

      CharacterClassifier classifier = new CharacterClassifier();

      List<Character> characters = new ArrayList<Character>();
      characters.add(new Character("Elsa", 25, 50.0, true));
      characters.add(new Character("Anna", 20, 50.0, false));
      characters.add(new Character("someone", 10, 5.0, false));

      assertEquals(classifier.aveImportanceByAge(characters, 21), 27.5, DELTA);
   }

   @Test
   public void testIncreaseImportance()
   {

      CharacterClassifier classifier = new CharacterClassifier();

	  List<Character> characters = new ArrayList<Character>();
      characters.add(new Character("Elsa", 25, 50.0, true));
      characters.add(new Character("Anna", 20, 50.0, false));
      characters.add(new Character("someone", 10, 5.0, false));

	  List<Character> updated = classifier.increaseImportance(characters, CharacterClassifier.updateImportance);
	  
      assertEquals(updated.get(0).getImportanceFactor(), 55.0, DELTA);
      assertEquals(updated.get(1).getImportanceFactor(), 50.0, DELTA);
      assertEquals(updated.get(2).getImportanceFactor(), 5., DELTA);
   }

   @Test 
   public void testComparator()
   {

    /*
     * TODO (6 points): Make a Comparator variable named comp.
     *   Initialize it using lamdas, key extractors, or another class.
     * The comparator must order Characters in alphabetical order by name.
     * Ties should be broken in descending order by age. 
     */


	  List<Character> characters = new ArrayList<Character>();
      characters.add(new Character("Elsa", 25, 50.0, true));
      characters.add(new Character("Anna", 5, 50.0, false));
      characters.add(new Character("Anna", 20, 50.0, false));
      characters.add(new Character("someone", 10, 5.0, false));

      assertTrue(comp.compare(characters.get(0), characters.get(1)) > 0);
      assertTrue(comp.compare(characters.get(1), characters.get(2)) > 0);
   }
}

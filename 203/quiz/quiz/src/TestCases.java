import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   @Test
   public void testGetTeamWithMaxFire()
   {
      PokemonTracker track = new PokemonTracker();

      Map<String, List<Trainer>> trainersFromTeam = new HashMap<>();
      Map<String, List<Pokemon>> pokemonFromTrainer = new HashMap<>();

      //fill map of trainers per team color
      trainersFromTeam.put("red",
         Arrays.asList(
            new Trainer("1", "kirsten"),
            new Trainer("2", "julie"),
            new Trainer("3", "katie")));

      trainersFromTeam.put("yellow",
         Arrays.asList(
            new Trainer("4", "erik")));

      trainersFromTeam.put("blue",
         null);

      // fill map of pokemon per trainer id
      pokemonFromTrainer.put("1",
         Arrays.asList(
            new Pokemon("1", "squirtle", "water"),
            new Pokemon("2", "pikachu", "electric"),
            new Pokemon("8", "charmander", "fire")));

      pokemonFromTrainer.put("2",
         Arrays.asList(
            new Pokemon("3", "arcanine", "fire")));

      pokemonFromTrainer.put("3",
         null);

      pokemonFromTrainer.put("4",
         Arrays.asList(
            new Pokemon("4", "charmander", "fire")));


      assertEquals("red", 
         PokemonTracker.getTeamWithMaxFire(trainersFromTeam, pokemonFromTrainer));
   }
}

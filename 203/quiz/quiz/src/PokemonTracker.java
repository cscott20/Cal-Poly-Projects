import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PokemonTracker {


   /**
    * Returns team with the most number of fire pokemon out of the teams.
    * If the teams are tied, break the tie however you'd like.
    * If no teams have a fire pokemon, return an empty string.
    */
   public static String getTeamWithMaxFire(
           Map<String, List<Trainer>> trainersFromTeam,
           Map<String, List<Pokemon>> pokemonFromTrainer) {
      String mostFire = "";
      int firemaxSoFar = 0;
      for (String teamName : trainersFromTeam.keySet()) {
         //passing in a team name
         int firepoke = 0;
        if (pokemonFromTrainer != null)
        {
        if (trainersFromTeam.get(teamName) != null){
        for (Trainer trainer: trainersFromTeam.get(teamName))
        {
            if (pokemonFromTrainer.get(trainer)!= null){
         for (Pokemon pokemon : pokemonFromTrainer.get(trainer)) {
            if ( pokemon != null && pokemon.getType() != null && pokemon.getType().equals("fire")) {
               firepoke += 1;
            }
        }}}
         if (firepoke >= firemaxSoFar)
         {
            mostFire = teamName;
            firemaxSoFar = firepoke;
         }
         }}
       }   
        
      
      return mostFire;
   }


}

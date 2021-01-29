//Charlie Scott
//Lab1
import org.json.JSONObject;
import org.json.*;
import java.io.*;
import java.util.*;
class correlation {
    
    public static void main(String[] args){
   
    try{
    // JSONTokener is the org.json wrapper around any Reader object 
    JSONTokener jsonData = new JSONTokener(new FileReader(new File("daily.json")));

    // Everything from the p.json file is converted into a JSON array
    // in one fell swoop
    JSONArray covidData = new JSONArray(jsonData);
    Map<String, ArrayList<Integer>> posData = new HashMap<String, ArrayList<Integer>>();
    Map<String, ArrayList<Integer>> deathData = new HashMap<String, ArrayList<Integer>>();

    for (int i = 0; i < covidData.length(); i++)
    {
        ArrayList<Integer> dataPos = new ArrayList<Integer>();
        ArrayList<Integer> dataDead = new ArrayList<Integer>();
        JSONObject stateData = covidData.getJSONObject(i);
        String state = stateData.optString("state");
        if ((!stateData.optString("positive").equals("") && Integer.parseInt(stateData.optString("positive")) > 0 )
            || (!stateData.optString("death").equals("") && Integer.parseInt(stateData.optString("death")) > 0 ))
        {
            posData.put(state, dataPos);
            deathData.put(state, dataDead);
        }
    }


    for (int i = 0; i < covidData.length(); i++)
    {
        JSONObject stateData = covidData.getJSONObject(i);
        String state = stateData.optString("state");
        if ((!stateData.optString("positive").equals("") && Integer.parseInt(stateData.optString("positive")) > 0 ))
        {
            (posData.get(state)).add(Integer.parseInt(stateData.optString("positive"))); 
        }
        if ((!stateData.optString("death").equals("") && Integer.parseInt(stateData.optString("death")) > 0 ))
        {
            (deathData.get(state)).add(Integer.parseInt(stateData.optString("death"))); 
        }
    }
    

    for (String state : posData.keySet())
    {
        int min = Math.min(posData.get(state).size(), deathData.get(state).size());
        posData.put(state, new ArrayList<Integer>(posData.get(state).subList(0, min)));
        deathData.put(state, new ArrayList<Integer>(deathData.get(state).subList(0, min)));
    }

    System.out.println("State, Pearson Correlation");
    ArrayList<String> sortedStates = new ArrayList<String>(posData.keySet());
    Collections.sort(sortedStates);
    for (String state : sortedStates)
    {

        if (posData.get(state).size() > 0)
        {
        Double corr = pCorr(posData.get(state), deathData.get(state));
            if (!(corr == null))
            {
                System.out.print(state);
                System.out.print(", ");
                System.out.print(corr);
                System.out.println();
            }
        }
    }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static Double pCorr(ArrayList<Integer> positive, ArrayList<Integer> death)
    {
        double numerator = 0;
        double dpos = 0;
        double ddeath = 0;
        double averageDeath = death
            .stream()
            .mapToDouble(a -> a)
            .average().getAsDouble();

        double averagePos = positive
            .stream()
            .mapToDouble(a -> a)
            .average().getAsDouble();

        for (int i = 0; i < positive.size(); i ++)
        {
            numerator += (positive.get(i) - averagePos) * (death.get(i) - averageDeath);
            dpos += Math.pow((positive.get(i) - averagePos),2);
            ddeath += Math.pow((death.get(i) - averageDeath), 2);
        }

        double denom = (Math.pow(dpos, .5) * (Math.pow(ddeath, .5)));
        if (denom == 0)
        {return null;}
        return (numerator / (Math.pow(dpos, .5) * (Math.pow(ddeath, .5))));
    }


}

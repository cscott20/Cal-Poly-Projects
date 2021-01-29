import org.json.JSONObject;
import org.json.*;
import java.io.*;
import java.util.*;
class stateInfo {
    
    public static void main(String[] args){
   
    try{
 	
    // JSONTokener is the org.json wrapper around any Reader object 
    JSONTokener jsonData = new JSONTokener(new FileReader(new File("daily.json")));

    // Everything from the p.json file is converted into a JSON array
    // in one fell swoop
    JSONArray covidData = new JSONArray(jsonData);
    HashMap<String, ArrayList<Integer>> newData = new HashMap<String, ArrayList<Integer>>();
    for (int i = 0; i < covidData.length(); i++)
    {
        ArrayList<Integer> data = new ArrayList<Integer>();
        JSONObject stateData = covidData.getJSONObject(i);
        String state = stateData.optString("state");
        int date = Integer.parseInt(stateData.optString("date"));
       // if (!stateData.optString("positive").equals("") && (Integer.parseInt(stateData.optString("positive")) >= 100))
       // {
            newData.put(state, data);
       // }
    } 
    for (int i = 0; i < covidData.length(); i++)
    {
        JSONObject stateData = covidData.getJSONObject(i);
        String state = stateData.optString("state");
        int date = Integer.parseInt(stateData.optString("date"));
        int cases = 0;
        int dateOf100;
        int daysSince100;
        if (!stateData.optString("positive").equals(""))
        {
            cases = Integer.parseInt(stateData.optString("positive"));
        }
        if (cases >= 100)
        {
            (newData.get(state)).add(Integer.parseInt(stateData.optString("positiveIncrease"))); 
        }
    } 
    //HashMap<String, ArrayList<Integer>> newData = new HashMap<String, ArrayList<Integer>>();
    String output = ("[");
    for (String state : newData.keySet())
    {
        output += ("{");
        output += ("state: ");
        output += (state);
        output += (", daySince100: ");
        int daysSince100 = 0;
        double averageCases = 0;
        ArrayList<Integer> dailyNewCases = new ArrayList<Integer>();
        ArrayList<Integer> dailyCoeff = new ArrayList<Integer>();

        //calculating
        Collections.reverse(newData.get(state));
        dailyNewCases = newData.get(state);
        if (dailyNewCases.size() == 0)
        {
            output += (", daySince100: ");
            output += (0);
            output += (", averageCases: ");
            output += (0);
            output += (", dailyNewCases: ");
            output += (0);
            output += (", dailyCoeff: ");
            output += (0);
            output += ("}, ");
       
        }
        else
        {


            averageCases = newData.get(state)
                .stream()
                .mapToDouble(a -> a)
                .average().getAsDouble();
            //printing
            output += (", daySince100: ");
            output += (newData.get(state).size());
            output += (", averageCases: ");
            output += (averageCases);
            output += (", dailyNewCases: ");
            output += (newData.get(state));
            output += (", dailyCoeff: ");
            ArrayList<Integer> coeff = new ArrayList<Integer>();
            for (int i = 1; i < newData.get(state).size(); i++)
            {
                if(newData.get(state).get(i-1) == 0)
                {
                    coeff.add(0);    
                }
                else
                {
                    coeff.add(newData.get(state).get(i) / newData.get(state).get(i-1));
                }
            }
            output += (coeff);
            output += ("}, ");
        } 
        }
        output = output.substring(0,output.length()-2);
        output = output + "]";
    System.out.println(output); 
   
    } catch (Exception e) {
        System.out.println(e);
    }
}
}

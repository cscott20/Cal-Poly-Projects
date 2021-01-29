import org.json.JSONObject;
import org.json.*;

import java.io.*;

class jsonArrayTest {

 public static void main(String[] args){
   
   try{
 	
  // JSONTokener is the org.json wrapper around any Reader object 
  JSONTokener t = new JSONTokener(new FileReader(new File("p.json")));

  // Everything from the p.json file is converted into a JSON array
  // in one fell swoop
  JSONArray a = new JSONArray(t);

  // individual JSON objects can now be extracted from the JSON array

   JSONObject o =  a.getJSONObject(0);
   JSONObject p =  a.getJSONObject(1);   
     
   System.out.print(o);
   System.out.println(); 
   System.out.println();     
   System.out.print(p);
   System.out.println();
   } catch (Exception e) {
     System.out.println("Ouch!");
     System.out.println(e);
   }

   System.out.println();
   }
}

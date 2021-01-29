//Charlie Scott
//cscott20@calpoly.edu

import org.apache.hadoop.io.IntWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.LongWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.Text;        // Hadoop's serialized String wrapper class
import org.apache.hadoop.mapreduce.Mapper; // Mapper class to be extended by our Map function
import org.apache.hadoop.mapreduce.Reducer; // Reducer class to be extended by our Reduce function
import org.apache.hadoop.mapreduce.Job; // the MapReduce job class that is used a the driver
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; // class for "pointing" at input file(s)
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; // class for "pointing" at output file
import org.apache.hadoop.fs.Path;                // Hadoop's implementation of directory path/filename
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat; // input format for key-value files
import org.apache.hadoop.conf.Configuration; // Hadoop's configuration object
// Exception handling
import java.lang.Math.*;
import java.io.IOException;
import java.util.*;
import java.lang.String;

public class Dice {


// Mapper  Class Template


public static class SwitchMapper 
     extends Mapper< Text, Text, Text, Text > {
public void map(Text key, Text value, Context context)
      throws IOException, InterruptedException {
    String fileName = context.getInputSplit().toString().split(":")[2].split("/Guttenberg/")[1];
    String words[] = value.toString().split(" |\\--");
    for (String word : words){
        context.write(new Text(fileName), new Text(word));}
}                                
}

public static class SwitchReducer 
      extends  Reducer< Text, Text, Text, HashMap<String, String>> {
public void reduce( Text key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {
    ArrayList<String> stops = new ArrayList<String>(Arrays.asList(" i", "i" ,"a" ,"about" ,"an" ,"are" ,"as" ,"at" ,"be" ,"by" ,"com" ,"for" ,"from" ,"how" ,"in" ,"is" ,"it","of" ,"on" ,"or" ,"that" ,"the" ,"this" ,"to" ,"was" ,"what" ,"when" ,"where" ,"who" ,"will" ,"with" ,"the", "www", " ", "\t", "\n", "", "-"));
    HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
    for (Text val : values) {
        String word = val.toString().toLowerCase().replaceAll("[,\\s ‘’?-_!\";'”:.\\]\\[]","").replaceAll("“","").replaceAll("\\s","");
        if (!stops.contains(word)){
            if (wordCount.containsKey(word)){
                wordCount.put(word, wordCount.get(word) + 1);}
            else{
                wordCount.put(word, 1);}
            }}
    HashMap<String, String> top100 = new HashMap<String, String>();
   int i = 0;
    for (String word : sortByValue(wordCount).keySet()) {
       if (i < 100){
            top100.put(word, Integer.toString(wordCount.get(word)));
       i++;}}
    top100.put("--Book Title Entry--",key.toString()); 
    context.write(new Text("1"), top100);
}}
private static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
    if (index == data.length) {
        int[] combination = data.clone();
        combinations.add(combination);
    } else if (start <= end) {
        data[index] = start;
        helper(combinations, data, start + 1, end, index + 1);
        helper(combinations, data, start + 1, end, index);
    }
}
public static List<int[]> generate(int n, int r) {
    List<int[]> combinations = new ArrayList<>();
    helper(combinations, new int[r], 0, n-1, 0);
    return combinations;
}



 public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
    { 
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 


public static class DiceMapper
     extends Mapper< LongWritable, Text, Text, Text > {
public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    context.write(new Text("1"), new Text(value));
}}

public static class DiceReducer 
      extends  Reducer< Text, Text, Text, Double> {
public void reduce(Text key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {
        String[] wordCount;
        String title = "";
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        ArrayList<String> words = new ArrayList<String>();
        for (Text val2 : values){
            wordCount = val2.toString().split("\t")[1].replaceAll("\\{", "").replaceAll("\\}","").split(", ");
            for (String wordPair : wordCount){
                if (wordPair.split("=")[0].equals("--Book Title Entry--")){
                    title = wordPair.split("=")[1];}
                else{
                    words.add(wordPair.split("=")[0]);}
             }
        map.put(title, (ArrayList<String>)words.clone());
        words.clear();
        title = "";}
        HashMap<Integer, String> combos = new HashMap<Integer, String>();
        int i = 0;
        for(String title2 : map.keySet()){
            combos.put(i, title2);
            i++;
        }
        HashMap<String, Double> output = new HashMap<String, Double>();
        
        List<int[]> combinations = generate(i, 2);
        double dice = 0;
        for (int[] pair : combinations){
            ArrayList<String> nList = map.get(combos.get(pair[0]));
            ArrayList<String> mList = map.get(combos.get(pair[1]));
            ArrayList<String> same = (ArrayList<String>)nList.clone();
            same.retainAll(mList);
            dice = (2.0 * same.size()) /((1.0 * nList.size()) + (1.0 * mList.size()));
            output.put((combos.get(pair[0]) + " & " + combos.get(pair[1])), dice);
            }
    for (String key2 : output.keySet()){ 
    context.write(new Text(key2), output.get(key2));
    }
}}

  public static void main(String[] args) throws Exception {
      Configuration conf = new Configuration();
      conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator",",");
     Job  job = Job.getInstance(conf);  
      job.setJarByClass(Dice.class);  
       KeyValueTextInputFormat.addInputPath(job, new Path("/data/", "Guttenberg/*"));
       FileOutputFormat.setOutputPath(job, new Path("./test/","books"));
       job.setInputFormatClass(KeyValueTextInputFormat.class);
      job.setMapperClass(SwitchMapper.class);
      job.setReducerClass(SwitchReducer.class);
       job.setOutputKeyClass(Text.class);
       job.setOutputValueClass(Text.class);
      job.setJobName("Charlie"); 
      job.waitForCompletion(true);
   Job DiceJob = Job.getInstance();
   DiceJob.setJarByClass(Dice.class);
   FileInputFormat.addInputPath(DiceJob, new Path("./test/books", "part-r-00000"));
   FileOutputFormat.setOutputPath(DiceJob, new Path("./test/","dice")); 
   DiceJob.setMapperClass(DiceMapper.class);
   DiceJob.setReducerClass(DiceReducer.class);
   DiceJob.setOutputKeyClass(Text.class);
   DiceJob.setOutputValueClass(Text.class);
   DiceJob.setJobName("Dice em All!");
    System.exit(DiceJob.waitForCompletion(true) ? 0 : 1);}
    }

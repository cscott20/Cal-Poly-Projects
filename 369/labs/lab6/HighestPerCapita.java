//Charlie Scott
//cscott20@calpoly.edu
import org.apache.hadoop.io.IntWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.FloatWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.LongWritable; // Hadoop's serialized int wrapper class
import org.apache.hadoop.io.Text;        // Hadoop's serialized String wrapper class
import org.apache.hadoop.mapreduce.Mapper; // Mapper class to be extended by our Map function
import org.apache.hadoop.mapreduce.Reducer; // Reducer class to be extended by our Reduce function
import org.apache.hadoop.mapreduce.Job; // the MapReduce job class that is used a the driver
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; // class for "pointing" at input file(s)
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat; // class for  standard text input
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs; // class for "pointing" at input file(s)
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; // class for "pointing" at output file
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat; // key-value input files
import org.apache.hadoop.conf.Configuration; // Hadoop's configuration object
import org.apache.hadoop.fs.Path;                // Hadoop's implementation of directory path/filename
import java.util.*;
import org.json.*;
import org.json.JSONObject;
import java.io.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import com.alexholmes.json.mapreduce.MultiLineJsonInputFormat;
import java.io.PrintWriter;
import java.io.StringWriter;

public class HighestPerCapita extends Configured implements Tool {

// Mapper  Class Template

public static class IowaMapper 
     extends Mapper<Text, Text, Text, FloatWritable> {

public void map(Text key, Text value, Context context)
      throws IOException, InterruptedException {
      String record[] = value.toString().split(",");
      String myKey = record[1].split(" ")[0];
      Text county = new Text(myKey.replaceAll("'", ""));
      String totalDollars = record[record.length - 1];
      Text outKey = county;
      FloatWritable outVal = new FloatWritable(Float.parseFloat(totalDollars));
        if(!(outKey.toString().isEmpty())){
     context.write(outKey, outVal);}
    }                                
}

public static class CountiesMapper 
     extends Mapper<LongWritable, Text, Text, FloatWritable> {
public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
      try{
      JSONObject json = new JSONObject(value.toString());
      Iterator<String> iter = json.keys();
        Text outputKey = new Text("");
        while (iter.hasNext()) {
        String jsonKey = iter.next();
        if((jsonKey.equals("county"))){
            outputKey.set(String.valueOf(json.getString(jsonKey)).replaceAll("'", ""));}
        else if(jsonKey.equals("population")){
          context.write(outputKey, new FloatWritable(-1 * json.getInt(jsonKey)));}
        } //if
      }   // while 

     catch (Exception e) {
 StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            context.write(new Text(exceptionAsString), new FloatWritable(32));
}}}

//  Reducer Class Template

public static class JoinReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< Text, FloatWritable, Text, Text> {

public void reduce( Text key, Iterable<FloatWritable> values, Context context)
     throws IOException, InterruptedException {
    
    float totalDollars = 0;
    float population = 0;
  for (FloatWritable val : values){
    if (val.get() > 0){
    totalDollars = totalDollars + val.get();
    }
    else{
    population = population - val.get();
  }}
  Text result = new Text(Float.toString((totalDollars / population ))); 
  context.write(key, result);   

 } // reduce


} // reducer


//  MapReduce Driver

public static class MaxMapper  
     extends Mapper< LongWritable, Text, Text, Text > {

public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

   context.write(new Text("1"), new Text(key + "---" + value));


 } // map

} // totalCountMapper

// MapReduce Reducer 2

public static class MaxReducer 
      extends  Reducer< Text, Text, Text, Text> {

@Override

public void reduce( Text key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {
    float max = 0;
    Text maxText = new Text("");
 for(Text val : values) {
   if (Float.valueOf(val.toString().split("---")[1].split("\t")[1]) > max){
        max = Float.valueOf(val.toString().split("---")[1].split("\t")[1]);
        maxText.set(val.toString().split("---")[1].split("\t")[0]);
    }
 }
     context.write(maxText, new Text(String.valueOf(max)));
 

 } // reduce
} // reducer


  // we do everything here in main()
  public int run(String[] args) throws Exception {

      Configuration conf = super.getConf();
      conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator",",");
     Job  job = Job.getInstance(conf);  
      job.setJarByClass(HighestPerCapita.class);  
       MultipleInputs.addInputPath(job, new Path("/data/", "iowa.csv"), KeyValueTextInputFormat.class, IowaMapper.class); 
       MultipleInputs.addInputPath(job, new Path("/data/", "counties-hadoop.json"), MultiLineJsonInputFormat.class, CountiesMapper.class);
       FileOutputFormat.setOutputPath(job, new Path("./test/","counties"));
      job.setReducerClass(JoinReducer.class);
       job.setOutputKeyClass(Text.class);
       job.setOutputValueClass(FloatWritable.class);
       MultiLineJsonInputFormat.setInputJsonMember(job, "county");
      job.setJobName("Filter Student List - Charlie");
    job.waitForCompletion(true);

   Job MaxJob = Job.getInstance();
   MaxJob.setJarByClass(HighestPerCapita.class);
   FileInputFormat.addInputPath(MaxJob, new Path("./test/counties", "part-r-00000"));
   FileOutputFormat.setOutputPath(MaxJob, new Path("./test/","countiesMax")); 
   MaxJob.setMapperClass(MaxMapper.class);
   MaxJob.setReducerClass(MaxReducer.class);
   MaxJob.setOutputKeyClass(Text.class);
   MaxJob.setOutputValueClass(Text.class);
   MaxJob.setJobName("Max em All!");
    return MaxJob.waitForCompletion(true) ? 0 : 1;}

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    int res = ToolRunner.run(conf, new HighestPerCapita(), args);
    System.exit(res);
  }
}

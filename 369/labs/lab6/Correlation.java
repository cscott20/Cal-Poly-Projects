//Charlie Scott
//csoctt20@calpoly.edu
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

public class Correlation {


// Mapper  Class Template


public static class SwitchMapper 
     extends Mapper< Text, Text, Text, Text > {
public void map(Text key, Text value, Context context)
      throws IOException, InterruptedException {

      String record[] = value.toString().split(",");
      String myKey = record[1];
      Text county = new Text(myKey);
      String itemDescription = record[record.length - 3];
      if (itemDescription.toLowerCase().contains("vodka")){
      context.write(county, new Text("vodka"));
      }
      if (itemDescription.toLowerCase().contains("rum")){
      context.write(county, new Text("rum"));
      }
    }                                
}


//  Reducer Class Template

public static class SwitchReducer 
      extends  Reducer< Text, Text, Text, Text> {
public void reduce( Text key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {
    int numRum = 0;
    int numVodka = 0;
  for (Text val : values) {
    if (val.toString().equals("vodka")){
        numVodka = numVodka + 1;
    }
    if (val.toString().equals("rum")){
        numRum = numRum + 1;
    }
  }
    if (!(key.toString().equals("''"))){
  context.write(key, new Text(Integer.toString(numRum) + "---" + Integer.toString(numVodka)));  } 
 }
}

public static class CorrMapper 
     extends Mapper< LongWritable, Text, Text, Text > {
public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
      context.write(new Text("1") , new Text(key.toString() + "---" + value.toString()));

}}

public static class CorrReducer 
      extends  Reducer< Text, Text, Text, Text> {
public void reduce( Text key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {
    int sumRum = 0;
    int sumVodka = 0;
    int count = 0;
    ArrayList<String> values2 = new ArrayList<String>();
  for (Text val : values) {
    values2.add(val.toString());
    String nums = ((val.toString()).split("\t")[1]);
    count += 1;
    sumRum += Integer.parseInt(nums.split("---")[0]);
    sumVodka += Integer.parseInt(nums.split("---")[1]);
    }

    double avgRum = sumRum / count;
    double avgVodka = sumVodka / count;
    int rumNum = 0;
    int vodkaNum = 0;
    double pearson = 0;
    double pearsonNum = 0;
    double pearsonDem1 = 0;
    double pearsonDem2 = 0;
    for (String val2 : values2){
    String nums = (val2.split("\t")[1]);
    rumNum = Integer.parseInt(nums.split("---")[0]);
    vodkaNum = Integer.parseInt(nums.split("---")[1]);
        pearson = 0;
        pearsonNum = 0;
        pearsonDem1 = 0;
        pearsonDem2 = 0;
        for (int i=0; i<count; i++){
            pearsonNum += (rumNum - avgRum) * (vodkaNum - avgVodka);  
            pearsonDem1 += Math.pow((rumNum - avgRum), 2);
            pearsonDem2 += Math.pow((vodkaNum - avgVodka), 2);  
        }
        pearson = pearsonNum/((Math.pow(pearsonDem1,.5)) * (Math.pow(pearsonDem1,.5)));   
        context.write(new Text(val2.split("\t")[0].split("---")[1]), new Text("Pearson: " + Double.toString(pearson)));
    }
}}


  public static void main(String[] args) throws Exception {
      Configuration conf = new Configuration();
      conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator",",");
     Job  job = Job.getInstance(conf);  
      job.setJarByClass(Correlation.class);  
       KeyValueTextInputFormat.addInputPath(job, new Path("/data/", "iowa.csv"));
       FileOutputFormat.setOutputPath(job, new Path("./test/","counties"));
       job.setInputFormatClass(KeyValueTextInputFormat.class);
      job.setMapperClass(SwitchMapper.class);
      job.setReducerClass(SwitchReducer.class);
       job.setOutputKeyClass(Text.class);
       job.setOutputValueClass(Text.class);
      job.setJobName("Filter Student List - Charlie"); 
      job.waitForCompletion(true);
   Job CorrJob = Job.getInstance();
   CorrJob.setJarByClass(Correlation.class);
   FileInputFormat.addInputPath(CorrJob, new Path("./test/counties", "part-r-00000"));
   FileOutputFormat.setOutputPath(CorrJob, new Path("./test/","correlation")); 
   CorrJob.setMapperClass(CorrMapper.class);
   CorrJob.setReducerClass(CorrReducer.class);
   CorrJob.setOutputKeyClass(Text.class);
   CorrJob.setOutputValueClass(Text.class);
   CorrJob.setJobName("Corr em All!");
    System.exit(CorrJob.waitForCompletion(true) ? 0 : 1);}
    }

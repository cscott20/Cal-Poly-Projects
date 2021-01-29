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

import java.io.IOException;
import java.util.*;

public class countySales {


// Mapper  Class Template


public static class SwitchMapper 
     extends Mapper< Text, Text, Text, Text > {

@Override   // we are overriding Mapper's map() method

// map methods takes three input parameters
// first parameter: input key 
// second parameter: input value
// third parameter: container for emitting output key-value pairs

public void map(Text key, Text value, Context context)
      throws IOException, InterruptedException {

      String record[] = value.toString().split(",");
      String myKey = record[1];
      Text county = new Text(myKey);
      String totalBottles = record[record.length - 2];
      String totalDollars = record[record.length - 1];
      Text outKey = county;
      Text outVal = new Text(totalBottles + " " + totalDollars );
      context.write(outKey, outVal);
    }                                
}


//  Reducer Class Template

public static class SwitchReducer   // needs to replace the four type labels with actual Java class names
      extends  Reducer< Text, Text, Text, Text> {

 // note: InValueType is a type of a single value Reducer will work with
 //       the parameter to reduce() method will be Iterable<InValueType> - i.e. a list of these values

@Override  // we are overriding the Reducer's reduce() method

// reduce takes three input parameters
// first parameter: input key
// second parameter: a list of values associated with the key
// third parameter: container  for emitting output key-value pairs

public void reduce( Text key, Iterable<Text> values, Context context)
     throws IOException, InterruptedException {


    float totalBottles = 0;
    float totalDollars = 0;
  for (Text val : values) {
    totalBottles = totalBottles + Float.parseFloat(val.toString().split(" ")[0]);
    totalDollars = totalDollars + Float.parseFloat(val.toString().split(" ")[1]);
  }
  Text result = new Text(Float.toString(totalBottles) +  ", " + Float.toString(totalDollars) + ", " + Float.toString(totalDollars/totalBottles));
  //Text result = new Text("Total Bottles: " + Float.toString(totalBottles) +  " Total Dollars: " + Float.toString(totalDollars) + " Average Price Per Bottle: " + Float.toString(totalDollars/totalBottles));
  context.write(key, result);   

 } // reduce


} // reducer


//  MapReduce Driver


  // we do everything here in main()
  public static void main(String[] args) throws Exception {

     // Step 0: let's  get configuration

      Configuration conf = new Configuration();
      conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator",","); // First comma in the line is the key - value separator


     // step 1: get a new MapReduce Job object
     Job  job = Job.getInstance(conf);  
     
    // step 2: register the MapReduce class
      job.setJarByClass(countySales.class);  

   //  step 3:  Set Input and Output files

       KeyValueTextInputFormat.addInputPath(job, new Path("/data/", "iowa.csv")); // put what you need as input file
       FileOutputFormat.setOutputPath(job, new Path("./test/","counties")); // put what you need as output file
       job.setInputFormatClass(KeyValueTextInputFormat.class);            // let's make input a CSV file

   // step 4:  Register mapper and reducer
      job.setMapperClass(SwitchMapper.class);
      job.setReducerClass(SwitchReducer.class);
  
   //  step 5: Set up output information
       job.setOutputKeyClass(Text.class); // specify the output class (what reduce() emits) for key
       job.setOutputValueClass(Text.class); // specify the output class (what reduce() emits) for value

   // step 6: Set up other job parameters at will
      job.setJobName("Filter Student List - Charlie");  // change this string to include your name

   // step 7:  ?
    
   // step 8: profit
      System.exit(job.waitForCompletion(true) ? 0:1);


  }// main()


} // MyMapReduceDriver


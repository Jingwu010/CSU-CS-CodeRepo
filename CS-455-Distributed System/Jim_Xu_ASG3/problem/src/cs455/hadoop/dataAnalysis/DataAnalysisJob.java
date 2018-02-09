// Author : Jimx

package cs455.hadoop.dataAnalysis;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * This is the main class. Hadoop will invoke the main method of this class.
 */
public class DataAnalysisJob {
    public static void main(String[] args) {
        try {
            Configuration conf = new Configuration();
            // Give the MapRed job a name. You'll see this name in the Yarn webapp.
            Job job1 = Job.getInstance(conf, "DataAnalysis");
            // Current class.
            job1.setJarByClass(DataAnalysisJob.class);
            // Mapper
            job1.setMapperClass(DataAnalysisFirstMapper.class);
            // Combiner. We use the reducer as the combiner in this case.
            job1.setCombinerClass(DataAnalysisCombiner.class);
            // Reducer
            job1.setReducerClass(DataAnalysisFirstReducer.class);
            // Outputs from the Mapper.
            job1.setMapOutputKeyClass(Text.class);
            job1.setMapOutputValueClass(WireFormat.class);
            // Outputs from Reducer. It is sufficient to set only the following two properties
            // if the Mapper and Reducer has same key and value types. It is set separately for
            // elaboration.
            job1.setOutputKeyClass(Text.class);
            job1.setOutputValueClass(WireFormat.class);
            // path to input in HDFS
            FileInputFormat.addInputPath(job1, new Path(args[0]));
            // path to output in HDFS
            FileOutputFormat.setOutputPath(job1, new Path(args[1]+"/firstOut"));
            // Block until the job is completed.
            job1.waitForCompletion(true);
            
            /* <------------------------- JoB_1 Finished --------------------------> */

            Job job2 = Job.getInstance(conf, "DataCompare");
            // Current class.
            job2.setJarByClass(DataAnalysisJob.class);
            // Mapper
            job2.setMapperClass(DataAnalysisSecondMapper.class);
            // Reducer
            job2.setReducerClass(DataAnalysisSecondReducer.class);
            // Outputs from the Mapper.
            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(Text.class);
            // Outputs from Reducer. It is sufficient to set only the following two properties
            // if the Mapper and Reducer has same key and value types. It is set separately for
            // elaboration.
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(Text.class);
            // path to input in HDFS
            FileInputFormat.addInputPath(job2, new Path(args[1]+"/firstOut"));
            // path to output in HDFS
            FileOutputFormat.setOutputPath(job2, new Path(args[1]+"/secondOut"));
            // Block until the job is completed.
            System.exit(job2.waitForCompletion(true) ? 0 : 1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}

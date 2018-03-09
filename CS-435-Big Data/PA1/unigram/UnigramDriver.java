package unigram;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class UnigramDriver {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Unigram");
        job.setJarByClass(UnigramDriver.class);

        // set number of reducer tasks
        job.setNumReduceTasks(3);

        job.setMapperClass(UnigramMapper.class);
        job.setCombinerClass(UnigramCombiner.class);
        job.setReducerClass(UnigramReducer.class);

        // mapper output key-value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(UnigramWritable.class);

        MultipleOutputs.addNamedOutput(job, "profile1", TextOutputFormat.class,
            Text.class, Text.class);
        MultipleOutputs.addNamedOutput(job, "profile2", TextOutputFormat.class,
            Text.class, Text.class);
        MultipleOutputs.addNamedOutput(job, "profile3", TextOutputFormat.class,
            Text.class, Text.class);
        // // reducer output key-value
        // job.setOutputKeyClass(Text.class);
        // job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

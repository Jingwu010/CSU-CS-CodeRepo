package tfidf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TfidfDriver extends Configured implements Tool{

    public int run(String[] args) throws Exception {

        Configuration conf1 = getConf();
        Job job1 = Job.getInstance(conf1);  
        job1.setJarByClass(TfidfDriver.class);
        job1.setJobName("TfidfPart1");

        FileInputFormat.setInputPaths(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1] + "/part1"));

        job1.setMapperClass(TfidfMapper1.class);
        job1.setReducerClass(TfidfReducer1.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);

        job1.waitForCompletion(true);

        /* --------------------------- */
        Configuration conf2 = getConf();
        Job job2 = Job.getInstance(conf2);
        job2.setJarByClass(TfidfDriver.class);
        job2.setJobName("TfidfPart2");

        FileInputFormat.setInputPaths(job2, new Path(args[1] + "/part1"));
        FileOutputFormat.setOutputPath(job2, new Path(args[1] + "/part2"));

        job2.setMapperClass(TfidfMapper2.class);
        job2.setReducerClass(TfidfReducer2.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);

        job2.waitForCompletion(true);

        Counter UniqueDocCounter = job2.getCounters().findCounter("UniqueDocument", "CNT");

        /* --------------------------- */
        Configuration conf3 = getConf();
        Job job3 = Job.getInstance(conf3);
        job3.setJarByClass(TfidfDriver.class);
        job3.setJobName("TfidfPart3");

        FileInputFormat.setInputPaths(job3, new Path(args[1] + "/part2"));
        FileOutputFormat.setOutputPath(job3, new Path(args[1] + "/part3"));

        job3.setMapperClass(TfidfMapper3.class);
        job3.setReducerClass(TfidfReducer3.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(Text.class);

        job3.waitForCompletion(true);

        /* --------------------------- */
        Configuration conf4 = getConf();
        conf4.setLong("UniqueDocument", UniqueDocCounter.getValue());
        Job job4 = Job.getInstance(conf4);
        job4.setJarByClass(TfidfDriver.class);
        job4.setJobName("TfidfPart4");

        FileInputFormat.setInputPaths(job4, new Path(args[1] + "/part3"));
        FileOutputFormat.setOutputPath(job4, new Path(args[1] + "/part4"));

        job4.setMapperClass(TfidfMapper4.class);
        job4.setOutputKeyClass(Text.class);
        job4.setOutputValueClass(DoubleWritable.class);

        job4.waitForCompletion(true);

        /* --------------------------- */
        Configuration conf5 = getConf();
        Job job5 = Job.getInstance(conf5);
        job5.setJarByClass(TfidfDriver.class);
        job5.setJobName("TfidfPart5");

        System.out.println("Driver : " + new Path(args[1] + "/part4/part-r-00000").toUri());
        job5.addCacheFile(new Path(args[1] + "/part4/part-r-00000").toUri());

        FileInputFormat.setInputPaths(job5, new Path(args[0]));
        FileOutputFormat.setOutputPath(job5, new Path(args[1] + "/final"));

        job5.setMapperClass(TfidfMapper5.class);
        job5.setReducerClass(TfidfReducer5.class);
        job5.setOutputKeyClass(Text.class);
        job5.setOutputValueClass(Text.class);

        return (job5.waitForCompletion(true) ? 0 : 1);
    }

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new TfidfDriver(), args);
        System.exit(exitCode);
	}
}
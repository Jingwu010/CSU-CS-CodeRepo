package tfidf;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/*
    Input ({DocumentID, unigram}, [1 ...])
    Output ({DocumentID, unigram}, frequency)
 */
public class TfidfReducer1 extends Reducer<Text, IntWritable, Text, IntWritable>{

	private IntWritable intWritable = new IntWritable();
	
	public void reduce(Text key, Iterable<IntWritable> values, Context context
		) throws IOException, InterruptedException {

		int wordCount = 0;
		for (IntWritable value : values) {
	        wordCount += value.get();
	    }
	    intWritable.set(wordCount);
	    context.write(key, intWritable);
	}
}
package tfidf;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/*
    Input (unigram, [{DocumentID, TFvalue} ...]) 
    Output (unigram, {DocumentID, TFvalue, ni})
 */
public class TfidfReducer3 extends Reducer<Text, Text, Text, Text>{
	public void reduce(Text key, Iterable<Text> values, Context context
		) throws IOException, InterruptedException {

		List<String> valuesList = new ArrayList<>();
		for (Text tvalue : values)
			valuesList.add(tvalue.toString());

		int size = 0;
		for (String value : valuesList)
			size ++;
		
		for (String value : valuesList)
			context.write(key, new Text(value.toString() + "," + Integer.toString(size)));
	}
}
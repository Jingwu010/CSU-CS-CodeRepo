package tfidf;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.io.Text;

/*
    Input (DocumentID, [{unigram, frequency} ... ])
    Output (DocumentID, {unigram, frequency, TFvalue})
 */
public class TfidfReducer2 extends Reducer<Text, Text, Text, Text>{

	private Set<String> uniqueIDs = new HashSet<String>();

	public void reduce(Text key, Iterable<Text> values, Context context
		) throws IOException, InterruptedException {
		if (!uniqueIDs.contains(key.toString())){
			uniqueIDs.add(key.toString());
			context.getCounter("UniqueDocument", "CNT").increment(1); //Increment counters
		}

		List<String> valuesList = new ArrayList<>();
		for (Text tvalue : values)
			valuesList.add(tvalue.toString());

		int maxFrequency = -1;
		for (String value : valuesList) {
			String[] segs = value.split(",");

			String unigram = segs[0];
			int frequency = Integer.parseInt(value.split(",")[1]);

			if (maxFrequency < frequency)
				maxFrequency = frequency;
			
	    }
	    for (String value : valuesList) {
			String[] segs = value.split(",");

			String unigram = segs[0];
			int frequency = Integer.parseInt(segs[1]);

			double tfValue = 0.5 + 0.5 * (frequency / (double)maxFrequency);
			context.write(key, new Text(value + "," + Double.toString(tfValue)));
	    }
	}
}
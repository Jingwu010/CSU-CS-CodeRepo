package unigram;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class UnigramCombiner
	extends Reducer<Text, UnigramWritable, Text, UnigramWritable>{

	private IntWritable result = new IntWritable();
 
	public void combine(Text key, Iterable<UnigramWritable> values,
                       Context context) throws IOException, InterruptedException {
		
		Map<String, Integer> wordmap = new HashMap<String, Integer>();
		int partition = Character.getNumericValue(key.toString().charAt(0));

		switch (partition) {
			case 1:
				context.write(new Text(key.toString()), new UnigramWritable());
				break;
			case 2:
				for (UnigramWritable val : values) {

					String word = val.getWord().toString();
					int count = val.getCount().get();
					if (wordmap.containsKey(word))
						wordmap.put(word, wordmap.get(word) + count);
					else wordmap.put(word, count);
				}

				for (Map.Entry<String, Integer> entry : wordmap.entrySet()){
					result.set(entry.getValue());
					UnigramWritable uwOut = new UnigramWritable(new Text(entry.getKey()), result);
					context.write(new Text(key.toString()), uwOut);	
				}
				break;
			case 3:
				int sum = 0;
				for (UnigramWritable val : values) {
					sum += val.getCount().get();
				}
				result.set(sum);
				UnigramWritable uwOut = new UnigramWritable(new Text(""), result);
				context.write(new Text(key.toString()), uwOut);
				break;
		}
		
	}

}


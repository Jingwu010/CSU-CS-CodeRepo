
package unigram;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.Map.Entry;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class UnigramReducer
	extends Reducer<Text, UnigramWritable, Text, Text>{

	private IntWritable result = new IntWritable();
	private MultipleOutputs mos;
	private Map<String, Integer> wordmap2 = new HashMap<String, Integer>();;

	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		mos = new MultipleOutputs(context);
	}

	public void reduce(Text key, Iterable<UnigramWritable> values,
                       Context context) throws IOException, InterruptedException {
		
		Map<String, Integer> wordmap = new HashMap<String, Integer>();
		int partition = Character.getNumericValue(key.toString().charAt(0));

		switch (partition) {
			case 1:
				mos.write("profile1", new Text(key.toString().substring(1)), new Text(""));
				break;
			case 2:
				for (UnigramWritable val : values) {

					String word = val.getWord().toString();
                    int count = val.getCount().get();
					if (wordmap.containsKey(word))
						wordmap.put(word, wordmap.get(word) + count);
					else wordmap.put(word, count);
				}
                
                // https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
				List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(wordmap.entrySet());

				Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
		            public int compare(Map.Entry<String, Integer> o1,
		                    Map.Entry<String, Integer> o2) {
		                return o2.getValue().compareTo(o1.getValue());
		            }
		        });

				for (Map.Entry<String, Integer> entry : list){
                    mos.write("profile2", new Text(key.toString().substring(1)), 
						new Text("\t" + entry.getKey() + "\t" + entry.getValue()));	
				}
				break;
			case 3:
				for (UnigramWritable val : values) {
					String word = val.getWord().toString();
					int count = val.getCount().get();
					if (wordmap2.containsKey(word))
						wordmap2.put(word, wordmap2.get(word) + count);
					else wordmap2.put(word, count);
				}
				break;
		}
		
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		// https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
		List<Entry<String, Integer>> list2 = new LinkedList<Entry<String, Integer>>(wordmap2.entrySet());

		Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                    Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
		for (Map.Entry<String, Integer> entry : list2){
			mos.write("profile3", new Text(entry.getKey()), new Text("\t" + entry.getValue()));	
		}
		mos.close();
	}
}


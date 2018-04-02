package tfidf;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/*
    Input (DocumentID, [{eachSentence, SentenceTF-IDF}]...)
    Output (DocumentID, [top 3 eachSentences])
 */
public class TfidfReducer5 extends Reducer<Text, Text, Text, Text>{

	/*
        https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
     */
    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		System.out.println("fuck");
		Map<String, Double> document_sentences = new HashMap<String, Double>();
		for (Text tvalue : values) {
			String value = tvalue.toString();
			// eachSentence, SentenceTF-IDF
			String[] segs = value.split("<=>");
			String sentence = segs[0];
			Double score = Double.parseDouble(segs[1]);
			document_sentences.put(sentence, score);
		}
		Map<String, Double> sorted_document_sentences = this.sortByValue(document_sentences);
		int flag = 0;
        for (Map.Entry<String,Double> entry : sorted_document_sentences.entrySet()) {
            flag += 1;
            context.write(key, new Text(entry.getKey()));
            if (flag >= 3) break;
        }
	}
}





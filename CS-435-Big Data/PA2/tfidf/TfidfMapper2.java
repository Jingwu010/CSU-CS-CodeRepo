package tfidf;

import java.io.IOException;
import java.lang.StringBuffer;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;

/*
    Input ({DocumentID, unigram}, frequency)
    Output (DocumentID, {unigram, frequency})
 */
public class TfidfMapper2 extends Mapper<Object, Text, Text, Text>{

	public void map(Object key, Text value, Context context
		) throws IOException, InterruptedException {

        String line = value.toString();
        if (line.isEmpty()) return;

        // {DocumentID, unigram} \t frequency
        String[] segs = line.split("\t");
        String documentID = segs[0].split(",")[0];
        String unigram = segs[0].split(",")[1];
        // DocumentID -- Single Word
    	context.write(new Text(documentID), new Text(unigram + "," + segs[1]));
	}
}
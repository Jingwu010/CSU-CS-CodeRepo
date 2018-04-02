package tfidf;

import java.io.IOException;
import java.lang.StringBuffer;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;

/*
    Input (DocumentID, {unigram, frequency, TFvalue})
    Output (unigram, {DocumentID, TFvalue}) 
 */
public class TfidfMapper3 extends Mapper<Object, Text, Text, Text>{

	public void map(Object key, Text value, Context context
		) throws IOException, InterruptedException {
		String line = value.toString();

        String DocumentID = line.split("\t")[0];

        // unigram, frequency, TFvalue
        String[] segs = line.split("\t")[1].split(",");
        
    	context.write(new Text(segs[0]), new Text(DocumentID + "," + segs[2]));
	}
}
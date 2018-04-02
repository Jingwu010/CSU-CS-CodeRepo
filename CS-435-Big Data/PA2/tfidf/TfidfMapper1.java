package tfidf;

import java.io.IOException;
import java.lang.StringBuffer;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/*
    Input (Title <====> DocumentID <====> Text)
    Output ({DocumentID, unigram}, 1)
 */
public class TfidfMapper1 extends Mapper<Object, Text, Text, IntWritable>{
	
	private final static IntWritable one = new IntWritable(1);
	private final static String wordbank = "abcdefghijklmnopqrstuvwxyz1234567890";
	
	public void map(Object key, Text value, Context context
		) throws IOException, InterruptedException {

		// StringTokenizer itr = new StringTokenizer(value.toString());
        String line = value.toString();
        if (line.isEmpty()) return;

        String[] segs = line.split("<====>");
        // Title - DocumentID - Text
        String text = segs[2];
        for (String word : text.split(" ")){

        	StringBuffer sb = new StringBuffer();
        	for (char c : word.toCharArray()){
        		char cur = Character.toLowerCase(c);
        		if(wordbank.indexOf(cur) != -1)
        			sb.append(cur);
        	}

        	if (sb.toString().isEmpty()) continue;
        	context.write(new Text(segs[1] + "," + sb.toString()), one);
        }
	}
}
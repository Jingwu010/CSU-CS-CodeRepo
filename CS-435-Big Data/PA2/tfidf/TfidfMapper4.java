package tfidf;

import java.io.IOException;
import java.lang.StringBuffer;
import java.lang.Math;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;

/*
    Input (unigram, {DocumentID, TFvalue, ni})
    Output ({DocumentID, unigram}, TF-IDFvalue) 
    IDFi = log10(N/ni) 
    TF-IDF = TFij x IDFi
 */
public class TfidfMapper4 extends Mapper<Object, Text, Text, DoubleWritable>{

    private long nUnique = 0;
    private DoubleWritable doubleWritable = new DoubleWritable();

    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        this.nUnique  = context.getConfiguration().getLong("UniqueDocument", 0);
    }

	public void map(Object key, Text value, Context context
		) throws IOException, InterruptedException {
        
        String line = value.toString();

        String unigram = line.split("\t")[0];

        // DocumentID, TFvalue, ni
        String[] segs = line.split("\t")[1].split(",");

        double TFvalue = Double.parseDouble(segs[1]);
        double IDFi = Math.log10(nUnique / Double.parseDouble(segs[2]));
        double TF_IDF = TFvalue * IDFi;
        doubleWritable.set(TF_IDF);
    	context.write(new Text(segs[0] + "," + unigram), doubleWritable);
	}
}
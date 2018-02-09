// Author : Jimx

package cs455.hadoop.dataAnalysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer: Input to the reducer is the output from the mapper. It receives word, list<count> pairs.
 * Sums up individual counts per given word. Emits <word, total count> pairs.
 * <KEYIN,VALUEIN,KEYOUT,VALUEOUT>
 * 
 */
public class DataAnalysisCombiner extends Reducer<Text, WireFormat, Text, WireFormat> {

    @Override
    protected void reduce(Text key, Iterable<WireFormat> values, Context context) throws IOException, InterruptedException {

        WireFormat combFormat = new WireFormat();

        for(WireFormat val : values){
            combFormat.merge(val);
        }

        context.write(key, combFormat);
    }
}

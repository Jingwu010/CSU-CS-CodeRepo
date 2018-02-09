// Author : Jimx

package cs455.hadoop.dataAnalysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DataAnalysisFirstReducer extends Reducer<Text, WireFormat, Text, WireFormat> {

    @Override
    protected void reduce(Text key, Iterable<WireFormat> values, Context context) throws IOException, InterruptedException {

        WireFormat redFormat = new WireFormat();

        for(WireFormat val : values){
            redFormat.merge(val);
        }

        context.write(key, redFormat);
    }
}

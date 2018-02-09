// Author : Jimx

package cs455.hadoop.dataAnalysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class DataAnalysisFirstMapper extends Mapper<Object, Text, Text, WireFormat> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        WireFormat mapFormat = new WireFormat();
        // Read Text into lines.
        String line = value.toString();

        if(line.substring(8, 10).equals("PR")) return;
        if(line.substring(8, 10).equals("VI")) return;

        mapFormat.readData(line);

        context.write(new Text(line.substring(8, 10)), mapFormat);
    }
}

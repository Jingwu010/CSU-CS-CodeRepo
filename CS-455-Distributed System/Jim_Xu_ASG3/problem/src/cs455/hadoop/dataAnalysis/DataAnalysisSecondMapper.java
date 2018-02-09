// Author : Jimx

package cs455.hadoop.dataAnalysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class DataAnalysisSecondMapper extends Mapper<Object, Text, Text, Text> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        // Read Text into lines.
        String line = value.toString();

        String[] parts = line.split("\\s+");

        StringBuilder sb =  new StringBuilder();

        sb.append(parts[0]);

        for(int i = parts.length-2; i < parts.length; i++){
        	sb.append("\t");
        	sb.append(parts[i]);
        }

        context.write(new Text("Across State"), new Text(sb.toString()));
    }
}

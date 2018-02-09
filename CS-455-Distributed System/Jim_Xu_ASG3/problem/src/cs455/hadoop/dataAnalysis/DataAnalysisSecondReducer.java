// Author : Jimx

package cs455.hadoop.dataAnalysis;

import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DataAnalysisSecondReducer extends Reducer<Text, Text, Text, Text> {

	private final static int roomsNum = 9;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        ArrayList<Double> mylist = new ArrayList<Double>();

    	double maxs = 0.0;
    	StringBuffer state = new StringBuffer();
        for(Text val : values){
        	String line = val.toString();

			String[] parts = line.split("\\s+");

            mylist.add(Double.parseDouble(parts[1]));

			double elderRate = Double.parseDouble(parts[2]);

			if(maxs < elderRate){
				maxs = elderRate;
                state.setLength(0);
                state.append(parts[0]);
			}
        }

        Collections.sort(mylist);

        String avgRoom = Double.toString(mylist.get((int)(mylist.size()*0.95)));
        context.write(new Text("the 95th percentile of the average number of rooms per house is"), new Text(avgRoom));
        context.write(new Text(state.toString()), new Text("has the highest percentage of elderly people"));
    }

    
}

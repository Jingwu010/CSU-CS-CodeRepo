package unigram;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UnigramWritable implements Writable{

    private Text word;
    private IntWritable count;

    //Empty Constructor
    public UnigramWritable() {
        this.word = new Text();
        this.count = new IntWritable();
    }

    //Custom Constructor
    public UnigramWritable(Text word, IntWritable count) {
        this.word = word;
        this.count = count;
    }

    @Override
    //overriding default readFields method. 
    //It de-serializes the byte stream data
    public void readFields(DataInput in) throws IOException {
        // sequence - word - count
        word.readFields(in);
        count.readFields(in);
    }

    @Override
    //It serializes object data into byte stream data
    public void write(DataOutput out) throws IOException {
        // sequence - word - count
        word.write(out);
        count.write(out);
    }

    public Text getWord(){
        return this.word;
    }

    public IntWritable getCount(){
        return this.count;
    }
}

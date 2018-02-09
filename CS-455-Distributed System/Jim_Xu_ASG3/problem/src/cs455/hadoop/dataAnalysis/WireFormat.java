// Author : Jimx

package cs455.hadoop.dataAnalysis;

import java.io.*;
import java.lang.StringBuilder;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

public class WireFormat implements Writable{

    public final static int ownerValueNum = 20;
    public final static int rentValueNum = 16;
    public final static int roomsNum = 9;
    public final static int racesNum = 5;

    private String line;

	private long owned, rent;
	private long population;
	private long maleMarried, femaleMarried;
	private long below18male, below18female;
    private long below29male, below29female;
    private long below39male, below39female;
    private long after39male, after39female;
    private long urban, rural, total;
    private long[] valueOwner, valueRent, rooms;
    private long elder;

    private long[] races;

    public WireFormat(){
    	valueOwner = new long[ownerValueNum];
    	valueRent = new long[rentValueNum];
    	rooms = new long[roomsNum];
    	races = new long[racesNum];
    }

	public WireFormat(byte[] marshalledBytes) throws IOException { 
		valueOwner = new long[ownerValueNum];
    	valueRent = new long[rentValueNum];
    	rooms = new long[roomsNum];
    	races = new long[racesNum];
		readBytes(marshalledBytes);
	}

    public void merge(WireFormat other) {
        this.owned += other.owned;
        this.rent += other.rent;

        this.population += other.population;
        this.maleMarried += other.maleMarried;
        this.femaleMarried += other.femaleMarried;

        this.below18male += other.below18male;
        this.below18female += other.below18female;
        this.below29male += other.below29male;
        this.below29female += other.below29female;
        this.below39male += other.below39male;
        this.below39female += other.below39female;
        this.after39male += other.after39male;
        this.after39female += other.after39female;

        this.urban += other.urban;
        this.rural += other.rural;
        this.total += other.total;

        this.elder += other.elder;

        for(int i = 0; i < ownerValueNum; i++){
            this.valueOwner[i] += other.valueOwner[i];
        }

        for(int i = 0; i < rentValueNum; i++){
            this.valueRent[i] += other.valueRent[i];
        }

        for(int i = 0; i < roomsNum; i++){
            this.rooms[i] += other.rooms[i];
        }

        for(int i = 0; i < racesNum; i++){
            this.races[i] += other.races[i];
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
    	readBytes(WritableUtils.readCompressedByteArray(in));
    }

    @Override
    public void write(DataOutput out) throws IOException {
        WritableUtils.writeCompressedByteArray(out, this.getBytes());
    }

    @Override
    public String toString() {
        //return new String(String.valueOf(owned));
        return write();
    }

	private byte[] getBytes() throws IOException { 
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream(); 
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream)); 
		
		dout.writeLong(owned); dout.writeLong(rent);
		dout.writeLong(population); dout.writeLong(maleMarried); dout.writeLong(femaleMarried);
		dout.writeLong(below18male); dout.writeLong(below18female);
		dout.writeLong(below29male); dout.writeLong(below29female);
		dout.writeLong(below39male); dout.writeLong(below39female);
		dout.writeLong(after39male); dout.writeLong(after39female);
		dout.writeLong(urban); dout.writeLong(rural); dout.writeLong(total);
		dout.writeLong(elder);
		for(int i = 0; i < ownerValueNum; i ++) dout.writeLong(valueOwner[i]);
		for(int i = 0; i < rentValueNum; i ++) dout.writeLong(valueRent[i]);
		for(int i = 0; i < roomsNum; i ++) dout.writeLong(rooms[i]);
		
		for(int i = 0; i < racesNum; i++) dout.writeLong(races[i]);

		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();

		return marshalledBytes;
	} 

	private void readBytes(byte[] marshalledBytes) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes); 
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream)); 

		owned = din.readLong(); rent = din.readLong();		
		population = din.readLong(); maleMarried = din.readLong(); femaleMarried = din.readLong();
		below18male = din.readLong(); below18female = din.readLong();
    	below29male = din.readLong(); below29female = din.readLong();
    	below39male = din.readLong(); below39female = din.readLong();
		after39male = din.readLong(); after39female = din.readLong();
		urban = din.readLong(); rural = din.readLong(); total = din.readLong();
		elder = din.readLong();
		for(int i = 0; i < ownerValueNum; i ++) valueOwner[i] = din.readLong();
		for(int i = 0; i < rentValueNum; i ++) valueRent[i] = din.readLong();
		for(int i = 0; i < roomsNum; i ++) rooms[i] = din.readLong();
			
		for(int i = 0; i < racesNum; i++) races[i] = din.readLong();

		baInputStream.close(); 
		din.close();
	}
	
	public void readData(String line){
		this.line = line;
		read();
	}


	/* <---------------------------- Reads -------------------------> */

	private void read(){
		if(checkValid(1) == true){
            readQues2();

            readQues3();

            readQues8();

            readQues9();
        }

        if(checkValid(2) == true){
            readQues1();

            readQues4();

            readQues5();

            readQues6();

            readQues7();

        }
	}

	private boolean checkValid(int n){
        if(line.charAt(10) == '1') 
            if(line.charAt(11) == '0')
                if(line.charAt(12) == '0')
                    if(line.charAt(27) == (char)(n + '0'))
                        return true;
        return false;
    }

    private void readQues1(){
        owned = Long.parseLong(line.substring(1803, 1812));
        rent = Long.parseLong(line.substring(1812, 1821));
    }

    private void readQues2(){
        population = Long.parseLong(line.substring(300, 309));
        maleMarried = Long.parseLong(line.substring(4422, 4431));
        femaleMarried = Long.parseLong(line.substring(4467, 4476));
    }

    private void readQues3(){
        for(int i = 0; i < 13; i++){
            int index = 3864 + i * 9;
            below18male += Long.parseLong(line.substring(index, index+9));
            index = 4143 + i * 9;
            below18female += Long.parseLong(line.substring(index, index+9));
        }

        for(int i = 0; i < 5; i++){
            int index = 3981 + i * 9;
            below29male += Long.parseLong(line.substring(index, index+9));
            index = 4260 + i * 9;
            below29female += Long.parseLong(line.substring(index, index+9));
        }

        for(int i = 0; i < 2; i++){
            int index = 4026 + i * 9;
            below39male += Long.parseLong(line.substring(index, index+9));
            index = 4305 + i * 9;
            below39female += Long.parseLong(line.substring(index, index+9));
        }

        for(int i = 0; i < 11; i++){
            int index = 4044 + i * 9;
            after39male += Long.parseLong(line.substring(index, index+9));
            index = 4323 + i * 9;
            after39female += Long.parseLong(line.substring(index, index+9));
        }
    }

    private void readQues4(){
        urban = Long.parseLong(line.substring(1821, 1830)) + 
                        Long.parseLong(line.substring(1830, 1839));

        rural = Long.parseLong(line.substring(1839, 1848));

        total = Long.parseLong(line.substring(1848, 1857)) + urban + rural;
    }

    private void readQues5(){
        for(int i = 0; i < ownerValueNum; i++){
            int index = 2928 + i * 9;
            valueOwner[i] = Long.parseLong(line.substring(index, index+9));
        }
    }

    private void readQues6(){
        for(int i = 0; i < rentValueNum; i++){
            int index = 3450 + i * 9;
            valueRent[i] = Long.parseLong(line.substring(index, index+9));
        }
    }  

    private void readQues7(){
    	for(int i = 0; i < roomsNum; i++){
    		int index = 2388 + i * 9;
    		rooms[i] = Long.parseLong(line.substring(index, index+9));
    	}
    }

    private void readQues8(){
    	elder = Long.parseLong(line.substring(1065, 1074));
    }

    private void readQues9(){
    	for(int i = 0; i < racesNum; i++){
    		int index = 381 + 9 * i;
    		races[i] = Long.parseLong(line.substring(index, index+9));
    	}
    }


    /* <--------------------------- Write --------------------------> */

    private static final String[] houseIntervals = {
		"Less than $15,000",
		"$15,000 - $19,999",
		"$20,000 - $24,999",
		"$25,000 - $29,999",
		"$30,000 - $34,999",
		"$35,000 - $39,999",
		"$40,000 - $44,999",
		"$45,000 - $49,999",
		"$50,000 - $59,999",
		"$60,000 - $74,999",
		"$75,000 - $99,999",
		"$100,000 - $124,999",
		"$125,000 - $149,999",
		"$150,000 - $174,999",
		"$175,000 - $199,999",
		"$200,000 - $249,999",
		"$250,000 - $299,999",
		"$300,000 - $399,999",
		"$400,000 - $499,999",
		"$500,000 or more"};

	private static final String[] rentIntervals = {
		"Less than $100",
		"$100 to $149",
		"$150 to $199",
		"$200 to $249",
		"$250 to $299",
		"$300 to $349",
		"$350 to $399",
		"$400 to $449",
		"$450 to $499",
		"$500 to $549",
		"$550 to $599",
		"$600 to $649",
		"$650 to $699",
		"$700 to $749",
		"$750 to $799",
		"$1000 or more"};

	private static final String[] racesName = {
		" White",
		" Black",
		" American Indian",
		" Asian",
		" Other"};

	private String write(){

		StringBuilder output = new StringBuilder();

		writeQues1(output);

		writeQues2(output);

		writeQues3(output);

		writeQues4(output);

		writeQues5(output);

		writeQues6(output);

		writeQues9(output);
		/* <------------- not used in first mapreduce ------------> */

		writeQues7(output);  // 1

		writeQues8(output);  // 1

		return output.toString();
	}

	private void writeQues1(StringBuilder output){
		long sum = rent + owned;
		output.append(divideAs(rent, sum));
		output.append("\t");
		output.append(divideAs(owned, sum));
		output.append("\t");
	}

	private void writeQues2(StringBuilder output){
		output.append(divideAs(maleMarried, population));
		output.append("\t");
		output.append(divideAs(femaleMarried, population));
		output.append("\t");
	}

	private void writeQues3(StringBuilder output){
		long sum = below18male + below18female +
					below29male + below29female +
					below39male + below39female +
					after39male + after39female;
		output.append(divideAs(below18male, sum));
		output.append("\t");
		output.append(divideAs(below18female, sum));
		output.append("\t");
		output.append(divideAs(below29male, sum));
		output.append("\t");
		output.append(divideAs(below29female, sum));
		output.append("\t");
		output.append(divideAs(below39male, sum));
		output.append("\t");
		output.append(divideAs(below39female, sum));
		output.append("\t");
	}

	private void writeQues4(StringBuilder output){
		output.append(divideAs(rural, total));
		output.append("\t");
		output.append(divideAs(urban, total));
		output.append("\t");
	}

	private void writeQues5(StringBuilder output){
		long sum = 0;
		for(int i = 0; i < ownerValueNum; i++)
			sum += valueOwner[i];
		sum /= 2;
		int index = -1;
		for(int i = 0; i < ownerValueNum; i++){
			if(sum < valueOwner[i]){
				index = i;
				break;
			}
			else sum -= valueOwner[i];
		}
		output.append(houseIntervals[index]);
		output.append("\t");
	}

	private void writeQues6(StringBuilder output){
		long sum = 0;
		for(int i = 0; i < rentValueNum; i++)
			sum += valueRent[i];
		sum /= 2;
		int index = -1;
		for(int i = 0; i < rentValueNum; i++){
			if(sum < valueRent[i]){
				index = i;
				break;
			}
			else sum -= valueRent[i];
		}
		output.append(rentIntervals[index]);
		output.append("\t");
	}

	private void writeQues7(StringBuilder output){
		long sumRooms = 0;
		long sumRoomsValue = 0;
		for(int i = 0; i < roomsNum; i++){
        	sumRooms += rooms[i];
        	sumRoomsValue += rooms[i] * (i+1);
        }
		output.append(divideAs(sumRoomsValue, sumRooms));
		output.append("\t");
	}

	private void writeQues8(StringBuilder output){
		output.append(divideAs(elder, population));
		output.append("\t");
	}

	private void writeQues9(StringBuilder output){
		long maxs = 0;
		int index = -1;
		for(int i = 1; i < racesNum; i++){
			if(maxs < races[i]){
				maxs = races[i];
				index = i;
			}
		}
		output.append(racesName[index]);
		output.append("\t");
	}

	private String divideAs(long l1, long l2){
		double ans = ((double)l1 / (double)l2);
		return String.valueOf(ans);
	}
} 

//Jim Xu	

public class Buffer {
	private int BUFFER_SIZE;
	private int in;
	private int out;
	private int[] buffer;
	private int buffercount;
	public Buffer(){
		this.BUFFER_SIZE = 10;
		buffer = new int[BUFFER_SIZE];
		in = out = 0;
		buffercount = 0;
	}
	public Buffer(int BUFFER_SIZE){
		this.BUFFER_SIZE = BUFFER_SIZE;
		buffer = new int[BUFFER_SIZE];
		in = out = 0;
		buffercount = 0;
	}
	public int makeNewItem(int item){
		int loc = in;
		buffer[in++] = item;
		in %= BUFFER_SIZE;
		buffercount ++;
		return loc;
	}
	public int consumeItem(){
		int item = buffer[out++];
		out %= BUFFER_SIZE;
		buffercount --;
		return item;
	}
	public int getIn(){
		return in;
	}
	public int getOut(){
		return out;
	}
	public int getBuffercount(){
		return buffercount;
	}
	public int getBuffersize(){
		return BUFFER_SIZE;
	}
} 
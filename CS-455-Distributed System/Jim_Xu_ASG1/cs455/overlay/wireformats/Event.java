package cs455overlay.wireformats;

//Jim Xu

import java.io.IOException;

public interface Event {
	public int getType();
	public byte[] getBytes() throws IOException;
}

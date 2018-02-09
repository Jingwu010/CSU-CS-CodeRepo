package cs455overlay.node;

// Jim Xu
// Class Name: 	Node
// Class Intro:	Node is a interface

public interface Node {
	public void onEvent(byte[] data);

	public void onEvent(Link link);
}

package edu.csu2017sp314.DTR14.tripco.View;

import org.junit.Test;
import static org.junit.Assert.*;

public class SelectionWriterTest {
	
	@Test
	public void testConstructor(){
		SelectionWriter sw1 = new SelectionWriter(new String[]{"1","2","3","4"}, "test.xml", "test.svg");
		assertFalse(sw1.header.isEmpty());
		assertFalse(sw1.footer.isEmpty());
		assertFalse(sw1.body.isEmpty());
		
		SelectionWriter sw2 = new SelectionWriter(new String[0], "test.xml", "test.svg");
		assertFalse(sw2.header.isEmpty());
		assertFalse(sw2.footer.isEmpty());
		assertFalse(sw2.body.isEmpty());
		for(String string: sw2.body){
			assertTrue(!string.contains("destinations"));
			assertTrue(!string.contains("id"));
			assertTrue(!string.contains("1"));
			assertTrue(!string.contains("2"));
			assertTrue(!string.contains("3"));
			assertTrue(!string.contains("4"));
		}
	}

}
	
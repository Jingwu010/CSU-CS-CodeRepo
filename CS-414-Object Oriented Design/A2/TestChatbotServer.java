package a2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test ChatbotServer handleOneClient method
 * 
 * @author Jim Xu
 */

@RunWith(MockitoJUnitRunner.class)
public class TestChatbotServer {
	
	@Mock
    private Chatbot mockChatbot;
	
	@Mock
	private ServerSocket mockServerSocket;
	
	@Mock
	private Socket mockSocket;
	
	private ChatbotServer chatbotServer;
	
	private InputStream[] myInputStreams;
	private OutputStream myOutputStream;
	
	
	@Before
	public void init(){
		chatbotServer = new ChatbotServer(mockChatbot, mockServerSocket);
	}
	
	@Before
	public void initChatbot(){	
		String[] aiInputs = {"Hello", "How old are you", "What is your major", "baba"};
		String[] aiOutputs = {"My name is AI", "I am 18", "I study computer science", "Sorry, I do not understand"};
		// use 
		for (int i = 0; i < aiInputs.length; i++){
			try {
				when(mockChatbot.getResponse(aiInputs[i])).thenReturn(aiOutputs[i]);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Unexpected Exception");
			}
		}
		
		// add AIException
		try {
			when(mockChatbot.getResponse("-11")).thenThrow(new AIException("Invalid input"));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
	}
	
	private void initMockServerSocket(String[] inputs){
		try {
			when(mockServerSocket.accept()).thenReturn(mockSocket);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
		myInputStreams = new InputStream[inputs.length];
		for (int i = 0; i < inputs.length; i++){
			myInputStreams[i] = new ByteArrayInputStream(inputs[i].getBytes());
		}
	}
	
	@Test
	public void testOutput(){
		// Test case 1: none input
		try {
			String[] inputs = {};
			String[] outputs = {};
			initMockServerSocket(inputs);
			for (int i = 0; i < inputs.length; i++){
				when(mockSocket.getInputStream()).thenReturn(myInputStreams[i]);
				myOutputStream = new ByteArrayOutputStream();
				when(mockSocket.getOutputStream()).thenReturn(myOutputStream);
				
				chatbotServer.handleOneClient();

				assertEquals(outputs[i]+"\n", myOutputStream.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
				
		// Test case 2: multiple agents with one line
		try {
			String[] inputs = {"Hello", "How old are you", "What is your major", "baba"};
			String[] outputs = {"My name is AI", "I am 18", "I study computer science", "Sorry, I do not understand"};
			initMockServerSocket(inputs);
			for (int i = 0; i < inputs.length; i++){
				when(mockSocket.getInputStream()).thenReturn(myInputStreams[i]);
				myOutputStream = new ByteArrayOutputStream();
				when(mockSocket.getOutputStream()).thenReturn(myOutputStream);
				
				chatbotServer.handleOneClient();

				assertEquals(outputs[i]+"\n", myOutputStream.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
		
		// Test case 3: single agent with multiple lines
		try {
			String[] inputs = {"Hello\nHow old are you\nWhat is your major\nbaba\n-1\n"};
			String[] outputs = {"My name is AI\nI am 18\nI study computer science\nSorry, I do not understand"};
			initMockServerSocket(inputs);
			when(mockSocket.getInputStream()).thenReturn(myInputStreams[0]);
			myOutputStream = new ByteArrayOutputStream();
			when(mockSocket.getOutputStream()).thenReturn(myOutputStream);
			
			chatbotServer.handleOneClient();
			
			assertEquals(outputs[0]+"\n", myOutputStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
		
		// Test case 4: multiple agents with multiple lines
		try {
			String[] inputs = {"Hello", "How old are you", "What is your major", "baba",
					"Hello\nHow old are you\nWhat is your major\nbaba\n-1\n"};
			String[] outputs = {"My name is AI", "I am 18", "I study computer science", "Sorry, I do not understand",
					"My name is AI\nI am 18\nI study computer science\nSorry, I do not understand"};
			initMockServerSocket(inputs);
			for (int i = 0; i < inputs.length; i++){
				when(mockSocket.getInputStream()).thenReturn(myInputStreams[i]);
				myOutputStream = new ByteArrayOutputStream();
				when(mockSocket.getOutputStream()).thenReturn(myOutputStream);
				
				chatbotServer.handleOneClient();
				
				assertEquals(outputs[i]+"\n", myOutputStream.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
	}
	
	@Test
	public void testAIException(){
		// Test case5: single agent with single AI Exception error
		try {
			String[] inputs = {"-11"};
			String[] outputs = {"Got AIException: Invalid input"};
			initMockServerSocket(inputs);
			for (int i = 0; i < inputs.length; i++){
				when(mockSocket.getInputStream()).thenReturn(myInputStreams[i]);
				myOutputStream = new ByteArrayOutputStream();
				when(mockSocket.getOutputStream()).thenReturn(myOutputStream);
				
				chatbotServer.handleOneClient();
				
				assertEquals(outputs[i]+"\n", myOutputStream.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
		
		// Test case 6: multiple agents with multiple inputs with AI Exception
		try {
			String[] inputs = {"How old are you", "baba", "-11", "baba\n-11"};
			String[] outputs = {"I am 18", "Sorry, I do not understand", "Got AIException: Invalid input",
					"Sorry, I do not understand\nGot AIException: Invalid input"};
			initMockServerSocket(inputs);
			for (int i = 0; i < inputs.length; i++){
				when(mockSocket.getInputStream()).thenReturn(myInputStreams[i]);
				myOutputStream = new ByteArrayOutputStream();
				when(mockSocket.getOutputStream()).thenReturn(myOutputStream);
				
				chatbotServer.handleOneClient();
				
				assertEquals(outputs[i]+"\n", myOutputStream.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
	}

}

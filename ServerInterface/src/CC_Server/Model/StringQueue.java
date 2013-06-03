package CC_Server.Model;

import java.util.LinkedList;
import java.util.Queue;

public class StringQueue {
	private Queue<String> msgQueue = new LinkedList<String>();
	
	public Queue<String> getQueue() {
		return msgQueue;
	}
	
	public synchronized void enqueueString(String input) {
		msgQueue.add(input);
	}
	
	public synchronized String dequeueString() {
		return (String) msgQueue.remove();
	}
	
	public synchronized boolean checkQueue() { 
		return msgQueue.isEmpty();
	}
	
}

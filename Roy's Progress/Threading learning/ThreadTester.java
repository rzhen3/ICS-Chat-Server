package learningThreads;

public class ThreadTester {

	public static void main(String[] args) {
		//One approach using individual threads to run the program
		
		
		// creating threads. setting identifiers
		//threads can be handed any object that implements the runnable interface
		Thread thread1 = new Thread(new MyThreadedClass("A"));
		Thread thread2 = new Thread(new MyThreadedClass("B"));
		Thread thread3 = new Thread(new MyThreadedClass("C"));
		Thread thread4 = new Thread(new MyThreadedClass("D"));
		
		//starting threads -> runs the object's run method
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}

}

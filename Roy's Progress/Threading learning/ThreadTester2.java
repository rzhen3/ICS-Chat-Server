package learningThreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTester2 {
	private static final int MAX_T = 4;
	public static void main(String[] args) {
		// One manner of doing things(if there are no specifics): *so instead of instantiating a 'Runnable' object
		//use Interface Initializer to override the implemented methods
		//this avoids the trouble of creating a new file/new class and doing everything 
		
		
//		Thread myThread = new Thread(new Runnable() {
//		//new Thread(new Runnable()){
//			public void run() {
//				for(int i = 0;i<100;i++) {
//					System.out.println("Thread is on loop "+i);
//				}
//			}
//			
//		});
//		myThread.start();
		//----------------------------------------------------------------------------
		//A variable can be used to store the interface initializer like so:
//		Runnable aRunnableObj = new Runnable() {
//			public void run() {
//				for(int i = 0;i<100;i++) {
//					System.out.println("Thread is on loop "+i);
//				}
//			}
//		};
//		Thread aThread = new Thread(aRunnableObj);
//		aThread.start();
		//----------------------------------------------------------------------------
		//In fact, the thread variable isn't particularly useful. Thus it makes sense to get rid of it all together with this implementation:
//		new Thread(new Runnable() {
//			public void run() {
//				for(int i = 0;i<100;i++)
//					System.out.println("Thread is on loop "+i);
//			}
//		}).start();
		
		//---------------------------------------------------------------------
		//Using ExecutorService. A thread pool that creates however many threads as specified. manages threads to allow the reuse of objects
		//Thread pool can then execute the runnable objects by itself (in the same way by calling the run method)
		//Avoids wasting time creating all those thread objects
		Runnable thread1 = new MyThreadedClass("A");
		Runnable thread2 = new MyThreadedClass("B");
		Runnable thread3 = new MyThreadedClass("C");
		Runnable thread4 = new MyThreadedClass("D");
		
		ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
		pool.execute(thread1);
		pool.execute(thread2);
		pool.execute(thread3);
		pool.execute(thread4);
		pool.shutdown();
		
	}

}

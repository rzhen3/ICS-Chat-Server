package Base;

public class Intro {
//concurrency refers to an application doing more than one thing at a time
		
// A process is a unit of execution that has its own memory space.
//Each instance of a JVM runs as a process (most of them).
//RUnning a Java console application starts a process. Starting a JavaFX Application starts a process

//Each java application/process has its own memory space called heap.
//Each process has their own heap

//A thread is a unit of execution within a process
//All programs have one explicit main thread but more can be created
//Behind the scenes threads handl things like manage memory and I/O

//Each thread shares the process's files and memory
//Each thread stack has a thread stack. which is the memory only a thraed can access. Diff between heap and thread stack

//Each process has a heap and every thread has a thread stack

//Threads are useful for performing tasks that take a long time
//Some APIs require a thread to be provided. In this case, sometimes providing the code is all tahts needed

//outputs can vary depending on JVM and operating system so not all programs will run the same wayu
	public static void main(String[] args) {
		
		
		
		System.out.println("Main thread");
		//To create a thread, create an instance of a thread class
		//The thread will operate on whatever object is attached.
		//That object must extend from thrad superclass
		//cannot start a thread instance more than once
		
		Thread aThread = new anotherThread();
		aThread.setName("== Another Thread==");
		aThread.start();
		
		
		//Priority can be used to influence thread run preference. THough alll is up to luck
		
		
		
		
		//anonymous class
		new Thread() {
			public void run() {
				System.out.println("Anonymous thread");
			}
		}.start();
		
		
		
		
		//Runnable object
		Thread myRunnableThread = new Thread(new MyRunnable());
		myRunnableThread.start();
		
		
		
		
		//Anonymous runnable 
		Thread anonymousRunnable = new Thread(new MyRunnable() {
			@Override
			public void run() {
				System.out.println("Anonymous runnable");
				try {
					aThread.join(2000);
					//when join is overloaded, the amount of millis passed in will elapse. if current thread still waiting, will auto run 
					System.out.println("anotherthread ended. now running again");
				} catch(InterruptedException e) {
					//checking for premature termination. otherwise, wait for joined thread to finish execution
					System.out.println("Can't wait. Interrupted");
				}
			}
		});
		anonymousRunnable.start();
		
		//IF the run method is called instead of using the 'start' method, the run method will be executed on the thread that called it and not on the new thread
		
		
		//calling a reference to another thread from one thread will interrupt that thread from this thread (if that makes sense future roy)
		//aThread.interrupt();//in this case, 3 seconds will not have passed
		System.out.println("Main thread 2");
		
	}

}

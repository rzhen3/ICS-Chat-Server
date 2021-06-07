package learningThreads;

public class MyThreadedClass implements Runnable{
	private static final int MAX = 100000;
	private String id;
	
	public MyThreadedClass(String id) {
		this.id = id;
	}
	public void run() {
		for(int i = 0;i<MAX;i++) {
			System.out.println("Thread "+ id+ " is on loop #"+i);
		}
	}

}

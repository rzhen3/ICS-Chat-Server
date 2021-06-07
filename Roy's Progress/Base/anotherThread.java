package Base;

public class anotherThread extends Thread{
	@Override
	//Override the run method from thread superclass
	public void run() {
		System.out.println(currentThread().getName());
		//difference vs this.getName()    ?
		try {
			//pauses for 3 secs
			Thread.sleep(3000);
		}catch(InterruptedException e) {
			
			//can be woken up prematurely in which case this part executes
			System.out.println("another thread woke up");
			return;
		}
		System.out.println("3 seconds passed and another thread awoke");
	}
}

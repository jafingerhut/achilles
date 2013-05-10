package achilles.trials;


public class IncPrimitiveLong implements Runnable {
    long jobSizePerThread;
    
    public IncPrimitiveLong(long jobSizePerThread) {
	this.jobSizePerThread = jobSizePerThread;
    }
    
    public void run() {
	long result = 0;
	long n = jobSizePerThread;
	for (long i = 0L; i < n; i++) {
	    result = result + 1;
	}
	System.out.println(result);
    }
}

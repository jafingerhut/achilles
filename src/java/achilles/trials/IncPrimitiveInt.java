package achilles.trials;


public class IncPrimitiveInt implements Runnable {
    long jobSizePerThread;
    
    public IncPrimitiveInt(long jobSizePerThread) {
	this.jobSizePerThread = jobSizePerThread;
    }
    
    public void run() {
	int result = 0;
	long n = jobSizePerThread;
	for (long i = 0L; i < n; i++) {
	    result = result + 1;
	}
	System.out.println(result);
    }
}

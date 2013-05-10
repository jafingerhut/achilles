package achilles.trials;


public class IncPrimitiveDouble implements Runnable {
    long jobSizePerThread;
    
    public IncPrimitiveDouble(long jobSizePerThread) {
	this.jobSizePerThread = jobSizePerThread;
    }
    
    public void run() {
	double result = 0.0;
	long n = jobSizePerThread;
	for (long i = 0L; i < n; i++) {
	    result = result + 1.0;
	}
	System.out.print(" " + result);
    }
}

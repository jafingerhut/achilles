package achilles.trials;


public class IncNewBoxedDouble implements Runnable {
    long jobSizePerThread;
    
    public IncNewBoxedDouble(long jobSizePerThread) {
	this.jobSizePerThread = jobSizePerThread;
    }
    
    public void run() {
	Double result = new Double(0.0);
	long n = jobSizePerThread;
	for (long i = 0L; i < n; i++) {
	    result = new Double(result.doubleValue() + 1.0);
	}
	System.out.println(result);
    }
}

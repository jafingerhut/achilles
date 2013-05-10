package achilles.trials;


public class IncNewBoxedDouble_PrngUpdate4PrimitiveInt implements Runnable {
    long jobSizePerThread;
    
    public IncNewBoxedDouble_PrngUpdate4PrimitiveInt(long jobSizePerThread) {
	this.jobSizePerThread = jobSizePerThread;
    }
    
    public void run() {
	Double result = new Double(0.0);
	int prng_state = 42;
	long n = jobSizePerThread;
	for (long i = 0L; i < n; i++) {
	    result = new Double(result.doubleValue() + 1.0);
	    prng_state = (3877 * prng_state + 29573) % 139968;
	    prng_state = (3877 * prng_state + 29573) % 139968;
	    prng_state = (3877 * prng_state + 29573) % 139968;
	    prng_state = (3877 * prng_state + 29573) % 139968;
	}
	System.out.println("result=" + result + "  prng_state=" + prng_state);
    }
}

package achilles;

import java.text.DecimalFormat;
import achilles.trials.*;
import java.lang.reflect.*;


public class ParallelTest {

    static int CacheBuster1_data[];
    
    public static class NewDoubleTestB implements Runnable {
	long jobSizePerThread;
	
	public NewDoubleTestB(long jobSizePerThread) {
	    this.jobSizePerThread = jobSizePerThread;
	}
	
	public void run() {
	    Double result = new Double(0.0);
	    long n = jobSizePerThread;
	    for (long i = 0L; i < n; i++) {
		double old = result.doubleValue() + 1.0;
		result = new Double(old);
	    }
	    System.out.println(result);
	}
    }
    
    public static class NewDoubleTestC implements Runnable {
	long jobSizePerThread;
	
	public NewDoubleTestC(long jobSizePerThread) {
	    this.jobSizePerThread = jobSizePerThread;
	}
	
	public void run() {
	    Double result = new Double(0.0);
	    long n = jobSizePerThread;
	    for (long i = 0L; i < n; i++) {
		result = new Double((double) i);
	    }
	    System.out.println(result);
	}
    }

    public static void handleExc(Exception e) {
	System.out.println("Caught exception " + e);
	System.exit(1);
    }

    public static void doTest(String taskName, Constructor taskConstructor,
			      int numThreads, long jobSizePerThread) {
	final int NWARMUPS = 2;
	final int NTRIALS = 10;
	long[] runTime = new long[NWARMUPS + NTRIALS];

	System.out.println("Task Specifier: " + taskName);
	System.out.println("Number of Threads: " + numThreads);
	System.out.println("Job size per thread: " + jobSizePerThread);
	for (int trial = 0; trial < (NWARMUPS + NTRIALS); trial++) {
	    Thread[] tarray = new Thread[numThreads];
	    try {
		for (int i = 0; i < numThreads; i++) {
		    tarray[i] = new Thread((Runnable) taskConstructor.newInstance(jobSizePerThread));
		}
	    }
	    catch (InstantiationException e) { handleExc(e); }
	    catch (IllegalAccessException e) { handleExc(e); }
	    catch (InvocationTargetException e) { handleExc(e); }
	    
	    long startTime = System.nanoTime();
	    for (int i = 0; i < numThreads; i++)
		tarray[i].start();
	    try {
		for (int i = 0; i < numThreads; i++)
		    tarray[i].join();
	    }
	    catch (InterruptedException e) { handleExc(e); }
	    runTime[trial] = System.nanoTime() - startTime;
	}
	long minTime = Long.MAX_VALUE;
	long maxTime = 0;
	long totalTime = 0;
	long n = 0;
	System.out.println("\nElapsed times in msec of all trials (warmup not included in final stats below):");
	for (int trial = 0; trial < (NWARMUPS + NTRIALS); trial++) {
	    System.out.format("Trial %2d: %.3f%s\n",
			      trial,
			      ((double) runTime[trial]) / 1000000.0,
			      (trial < NWARMUPS) ? " (warmup)" : "");
	    if (trial >= NWARMUPS) {
		minTime = Math.min(minTime, runTime[trial]);
		maxTime = Math.max(maxTime, runTime[trial]);
		totalTime += runTime[trial];
		++n;
	    }
	}

	System.out.println();
	System.out.println(taskName + " ALL THREADS FINISHED.");
	System.out.print(taskName);
	System.out.format(" ELAPSED Times (msec): min=%.3f  max=%.3f  avg=%.3f\n",
			  ((double) minTime) / 1000000.0,
			  ((double) maxTime) / 1000000.0,
			  (((double) totalTime) / n) / 1000000.0);
    }
}

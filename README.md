# achilles

A little bit of Java and Clojure code for testing performance with
multiple threads on the JVM.  The kinds of computations run in
parallel are mostly very simple microbenchmarks, intended to help
understand what is going on without getting lost in complex benchmark
code.

The name "Achilles" occurred to me for two possible reasons: something
from Greek mythology that had to do with speed -- in particular Zeno's
paradox regarding Achilles and the tortoise.

Depending upon the results found, I am a bit worried I will find
simple workloads where achieving parallel speedup near the number of
physical CPU cores used, in a single JVM process is difficult, perhaps
being an "Achilles' heel" for JVM performance.  This could be so even
for what appear to be embarrassingly parallel computations problems.
If so, my current guess is that such low speedups may be due to the
garbage collection implementations commonly available in JVMs.


## Usage

Run `lein run help` for some usage instructions.  One example of a run
and its output:

    % lein run IncPrimitiveDouble 10000000 1
    Task Specifier: IncPrimitiveDouble
    Number of Threads: 1
    Job size per thread: 10000000
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    1.0E7
    Elapsed times in msec of all trials (warmup not included in final stats below):
    Trial  0: 216.940 (warmup)
    Trial  1: 203.953 (warmup)
    Trial  2: 203.087
    Trial  3: 203.426
    Trial  4: 202.640
    Trial  5: 201.443
    Trial  6: 201.990
    Trial  7: 199.705
    Trial  8: 200.828
    Trial  9: 199.493
    Trial 10: 201.313
    Trial 11: 205.747
    
    IncPrimitiveDouble ALL THREADS FINISHED.
    IncPrimitiveDouble ELAPSED Times (msec): min=199.493  max=205.747  avg=201.967

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.

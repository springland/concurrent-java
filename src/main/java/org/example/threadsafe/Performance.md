

AtomicLong  6ms
CAS 8 ms without ,22 ms without output
CASWithVolatile
Lock 15ms
Synchronized 22ms



LongAdder has the best performance when there are more threads

1000 threads
LongAdder took 69ms
AtomicLong took 93ms

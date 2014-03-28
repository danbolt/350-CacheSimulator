350-CacheSimulator
==================

The second project for CSC 350 at the University of Victoria.

## Introduction
350-CacheSimulator is a cache simulation project by Ji Hwan Park, Daniel Savage, and Ian Sutton for CSC 350 at the University of Victoria. It is written in Java.

## Building the Simulator
As the simulator consists of a variety of Java classes, we've created a quick build script in bash that will compile them all. You can call this script by running:
```bash
$ ./build.sh
```

## Running the Simulator
Running the simulator is a fairly straightforward task. Simulation results are outputted to three [comma seperated value](http://en.wikipedia.org/wiki/Comma-separated_values)(CSV) files. Each file shows simulation results for various combinations of a specific replacement policy. You can run this simulation once by running:
```bash
$ java SimulatorTester
```
If you run the program twice, it will not overwrite the old CSV files with new information, but rather append them to the end. This allows a user to run the simulation as many times as he/she desires. Through running multiple simulations, we hope to acheive a [monte carlo](http://en.wikipedia.org/wiki/Monte_Carlo_method) style of results. We've included a bash script that will run SimulatorTester 50 times in succession, creating a sizable amount of data to statistically toy with. To see this, simply run:
```bash
$ ./run50Times.sh
```

Feel free to give us a shout if you have any questions. Thanks!

#!/bin/bash

# Runs the SimulatorTester class 50 times, getting a wide range of results to ananalyze.

date -u
echo "starting..."
for i in {1..50}
do
    echo "Start iteration $i"
    java SimulatorTester
    echo "Done iteration $i"
done
echo "Finished!"
date -u


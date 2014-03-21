// Copyright 2013 James Ji Hwan Park, Daniel Savage, and Ian Sutton

import java.util.*;
import java.lang.Math.*;
import java.io.*;
import java.lang.ProcessBuilder;

public class SimulatorTester
{
	private enum ReplacementPolicy
	{
		LRU,
		FIFO,
		RAND
	}

	private ReplacementPolicy intToPolicy(int policy)
	{
		switch (policy)
		{
			case 0:
				return ReplacementPolicy.LRU;
			case 1:
				return ReplacementPolicy.FIFO;
			case 2:
				return ReplacementPolicy.RAND;
			default:
				return ReplacementPolicy.LRU;
		}
	}

	private int policyToInt(ReplacementPolicy policy)
	{
		switch (policy)
		{
			case LRU:
				return 0;
			case FIFO:
				return 1;
			case RAND:
				return 2;
			default:
				return 0;
		}
	}

	private class SimulationResult
	{
		public ReplacementPolicy policy = ReplacementPolicy.LRU;
		public int hits = 0;
		public int compulsoryMisses = 0;
		public int capacityMisses = 0;
		public int conflictMisses = 0;
		public int totalAccesses = 0;
		public int associativity = 0;
		public int cacheSize = 0;

		public SimulationResult(ReplacementPolicy policy, int hits, int compulsoryMisses, int capacityMisses, int conflictMisses, int totalAccesses, int associativity, int cacheSize)
		{
			this.policy = policy;
			this.hits = hits;
			this.compulsoryMisses = compulsoryMisses;
			this.capacityMisses = capacityMisses;
			this.conflictMisses = conflictMisses;
			this.totalAccesses = totalAccesses;
			this.associativity = associativity;
			this.cacheSize = cacheSize;
		}

		public String toString()
		{
			String s = new String("Simulation:\nPolicy: " + policy + "\nCompulsory Misses: " + compulsoryMisses + "\nCapacity Misses: " + capacityMisses + "\nConflictMisses: " + conflictMisses + "\n");

			return s;
		}
	}

	/**
	 * Runs a cache simulation process.
	 *
	 * @param  policy Cache's replacement policy. Use 0 for LRU, 1 for FIFO, and 2 for RAND.
	 * @param  associativity How associative you want your cache to be.
	 * @param  size Number of slots the cache has.
	 * @param  trials Quantity of random accesses per trial.
	 * @return Result of simulation in a containter class.
	 */
	private SimulationResult simulate(int policy, int associativity, int size, int trials)
	{
		Process process = null;
		String outputLine = null;

		try
		{
			process = new ProcessBuilder("java", "CacheSim", Integer.toString(policy), Integer.toString(associativity), Integer.toString(size), Integer.toString(trials)).start();
		}
		catch (Exception ioEx)
		{
			ioEx.printStackTrace();
		}

		try
		{
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			while ((line = br.readLine()) != null)
			{
				outputLine = line;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		String[] items = outputLine.split(" ");

		return new SimulationResult(intToPolicy(policy), Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2]), Integer.parseInt(items[3]), Integer.parseInt(items[4]), associativity, size);
	}

	private void runSimulations(ReplacementPolicy policy, int numberOfTests, HashSet<SimulationResult> resultsHash)
	{
		if (resultsHash == null)
		{
			return;
		}

		for (int sizePower = 2; sizePower <= 8; sizePower++)
		{
			for (int associativity = 2; associativity <= sizePower; associativity++)
			{
				resultsHash.add(simulate(policyToInt(policy), (int)Math.pow(2, associativity), (int)Math.pow(2, sizePower), numberOfTests));
			}
		}
	}

	public static void main(String[] args)
	{
		SimulatorTester tester = new SimulatorTester();

		HashSet<SimulationResult> LRUResults = new HashSet<SimulationResult>();
		tester.runSimulations(ReplacementPolicy.LRU, 100, LRUResults);
		tester.runSimulations(ReplacementPolicy.LRU, 1000, LRUResults);
		tester.runSimulations(ReplacementPolicy.LRU, 10000, LRUResults);
		tester.runSimulations(ReplacementPolicy.LRU, 100000, LRUResults);

		HashSet<SimulationResult> FIFOResults = new HashSet<SimulationResult>();
		tester.runSimulations(ReplacementPolicy.FIFO, 100, FIFOResults);
		tester.runSimulations(ReplacementPolicy.FIFO, 1000, FIFOResults);
		tester.runSimulations(ReplacementPolicy.FIFO, 10000, FIFOResults);
		tester.runSimulations(ReplacementPolicy.FIFO, 100000, FIFOResults);

		HashSet<SimulationResult> RANDResults = new HashSet<SimulationResult>();
		tester.runSimulations(ReplacementPolicy.RAND, 100, RANDResults);
		tester.runSimulations(ReplacementPolicy.RAND, 1000, RANDResults);
		tester.runSimulations(ReplacementPolicy.RAND, 10000, RANDResults);
		tester.runSimulations(ReplacementPolicy.RAND, 100000, RANDResults);
	}
}
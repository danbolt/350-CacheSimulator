// Copyright 2013 James Ji Hwan Park, Daniel Savage, and Ian Sutton

// All methods will be within the static context. This is to encourage functional purity.

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

	private class SimulationResult
	{
		public ReplacementPolicy policy = ReplacementPolicy.LRU;
		public int hits = 0;
		public int compulsoryMisses = 0;
		public int capacityMisses = 0;
		public int conflictMisses = 0;
		public int totalAccesses = 0;

		public SimulationResult(ReplacementPolicy policy, int hits, int compulsoryMisses, int capacityMisses, int conflictMisses, int totalAccesses)
		{
			this.policy = policy;
			this.compulsoryMisses = compulsoryMisses;
			this.capacityMisses = capacityMisses;
			this.conflictMisses = conflictMisses;
			this.totalAccesses = totalAccesses;
		}

		public String toString()
		{
			String s = new String("Simulation:\nPolicy: " + policy + "\nCompulsory Misses: " + compulsoryMisses + "\nCapacity Misses: " + capacityMisses + "\nConflictMisses: " + conflictMisses);

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

		return new SimulationResult((policy == 0) ? ReplacementPolicy.LRU : ((policy == 1)? ReplacementPolicy.FIFO : ReplacementPolicy.RAND), Integer.parseInt(items[0]), Integer.parseInt(items[1]), Integer.parseInt(items[2]), Integer.parseInt(items[3]), Integer.parseInt(items[4]));
	}

	public static void main(String[] args)
	{
		SimulatorTester tester = new SimulatorTester();

		int numberOfTests = 10000;

		HashSet<SimulationResult> LRUResults = new HashSet<SimulationResult>();

		//SimulationResult result = tester.simulate(0, 4, 1024, 2048);
		
		for (int sizePower = 2; sizePower < 15; sizePower++)
		{
			LRUResults.add(tester.simulate(0, 4, (int)Math.pow(2, sizePower), numberOfTests));
		}
	}
}
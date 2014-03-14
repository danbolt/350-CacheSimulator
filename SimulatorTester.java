// Copyright 2013 James Ji Hwan Park, Daniel Savage, and Ian Sutton

import java.util.*;
import java.io.*;
import java.lang.ProcessBuilder;

public class SimulatorTester
{
	public static void main(String[] args)
	{
		System.out.println("Simulator Testing");

		Process process = null;

		try
		{
			process = new ProcessBuilder("java", "TestProgram", "arg1", "arg2").start();
		}
		catch (IOException ioEx)
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
				System.out.println(line);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
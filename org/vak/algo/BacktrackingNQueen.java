package org.vak.algo;

import java.util.Date;

public class BacktrackingNQueen
{

	private static int[][] queenPosition;
	static private int noOfQueen = 13;

	public static void main(String a[])
	{
		Date start = new Date();

		boolean isPOsible = false;
		int count = 0;

		// for (int i = 0; i < noOfQueen; i++)
		{
			setQueen(noOfQueen);
			isPOsible = chkAllPosibleConfig(queenPosition, 0, 0, 0);
			if (isPOsible)
				count++;
		}
		Date end = new Date();
		System.out.println("Time Taken for locate" + noOfQueen + "Queens  ::" + (end.getTime() - start.getTime()) + "ms");
		// System.out.println("posible ways :: " + count);

	}

	public static boolean setQueen(int size)
	{
		queenPosition = new int[size][2];
		return true;// locateQueen(0, 0, 0);

	}

	static private boolean isValidMove(int[][] data, int queen, int i, int j)
	{
		// System.out.println(" isValidMove :: Queen ::: " + queen + " :: " + i
		// + " :: " + j);
		for (int q = 0; q < queen; q++)
		{

			// System.out.println("data[q][0] :: " + data[q][0]);
			// System.out.println("data[q][1] :: " + data[q][1]);
			if (data[q][0] == i || data[q][1] == j || (Math.abs((i - data[q][0])) == Math.abs((j - data[q][1]))))
			{
				return false;
			}
		}

		// System.out.println("return :: true ");
		data[queen][0] = i;
		data[queen][1] = j;
		return true;
	}

	static private boolean chkAllPosibleConfig(int[][] data, int i, int j, int queen)
	{
		// System.out.println(" chkAllPosibleConfig :: i ::: " + i + " ::: j ::
		// " + j + " data[0][1] :: " + data[0][1] + " queen :: " + queen);
		if (i == data.length)
		{
			if (data[0][1] == (data.length - 1))
			{
				// System.out.println("RETRU FALSE ONE :: " + 1);
				return false;
			} else
			{
				// System.out.println("RETRUn :: " + 2);
				queenPosition = new int[data.length][2];
				return chkAllPosibleConfig(queenPosition, 0, (data[0][1] + 1), 0);
			}

		}

		if (j == data.length)
		{
			if (i == 0)
				return false;
			data[queen][0] = 0;
			data[queen][1] = 0;

			return chkAllPosibleConfig(data, i - 1, (data[queen - 1][1] + 1), queen - 1);
		}

		if (isValidMove(data, queen, i, j))
		{
			if (i == (data.length - 1))
			{
				// System.out.println("RETRUn TRUE :: " + 3);

				for (int t = 0; t < data.length; t++)
				{
					System.out.println(data[t][0] + " :::: " + data[t][1]);
				}
				System.out.println("-----------------------------------------------------------");
				return true;
			}

			// System.out.println("RETRUn :: " + 4);
			return chkAllPosibleConfig(data, i + 1, 0, queen + 1);

		}

		if (j == (data.length - 1))
		{
			// System.out.println("RETRUn :: " + 6);
			data[queen][0] = 0;
			data[queen][1] = 0;
			return chkAllPosibleConfig(data, i - 1, (data[queen - 1][1] + 1), queen - 1);
		} else
		{
			// System.out.println("RETRUn :: " + 7);
			return chkAllPosibleConfig(data, i, j + 1, queen);
		}

	}

}

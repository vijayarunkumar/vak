package org.vak.lambda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main_Hack implements Comparable<Main_Hack>
{
	public static void main(String... strings)
	{
		// roleStepSQLGen();

		new Main_Hack().getRepeatTempleate("C:\\Users\\va87348\\Desktop\\tmplate.sql", "C:\\Users\\va87348\\Desktop\\control.ctl", "C:\\Users\\va87348\\Desktop\\output.sql", true);
		// int a = test(2);
		// System.out.println(a);

	}

	public boolean getRepeatTempleate(String templatepath, String controlPath, String outputPath, boolean isCompination)
	{
		Scanner scan = null;
		try
		{
			File templateFile = new File(templatepath);
			File controlFile = new File(controlPath);
			scan = new Scanner(templateFile);
			Set<ControlFile> controlList = new HashSet<ControlFile>();
			StringBuilder template = new StringBuilder();
			int dataSize = 0;
			while (scan.hasNext())
				template.append(scan.nextLine() + "\n");
			scan.close();
			scan = new Scanner(controlFile);
			while (scan.hasNext())
			{

				String[] ctl = scan.nextLine().split(" ");
				System.out.println(ctl.length);
				ControlFile control = new ControlFile(ctl[0], ctl[1], Integer.parseInt(ctl[2]));
				if (control.getDataSize() > dataSize)
					dataSize = control.getDataSize();
				controlList.add(control);
			}
			if (isCompination)
				return processDataCompination(template, controlList, outputPath);
			else

				return processData(template, controlList, outputPath, dataSize);

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			scan.close();
		}

		return true;
	}

	private boolean processCompinationData(StringBuilder template, OutputStreamWriter writer, int parent, String currentValue, List<ControlFile> controlList) throws IOException
	{
		if (parent == controlList.size())
		{
			String writeData = template.toString();
			System.out.println(currentValue);
			String[] splitedData = currentValue.split("###~#");

			for (int i = 0; i < splitedData.length; i++)
			{
				writeData = writeData.replace(controlList.get(i).placeHolder, splitedData[i]);
			}
			System.out.println(writeData);
			writer.append(writeData.toString());

			return true;
		} else
		{

			for (int i = 0; i < controlList.get(parent).dataHolder.size(); i++)
			{
				System.out.println("currentValue " + currentValue + "   :: " + i);
				if (currentValue.equals(""))

					processCompinationData(template, writer, parent + 1, controlList.get(parent).dataHolder.get(i), controlList);
				else

					processCompinationData(template, writer, parent + 1, currentValue + "###~#" + controlList.get(parent).dataHolder.get(i), controlList);
			}
		}

		return false;
	}

	private boolean processDataCompination(StringBuilder template, Set<ControlFile> controlList, String outputPath)
	{
		OutputStreamWriter writer = null;
		try
		{
			writer = new OutputStreamWriter(new FileOutputStream(outputPath));

			List<ControlFile> listData = new ArrayList<ControlFile>(controlList);

			processCompinationData(template, writer, 0, "", listData);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	private boolean processData(StringBuilder template, Set<ControlFile> controlList, String outputPath, int dataSize)
	{
		OutputStreamWriter writer = null;
		try
		{
			writer = new OutputStreamWriter(new FileOutputStream(outputPath));
			for (int i = 0; i < dataSize; i++)
			{
				String dataRow = template.toString();
				System.out.println("data Size  :: " + dataSize);
				System.out.println("I :: " + i);

				for (ControlFile contorl : controlList)
				{
					dataRow = dataRow.replace(contorl.placeHolder, contorl.dataHolder.get(i));
				}
				writer.append(dataRow);
			}

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try
			{
				writer.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	private class ControlFile
	{
		List<String> dataHolder = new ArrayList<>();
		final private Map<String, File> fileHolder = new HashMap<String, File>();
		String placeHolder = null;
		String fileLocation = null;
		File file = null;
		int position = 0;

		public ControlFile(String placeHolder, String fileLocation, int position)
		{
			this.placeHolder = placeHolder.trim();
			if (fileHolder.containsKey(fileLocation.trim()))
			{
				this.file = fileHolder.get(fileLocation.trim());
			} else
			{
				this.file = new File(fileLocation.trim());
				fileHolder.put(fileLocation.trim(), this.file);
			}
			this.fileLocation = fileLocation.trim();
			this.position = position;
			getAllData();
		}

		int getDataSize()
		{
			return this.dataHolder.size();
		}

		void getAllData()
		{
			try
			{
				Scanner scan = new Scanner(this.file);
				while (scan.hasNext())
				{
					String[] strArr = scan.nextLine().split(" ");
					if (strArr.length <= this.position)
						dataHolder.add(null);
					else
						dataHolder.add(strArr[this.position]);
				}

			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public int hashCode()
		{
			return placeHolder.hashCode() + fileLocation.hashCode() + position;
		}

		@Override
		public boolean equals(Object obj)
		{
			ControlFile ctl = (ControlFile) obj;

			return (ctl.placeHolder.equals(this.placeHolder) && ctl.fileLocation.equals(this.fileLocation) && this.position == ctl.position);
		}

	}

	static void minimumBribes(int[] q)
	{
		int count = 0;

		int ans = 0;
		for (int i = q.length - 1; i >= 0; i--)
		{
			if (q[i] - (i + 1) > 2)
			{
				System.out.println("Too chaotic");
				return;
			}
			for (int j = Math.max(0, q[i] - 2); j < i; j++)
				if (q[j] > q[i])
					count++;
		}
		System.out.println(count);
	}

	static public int test(int n)
	{
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		return test(n - 1) + test(n - 2);
	}

	private static void roleStepSQLGen()
	{

		File fileStep = new File("C:\\Users\\va87348\\Desktop\\step.txt");
		File fileRole = new File("C:\\Users\\va87348\\Desktop\\role.txt");
		File fileTemplate = new File("C:\\Users\\va87348\\Desktop\\template.sql");
		File fileOut = new File("C:\\Users\\va87348\\Desktop\\role_step.sql");
		String MODULE = "@@MODULE@@";
		String ACTION = "@@ACTION@@";
		String STEPID = "@@STEPID@@";
		String ROLEID = "@@ROLEID@@";
		String FLAG = "@@FLAG@@";
		int TEMPLATE_LINES = 1;
		try
		{
			Scanner scanStep = new Scanner(new FileInputStream(fileStep));
			Scanner scanRole = new Scanner(new FileInputStream(fileRole));
			Scanner scanTemplate = new Scanner(new FileInputStream(fileTemplate));
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(fileOut));
			String[] temoplate = new String[TEMPLATE_LINES];
			int count = 0;
			while (scanTemplate.hasNext())
			{
				temoplate[count++] = scanTemplate.nextLine();
			}
			List<String> steps = new ArrayList<String>();
			while (scanStep.hasNext())
			{
				steps.add(scanStep.nextLine());
			}
			List<String> roles = new ArrayList<String>();

			while (scanRole.hasNext())
			{
				roles.add(scanRole.nextLine());
			}

			write.write("---------" + roles + "----" + steps + "-----\n");
			for (String role : roles)
				for (String step : steps)
					for (String out : temoplate)
					{
						out = out.replaceAll(MODULE, "KYCROR");
						out = out.replaceAll(ACTION, "Create");
						out = out.replaceAll(STEPID, step);
						out = out.replaceAll(ROLEID, role);
						out = out.replaceAll(FLAG, "N");
						write.write(out + "\n");
					}

			scanTemplate.close();
			write.close();
			scanRole.close();
			scanStep.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static private void hdmSQLFIleGen()
	{
		File fileIn = new File("C:\\Users\\va87348\\Desktop\\in.txt");
		File fileTemplate = new File("C:\\Users\\va87348\\Desktop\\CoreFile.txt");
		File fileOut = new File("C:\\Users\\va87348\\Desktop\\out.sql");
		String LOB = "@@LOB@@";
		int LOB_POS = 0;
		String START_DATE = "@@START_DATE@@";
		int START_DATE_PO = 1;
		String END_DATE = "@@END_DATE@@";
		int END_DATE_POS = 2;
		String CRITERIA = "@@CRITERIA@@";
		int CRITERIA_POS = 3;
		int TEMPLATE_LINES = 6;
		try
		{
			Scanner scan = new Scanner(new FileInputStream(fileIn));
			Scanner scanTemplate = new Scanner(new FileInputStream(fileTemplate));
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(fileOut));
			String[] temoplate = new String[TEMPLATE_LINES];
			int count = 0;
			while (scanTemplate.hasNext())
			{
				temoplate[count++] = scanTemplate.nextLine();
			}
			while (scan.hasNext())
			{
				String[] in = scan.nextLine().split("\t");

				write.write("---------" + in[LOB_POS] + "---------\n");
				for (String out : temoplate)
				{
					out = out.replaceAll(LOB, in[LOB_POS]);
					out = out.replaceAll(START_DATE, in[START_DATE_PO]);
					out = out.replaceAll(END_DATE, in[END_DATE_POS]);
					out = out.replaceAll(CRITERIA, in[CRITERIA_POS]);
					write.write(out + "\n");
				}
				write.write("\n");
			}
			scanTemplate.close();
			write.close();
			scan.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static private String addDate(String date, int days)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date)); // Now use today date.
		c.add(Calendar.DATE, days); // Adding 5 days
		String output = sdf.format(c.getTime());
		System.out.println(output);
		return output;
	}

	public int test;
	private int test1;

	public void test123()
	{
		in.notify();
		int s = 0;
		class A
		{
			public void test()
			{
				test = test1 = s;
			}
		}
	}

	Integer in = new Integer(0);

	public void getData() throws InterruptedException
	{
		synchronized (in)
		{
			++in;
			in.wait();
		}
	}

	static void volatileTest()
	{
		final Volatile vo = new Volatile();
		ExecutorService service = Executors.newFixedThreadPool(20);
		final int[] a = new int[1];
		for (int i = 0; i < 200; i++)
		{
			try
			{
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			a[0] = i;
			service.submit(() -> {
				System.out.println(a[0]);
				if (a[0] % 2 != 0)
					vo.update(a[0], a[0], a[0]);
				else
					System.out.println((a[0] - 1) + "  ::: " + vo.days + " :: " + vo.months + " ::: " + vo.years);

			});

		}
		service.shutdown();
	}

	static int pageCount(int n, int p)
	{

		if (n % 2 == 0)
		{
			if (p > ((n / 2) + 1))
			{
				if (n - p != 0)
					return Math.round((n - p) / 2);
				return 0;
			} else
				return Math.round(p / 2);

		} else
		{
			if (p > ((n / 2)))
				return Math.round((n - p) / 2);
			else
				return Math.round(p / 2);
		}

	}

	static void sort(char arr[])
	{
		int n = arr.length;

		// The output character array that will have sorted arr
		char output[] = new char[n];

		// Create a count array to store count of inidividul
		// characters and initialize count array as 0
		int count[] = new int[256];
		/*
		 * for (int i = 0; i < 256; ++i) count[i] = 0;
		 */

		// store count of each character
		for (int i = 0; i < n; ++i)
		{
			++count[arr[i]];
			System.out.println(count[arr[i]]);
		}

		// Change count[i] so that count[i] now contains actual
		// position of this character in output array
		for (int i = 1; i <= 255; ++i)
		{
			count[i] += count[i - 1];
			System.out.println(count[i]);
		}

		System.out.println("       -------------------------------       ");
		// Build the output character array
		// To make it stable we are operating in reverse order.
		for (int i = n - 1; i >= 0; i--)
		{
			System.out.println(count[arr[i]]);
			output[count[arr[i]] - 1] = arr[i];
			--count[arr[i]];
		}

		// Copy the output array to arr, so that arr now
		// contains sorted characters
		for (int i = 0; i < n; ++i)
			arr[i] = output[i];
	}

	static public void breakMultiLoop()
	{
		outerloop: for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (i * j > 6)
				{
					System.out.println("Breaking");
					break outerloop;
				}
				System.out.println(i + " " + j);
			}
		}
		System.out.println("Done");
	}

	static public int getMaxShareValue(int[] a)
	{

		int diff = 0;
		int start = 0;
		int ends = 0;

		for (int i = 0; i < a.length - 1; i++)
		{
			for (int j = i + 1; j < a.length; j++)
			{
				System.out.println(a[j] - a[i]);
				if ((a[j] - a[i]) > diff)
				{
					System.out.println("IF");
					diff = a[j] - a[i];
					start = i;
					ends = j;
				}
			}

			System.out.println("----------------------");
		}

		System.out.println(diff);
		System.out.println(start);
		System.out.println(ends);
		return 0;
	}

	static int count = 1;

	public int testExecutorService()
	{
		count++;

		System.out.println("Start" + Thread.currentThread().getName() + "  ::: Start");

		try
		{
			Thread.currentThread().sleep((10000 / count));
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Ends" + Thread.currentThread().getName() + "  ::: Ends");
		return 1;
	}

	static private void testStack() throws FileNotFoundException
	{

		File file = new File("C:\\Users\\va87348\\Desktop\\stack.log");
		Scanner sc = new Scanner(new FileInputStream(file));

		while (sc.hasNext())
		{
			String input = sc.next();
			System.out.println("input  :: " + input);
			List<Character> stack = new ArrayList<Character>();
			boolean isParsed = true;
			for (int i = 0; i < input.length(); i++)
			{
				if (isClosed(input.charAt(i)))
				{
					System.out.println("stack.size()  :: " + stack.size());
					if (stack.size() != 0 && matchOpenAndClose(stack.get(stack.size() - 1), input.charAt(i)))
					{
						stack.remove(stack.size() - 1);
					} else
					{
						isParsed = false;
						break;
					}
				} else
					stack.add(input.charAt(i));

			}
			if (stack.size() != 0)
				isParsed = false;
			System.out.println("Resule :: " + isParsed);
		}

	}

	private static boolean isClosed(char symbol)
	{
		System.out.println("is Closed :: " + symbol + "  :: " + (symbol == ']' || symbol == '}' || symbol == ')'));
		return symbol == ']' || symbol == '}' || symbol == ')';
	}

	static private boolean matchOpenAndClose(char open, char close)
	{

		System.out.println("matchOpenAndClose open :: " + open + " ::: close ::  " + close + " res :: "
				+ (open == '[' ? close == ']' ? true : false : open == '{' ? close == '}' ? true : false : open == '(' ? close == ')' ? true : false : false));
		return open == '[' ? close == ']' ? true : false : open == '{' ? close == '}' ? true : false : open == '(' ? close == ')' ? true : false : false;
	}

	static int activityNotifications(int[] expenditure, int d)
	{
		int output = 0;
		for (int i = 0; i < (expenditure.length - d); i++)
		{
			float count = 0;
			for (int j = i; j < i + d; j++)
			{
				count += expenditure[j];
			}

			System.out.println("  Count :::: " + count);
			System.out.println("((count / d) * 2)   :: " + ((count / d) * 2));
			System.out.println("expenditure[i + d]   :: " + expenditure[i + d]);
			if (((count / d) * 2) <= expenditure[i + d])
				output++;
		}
		return output;
	}

	public static int findFriendsCircle(int[][] data)
	{
		boolean[] visit = new boolean[data.length];
		Map<Integer, List<Integer>> map = new LinkedHashMap<Integer, List<Integer>>();
		int count = 0;
		for (int i = 0; i < data.length; i++)
		{
			if (!visit[i])
			{
				System.out.println(" i " + i);
				count++;
				List<Integer> list = new ArrayList<Integer>();
				list.add(i);
				map.put(count, list);
				visit[i] = true;
				findFriendsCircle(data, i, visit, map);
			}
		}
		System.out.println(map);

		return count;
	}

	private static void findFriendsCircle(int[][] data, int i, boolean[] visit, Map<Integer, List<Integer>> map)
	{
		for (int j = 0; j < data.length; j++)
		{
			if (!visit[j] & i != j & data[i][j] == 1)
			{
				System.out.println(" i :: " + i + "  j  :: " + j + " ij " + data[i][j]);
				visit[j] = true;
				findFriendsCircle(data, j, visit, map);
				map.get(map.size()).add(j);
			}

		}
	}

	static public int findGlobalMax(int[] arr, int m)
	{

		int glopalMax = Integer.MIN_VALUE;
		for (int i = 0; i < (arr.length - (m - 1)); i++)
		{

			int inner[] = new int[m];
			int innerCount = 0;
			for (int j = i; j < (i + m); j++)
			{
				inner[innerCount] = arr[j];
				innerCount++;
			}

			int currentMin = Integer.MAX_VALUE;
			for (int j = 0; j < (inner.length - 1); j++)
			{

				for (int k = (j + 1); k < inner.length; k++)
				{
					int min = inner[k] - inner[j];
					System.out.println(inner[j] + " :: " + inner[k] + " i  :: " + i + " min  :: " + min);
					currentMin = currentMin > min ? min : currentMin;
				}
			}

			glopalMax = glopalMax < currentMin ? currentMin : glopalMax;
		}

		return glopalMax;

	}

	public static void print()
	{
		for (int i = 1; i <= 500; i++)
		{
			System.out.println(i);
		}
	}

	// Complete the climbingLeaderboard function below.
	static int[] climbingLeaderboard(int[] scores, int[] alice)
	{
		int[] out = new int[alice.length];

		Arrays.sort(scores);
		Arrays.sort(alice);

		scores = removeDuplicates(scores, alice);

		/*
		 * for (int i = 0; i < alice.length; i++) { out[i] = scores.length + 1;
		 * for (int j = 0; j < scores.length; j++) { if (alice[i] >= scores[j])
		 * { out[i] = scores.length - j; }
		 * 
		 * if(alice[i] < scores[j]) break; } }
		 */
		return scores;
	}

	public static int[] removeDuplicates(int[] scores, int[] alice)
	{
		if (scores.length < 2)
			return scores;

		int[] out = new int[alice.length];
		int j = 0;
		int i = 1;

		while (i < scores.length)
		{
			if (scores[i] == scores[j])
			{
				i++;
			} else
			{

				j++;
				scores[j] = scores[i];
				i++;
			}
		}

		int[] B = Arrays.copyOf(scores, j + 1);

		return B;
	}

	static void test()
	{
		StackTraceElement[] st = Thread.currentThread().getStackTrace();

		for (StackTraceElement stt : st)

			System.out.println("Stack  :: " + stt.getClassName() + "." + stt.getMethodName() + " - " + stt.getLineNumber());

		test2();
	}

	static void test2()
	{
		StackTraceElement[] st = Thread.currentThread().getStackTrace();

		for (StackTraceElement stt : st)

			System.out.println("Stack teast 2 :: " + stt.getClassName() + "." + stt.getMethodName() + " - " + stt.getLineNumber());
	}

	static int pickingNumbers(int[] a)
	{

		int count = 0;

		for (int i = 0; i < a.length; i++)
		{
			int tempCount = 0;
			for (int j = i; j < a.length; j++)
			{
				if (Math.abs(a[i] - a[j]) <= 1)
				{
					System.out.println(" i  :: " + a[i] + " j  :: " + a[j] + " out :: " + Math.abs(a[i] - a[j]));
					tempCount++;
				}

			}
			if (tempCount > count)
			{
				count = tempCount;
				System.out.println("  i :: " + a[i] + " Count  :: " + count);
			}
		}
		System.out.println(count);
		return count;
	}

	static int formingMagicSquare(int[][] s)
	{
		int cost = Integer.MAX_VALUE;
		int t[][] =
		{
				{ 4, 9, 2, 3, 5, 7, 8, 1, 6 },
				{ 4, 3, 8, 9, 5, 1, 2, 7, 6 },
				{ 2, 9, 4, 7, 5, 3, 6, 1, 8 },
				{ 2, 7, 6, 9, 5, 1, 4, 3, 8 },
				{ 8, 1, 6, 3, 5, 7, 4, 9, 2 },
				{ 8, 3, 4, 1, 5, 9, 6, 7, 2 },
				{ 6, 7, 2, 1, 5, 9, 8, 3, 4 },
				{ 6, 1, 8, 7, 5, 3, 2, 9, 4 }, };
		int temp = 0;
		for (int i = 0; i < 8; i++)
		{
			temp = Math.abs(s[0][0] - t[i][0]) + Math.abs(s[0][1] - t[i][1]) + Math.abs(s[0][2] - t[i][2]) + Math.abs(s[1][0] - t[i][3]) + Math.abs(s[1][1] - t[i][4]) + Math.abs(s[1][2] - t[i][5])
					+ Math.abs(s[2][0] - t[i][6]) + Math.abs(s[2][1] - t[i][7]) + Math.abs(s[2][2] - t[i][8]);
			cost = temp < cost ? temp : cost;
		}
		return cost;

	}

	static int formingMagicSquares(int[][] s)
	{

		System.out.println("ARG :: " + s.length);
		int total = 0;

		for (int i = 1; i <= (s.length * s[0].length); i++)
			total += i;
		total = total / s.length;

		System.out.println(total);

		return (int) (total);

	}

	int proceesMagicSqureRow(int[][] s, int total)
	{
		for (int i = 0; i < s.length; i++)
		{
			if (isSameNumberExcist(s[0]))
			{
				changeValues(s[0], total);
			}
			isRowCorrect(s[0], total);
		}

		return 0;
	}

	void changeValues(int[] is, int total)
	{

	}

	boolean isRowCorrect(int[] row, int total)
	{
		int value = 0;
		for (int i = 0; i < row.length; i++)
		{
			value += row[i];
		}

		return value == total;
	}

	boolean isSameNumberExcist(int[] row)
	{
		int temTotal = 0;
		for (int i = 0; i < row.length; i++)
		{
			temTotal += row[i];
			for (int j = i + 1; i < row.length; j++)
			{

				// if (row[i] == row[j])
				// changeValues();
			}
		}
		return false;
	}

	static ArrayList<Integer[][]> magicSquares = new ArrayList<Integer[][]>();

	public static void generateSquares(Integer[][] square, int[] usedNums, int startPos)
	{

		/*
		 * int cost[] = { 0, 0, 0, 0, 0, 0, 0, 0 }; int t[][] = { { 4, 9, 2, 3,
		 * 5, 7, 8, 1, 6 }, { 4, 3, 8, 9, 5, 1, 2, 7, 6 }, { 2, 9, 4, 7, 5, 3,
		 * 6, 1, 8 }, { 2, 7, 6, 9, 5, 1, 4, 3, 8 }, { 8, 1, 6, 3, 5, 7, 4, 9, 2
		 * }, { 8, 3, 4, 1, 5, 9, 6, 7, 2 }, { 6, 7, 2, 1, 5, 9, 8, 3, 4 }, { 6,
		 * 1, 8, 7, 5, 3, 2, 9, 4 }, };
		 * 
		 * for (int i = 0; i < 3; i++) { cost[i] = Math.abs(t[i][0] - t[0][0]) +
		 * Math.abs(t[i][1] - t[0][1]) + Math.abs(t[i][2] - t[0][2]); cost[i] =
		 * cost[i] + Math.abs(t[i][3] - t[1][0]) + Math.abs(t[i][4] - t[1][1]) +
		 * Math.abs(t[i][5] - t[1][2]); cost[i] = cost[i] + Math.abs(t[i][6] -
		 * t[2][0]) + Math.abs(t[i][7] - t[2][1]) + Math.abs(t[i][8] - t[2][2]);
		 * }
		 * 
		 * Arrays.sort(cost); System.out.println(cost[0]);
		 */

		// try each possible remaining digit for each table cell
		System.out.println("Start position :: " + startPos);
		for (int j = 1; j <= 9; j++)
		{
			System.out.println("JJ " + j);
			if (usedNums[j] > 0)
				continue;

			square[startPos / 3][startPos % 3] = j;

			// check that complete row equals 15
			if (startPos % 3 == 2 && square[startPos / 3][0] + square[startPos / 3][1] + square[startPos / 3][2] != 15)
			{
				continue;
			}

			// check that complete column equals 15
			if (startPos / 3 >= 2 && square[0][startPos % 3] + square[1][startPos % 3] + square[2][startPos % 3] != 15)
			{
				continue;
			}

			// check that complete diagonals equals 15
			if (startPos == 6 && square[0][2] + square[1][1] + square[2][0] != 15)
			{
				continue;
			}

			if (startPos == 8 && square[0][0] + square[1][1] + square[2][2] != 15)
			{
				continue;
			}

			int[] clonedUsedNums = usedNums.clone();
			clonedUsedNums[j] = j;

			if (startPos == 8)
			{
				Integer[][] newSquare = square.clone();
				magicSquares.add(newSquare);
			} else
			{
				generateSquares(square, clonedUsedNums, startPos + 1);
			}
		}

	}

	@Override
	public int compareTo(Main_Hack o)
	{
		System.out.println("Compare TO");
		this.name.compareTo(o.name);
		return 0;
	}

	@Override
	public int hashCode()
	{

		System.out.println("Hash Code Called");
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		System.out.println("EquLS cALL" + this.name.equals(((Main_Hack) obj).name));
		return this.name.equals(((Main_Hack) obj).name);
	}

	private String name = "";

	@Override
	public String toString()
	{
		return super.toString();
	}

	static void generatePermutations(List<List<String>> Lists, List<String> result, int depth, String current)
	{
		if (depth == Lists.size())
		{
			result.add(current);
			return;
		}

		for (int i = 0; i < Lists.get(depth).size(); ++i)
		{
			generatePermutations(Lists, result, depth + 1, current + Lists.get(depth).get(i));
		}
	}

	public static void mainOld(String... strings) throws FileNotFoundException, IOException, CloneNotSupportedException
	{

		/*
		 * System.out.println("----- Main Start ----- ");
		 * 
		 * int[][] arg = { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 5 } }; int cost =
		 * formingMagicSquare(arg);
		 * 
		 * System.out.println("----- Main Ends ------- " + cost); Integer[][]
		 * args = { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 5 } };
		 * 
		 * generateSquares(args, new int[10], 0);
		 * 
		 * // generateSquares(arg, new int[0], 0);
		 */

		/*
		 * int s[] = { 4, 6, 5, 3, 3, 1 }; pickingNumbers(s);
		 */

		// test();

		/*
		 * Ser ser = new Ser(); ser.seralizeMe();
		 * 
		 * ser.clone();
		 */

		/*
		 * Map<Main_Hack, String> map = new Hashtable<>(); Main_Hack main_Hack1
		 * = new Main_Hack(); main_Hack1.name = "vak"; Main_Hack main_Hack2 =
		 * new Main_Hack(); main_Hack2.name = "vak"; Main_Hack main_Hack3 = new
		 * Main_Hack(); main_Hack3.name = "vak2"; map.put(main_Hack1, "");
		 * map.put(main_Hack2, ""); map.put(main_Hack3, "");
		 * 
		 * Set<String> set = new HashSet<String>(); set.add("A"); set.add("DS");
		 * set.add("AAA"); set.add("Ea"); set.add("AA"); set.add("123");
		 * set.add("432");
		 * 
		 * Map<String, String> map1 = new HashMap<String, String>();
		 * 
		 * map1.put("A", null); map1.put("DS", null); map1.put("123", null);
		 * map1.put("AAA", null); map1.put("Ea", null); map1.put("AA", null);
		 * map1.put("432", null);
		 * 
		 * System.out.println(set); System.out.println(map1);
		 */

		/*
		 * int[] score = { 100, 100, 50, 40, 40, 20, 10 }; int[] al = { 5, 25,
		 * 50, 120 };
		 * 
		 * int[] out = climbingLeaderboard(score, al);
		 * 
		 * for (int ou : out) { System.out.println("FINAL RANK : " + ou); }
		 * 
		 * Main_Hack[] test = new Main_Hack[3];
		 * 
		 * test[0] = new Main_Hack(); test[1] = new Main_Hack(); test[2] = new
		 * Main_Hack();
		 * 
		 * Arrays.sort(test);
		 * 
		 * Connection con;
		 * 
		 * int t = -45; System.out.println("PArame :: " + (-t));
		 * 
		 * Thread t1 = new Thread(Main_Hack::print); Thread t2 = new
		 * Thread(Main_Hack::print);
		 * 
		 * List<String> list = new LinkedList<>();
		 * 
		 * list.add("10");
		 * 
		 * System.out.println("from list :: " + list.get(0) + "  " +
		 * list.size());
		 */

		/*
		 * try { System.out.println(new
		 * File(Main_Hack.class.getProtectionDomain().getCodeSource().
		 * getLocation().toURI()).getPath() + File.separator+ "lib"); } catch
		 * (URISyntaxException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * File file = new File("C:\\actimzeLogs3\\dump.log"); BufferedReader br
		 * = new BufferedReader(new FileReader(file)); List<String> list1 = new
		 * ArrayList<String>(); System.out.println("Start ::: "); while
		 * (br.ready()) { list1.add(br.readLine()); //
		 * System.out.println(br.readLine());
		 * 
		 * } boolean v = true;
		 * 
		 * int count =0; while(v) { try { Thread.sleep(10000); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 * 
		 * System.out.println("Ends");
		 */

		/*
		 * int[] data11 = { 1, 2, 3, 4, 5 }; findGlobalMax(data11, 3);
		 * 
		 * List<Integer> lists =
		 * Arrays.stream(data11).boxed().collect(Collectors.toList()); data11 =
		 * lists.stream().mapToInt(i -> { System.out.println(i); return i;
		 * }).toArray(); System.out.println(lists);
		 */

		/*
		 * int[][] data = { { 5, 0, 0, 1, 1 }, { 0, 5, 1, 0, 0 }, { 0, 1, 5, 0,
		 * 0 }, { 1, 0, 0, 5, 0 }, { 1, 0, 0, 0, 5 } };
		 * System.out.println("count  :: " + findFriendsCircle(data));
		 */
		/*
		 * File file = new File("C:\\Users\\va87348\\Desktop\\test.log");
		 * Scanner scan = new Scanner(new FileInputStream(file)); int t =
		 * Integer.parseInt(scan.nextLine().split(" ")[1]); String s =
		 * scan.nextLine(); System.out.println(s.length()); String[] str =
		 * s.split(" "); int[] arr = new int[str.length]; for (int i = 0; i <
		 * str.length; i++) { arr[i] = Integer.parseInt(str[i]); }
		 * System.out.println(activityNotifications(arr, t)); char open = 'a';
		 * char close = 'a'; Character ssss; boolean sss = open == '[' ? close
		 * == ']' ? true : false : open == '{' ? close == '}' ? true : false :
		 * open == '(' ? close == ')' ? true : false : false;
		 */
		// testStack();

		// System.out.println('s'!='s' & 'a' == 'a');

		/*
		 * System.out.println(5 >> 2);
		 * 
		 * ExecutorService ex = Executors.newFixedThreadPool(5);
		 * 
		 * Main_Hack test = new Main_Hack(); while (count < 10) {
		 * ex.submit(test::testExecutorService); } ex.shutdown()
		 */;

		/*
		 * int[] a = { 2, 5, 1, 7, 2, 8, 3, 4, 5 }; getMaxShareValue(a);
		 */

		/*
		 * char arr[] = { 'g', 'e', 'e', 'k', 's', 'f', 'o', 'r', 'g', 'e', 'e',
		 * 'k', 's' };
		 * 
		 * sort(arr);
		 * 
		 * System.out.print("Sorted character array is "); for (int i = 0; i <
		 * arr.length; ++i) System.out.print(arr[i]);
		 */

		// breakMultiLoop();
		// System.out.println(pageCount(5, 1));

		/*
		 * int i = 5; i = i >> 1; System.out.println(14 ^ 23); Object[] myObj =
		 * new String[] { "one", "two", "three" }; for (String s : (String[])
		 * myObj) System.out.print(s + ".");
		 */

		/*
		 * String a = "ab"; String b = new String("cab");
		 * 
		 * String s = b.substring(1);
		 * 
		 * System.out.println(a.hashCode()); System.out.println(s.hashCode());
		 * System.out.println(b.hashCode()); System.out.println(a);
		 * System.out.println(s); System.out.println(b);
		 * System.out.println(a==b); System.out.println(s==b);
		 */
		for (int i = 0; i < 100; i++)
			System.out.println((int) (Math.random() * 3));
	}

}

class Volatile
{
	int years;
	int months;
	volatile int days;

	public void update(int years, int months, int days)
	{
		this.years = years;
		this.months = months;
		this.days = days;
	}

}
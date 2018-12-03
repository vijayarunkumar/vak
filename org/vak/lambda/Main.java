package org.vak.lambda;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mikew
 */
public class Main
{

	static String timeConversion(String s)
	{
		System.out.println(s.startsWith("12"));
		if (s.contains("PM"))
			if (!s.startsWith("12"))
				s = (Integer.parseInt(s.substring(0, 2)) + 12) + s.substring(2, s.length());
			else if (s.startsWith("12"))
			{
				System.out.println(s);
				s = "00" + s.substring(2, s.length());
				System.out.println(s);
			}

		return s.substring(0, s.length() - 2);

	}

	/*
	 * Complete the verticalRooks function below.
	 */
	static String verticalRooks(int[] r1, int[] r2)
	{
		int p1 = 0;
		int p2 = 0;
		plyer2Move(r1, r2);
		for (int i = 0; i < r1.length; i++)
		{
			int result = currentColumnFinishBy(r1[i], r2[i], (r1.length));
			System.out.println("Result :: " + result);
			if (result == 1)
				p1++;
			else if (result == 2)
				p2++;
			else
				System.out.println("Going on");
		}

		if (p1 == 0 && p2 == r1.length)
			return "player-2";
		else if (p2 == 0 && p1 == r1.length)
			return "player-1";
		return "Wrong ";
	}

	static void plyer2Move(int[] r1, int[] r2)
	{

	}

	static boolean findPosibleMOves(int[] r1, int[] r2, int player)
	{
		for (int i = 0; i < r1.length; i++)
		{

			if (currentColumnFinishBy(r1[i], r2[i], r1.length) == 0)
			{

			}
		}

		return true;
	}

	static void plyer1Move(int[] r1, int[] r2)
	{

	}

	static int currentColumnFinishBy(int r1, int r2, int size)
	{
		System.out.println(r1 + " -- " + r2 + " -- " + size);
		if ((r1 == 1 && r2 == 2) || (r1 == size && (r2 == size - 1)))
			return 2;
		else if ((r2 == 1 && r1 == 2) || (r2 == size && (r1 == size - 1)))
			return 1;

		return 0;
	}

	public static void main(String[] args)
	{

		/*
		 * RunnableTest.main(args); ComparatorTest.main(args);
		 * ListenerTest.main(args);
		 */

		/*
		 * int[] arr = { 1, -2, 4, -5, 1 };
		 * 
		 * int number = 1; int count =0; for (int k = 0; k < arr.length; k++) {
		 * for (int i = 0; i < (arr.length - k); i++) { int tempCount = 0; for
		 * (int j = i; j < (i + number); j++) { tempCount +=arr[j]; }
		 * if(tempCount < 0) count++;
		 * 
		 * } number++; } System.out.println(count);
		 */

		// Scanner scan = new Scanner(System.in);

		// System.out.println(isPalindrome("AbBA"));
		// System.out.println(new MyRegex().pattern);
		// System.out.println("111".matches(new MyRegex().pattern));
		// timeConversion("12:40:22AM");
		/*
		 * int[] r1 = { 1, 1, 1, 1 }; int[] r2 = { 2, 2, 2, 2 };
		 * 
		 * System.out.println(verticalRooks(r1, r2));
		 */

		/*
		 * int j = 0; for(int i=0;i<10;i++) { System.out.println(j++ + " -- " +
		 * j); System.out.println("Hai"); }
		 */

		/*
		 * String data = new String("<test><ONE>INNER</ONE></test>");
		 * List<String> token = new ArrayList<String>(); List<String> out = new
		 * ArrayList<String>(); data = parser(data, token, out);
		 * 
		 * System.out.println(token); int j = 0; int k=0; for (int i = 0; i <
		 * 10; i++) { k = j++; System.out.println("Helo" + k); }
		 */
		/*
		 * System.out.println(System.getProperty("user.home")); String account =
		 * ""; StringBuilder assctdAccnts = new StringBuilder(); StringBuilder
		 * accntType = new StringBuilder(); int count = 0; String data =
		 * "ACOUNT MULTIPLE"; boolean isAccoutExceed = false;
		 * 
		 * while (true && !isAccoutExceed) { if (count == 0) { account = data;
		 * assctdAccnts.append(data); count++; } else {
		 * 
		 * if ((assctdAccnts.toString().length() + data.length()) <= 255) {
		 * assctdAccnts.append("||").append(data); account = "";
		 * accntType.setLength(0);// Empty the account type if more // than one
		 * account available } else { System.out.println("EXIT"); break; } } }
		 * 
		 * System.out.println(assctdAccnts); System.out.println("account" +
		 * account);
		 */

		Map<Serialize, String> mapSerialize = new HashMap<Serialize, String>();
		Map<String, String> mapString = new HashMap<String, String>();
		Serialize serialize = new Serialize();
		Serialize serialize1 = new Serialize();
		Serialize serialize2 = new Serialize();
		Serialize serialize3 = new Serialize();
		serialize.name = "Vijai";
		serialize3.name = "Vijai";
		serialize1.name = "Arun";
		serialize2.name = "Arun";
		mapString.put("Vijai", "Vijai");
		mapString.put("Vijai", "Vijai");
		mapString.put("Arun", "Arun");
		mapString.put("Arun", "Arun");
		mapSerialize.put(serialize, "Vijai");
		mapSerialize.put(serialize1, "Arun");
		mapSerialize.put(serialize2, "Arun");
		mapSerialize.put(serialize3, "Vijai");

		System.out.println("mapString  :: " + mapString);
		System.out.println("mapSerialize  :: " + mapSerialize);
	}

	static private File file = new File(System.getProperty("user.home") + "/APF.skin");

	static class Serialize implements Serializable
	{

		public Serialize()
		{
			// System.out.println("has " + this.hashCode());
		}

		/**
		* 
		*/
		private static final long serialVersionUID = 343434343L;
		String name = "";

		@Override
		public int hashCode()
		{
			int hash = this.name.hashCode();
			System.out.println("HASH CODE CALLED" + hash);
			return 1;
		}

		@Override
		public boolean equals(Object obj)
		{
			System.out.println("EQUALS METHOD CALLS");
			Serialize se = (Serialize) obj;
			return name.equals(se.name);
		}

	}

	static String parser(String data, List<String> token, List<String> out)
	{
		System.out.println("INI Data :: " + data);
		System.out.println("INI Token :: " + token);
		System.out.println("INI out :: " + out);
		while (data.length() > 2)
		{

			data = getFromTag(data, token, out);
			System.out.println("From Data :: " + data);
			System.out.println("From Token :: " + token);
			System.out.println("From out :: " + out);

			data = getEndTag(data, token, out);
			System.out.println("end data :: " + data);
			System.out.println("end token :: " + token);
			System.out.println("end out :: " + out);

		}

		return data;
	}

	/* static String */

	static String getFromTag(String data, List<String> token, List<String> out)
	{
		System.out.println(data.substring(data.indexOf(">") - 1, data.indexOf(">")));
		if (data.charAt(0) == '<' && data.charAt(1) != '/')
		{
			token.add(data.substring(1, data.indexOf(">")));
			return data.substring(data.indexOf(">") + 1, data.length());
		}
		return data;
	}

	static String getEndTag(String data, List<String> token, List<String> out)
	{
		if ((data.substring(data.indexOf("<"), data.indexOf("<") + 2).equals("</")))
		{
			if (token.get(token.size() - 1).equals((data.substring(data.indexOf("</") + 2, data.indexOf(">")))))
			{
				token.remove(token.size() - 1);

				out.add(data.substring(0, data.indexOf("<")));
				return data.substring(data.indexOf(">") + 1, data.length());
			} else
			{
				System.out.println("DATA :: " + data);
				out.add("None");
				return data.substring(data.indexOf(">") + 1, data.length());
			}

		}
		return data;
	}

	static class MyRegex
	{
		public String pattern = "[01]?[0-9][0-9]?";

	}

	public static String passwordExample()
	{
		Console console = System.console();
		if (console == null)
		{
			System.out.println("Couldn't get Console instance");
		}

		char passwordArray[] = console.readPassword("Enter your secret password: ");
		return new String(passwordArray);

	}

	protected boolean isPalindrome(String word)
	{

		char[] ch = word.toLowerCase().toCharArray();
		for (int i = 0; i < (ch.length / 2); i++)
		{
			if (ch[i] != ch[ch.length - (i + 1)])
				return false;
		}

		return true;
	}

}

interface MyInterface
{

	void printIt(String text);

	default public void printUtf8To(String text, OutputStream outputStream)
	{
		try
		{
			outputStream.write(text.getBytes("UTF-8"));
		} catch (IOException e)
		{
			throw new RuntimeException("Error writing String as UTF-8 to OutputStream", e);
		}
	}

	static void printItToSystemOut(String text)
	{
		System.out.println(text);
	}
}

class Test extends Main
{
	protected boolean isPalindrome(String word)
	{

		char[] ch = word.toLowerCase().toCharArray();
		for (int i = 0; i < (ch.length / 2); i++)
		{
			if (ch[i] != ch[ch.length - (i + 1)])
				return false;
		}

		return true;
	}
}

package org.vak.lambda;

import java.io.IOException;
import java.io.OutputStream;

public class Java8Examples
{

	public static void main(String[] args)
	{
		MyInterface myInterface = text -> {
			System.out.println(" test ::: " + text);
			return 45;
		};

		int s = myInterface.printIt("test");
		System.out.println("INT :: " + s);
		myInterface.printUtf8To("test", null);
		MyInterface.printItToSystemOut("test");

		Java8Examples Java8Examples = new Java8Examples();

		Java8Examples.method(Java8Examples::printIterrer);
	}

	public void method(MyInterface obj)
	{
		obj.printIt("Est");
		obj.printUtf8To("Est", null);
	}

	public int printIterrer(String text)
	{
		System.out.println("FROm INNER METHOD REF" + text);
		return 0;
	}

}

class TestDefault implements MyInterface, MyInterfaceOne
{

	@Override
	public int printIt(String text)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printUtf8To(String text, OutputStream outputStream)
	{
		// TODO Auto-generated method stub
		MyInterface.super.printUtf8To(text, outputStream);
	}

}

interface MyInterface
{

	public int printIt(String text);
	

	default void printUtf8To(String text, OutputStream outputStream)
	{
		try
		{

			if (outputStream != null)
				outputStream.write(text.getBytes("UTF-8"));
			else
				System.out.println("ELSE Running");
		} catch (IOException e)
		{
			throw new RuntimeException("Error writing String as UTF-8 to OutputStream", e);
		}
	}

	public static void printItToSystemOut(String text)
	{
		System.out.println("Static " + text);
	}
}

interface MyInterfaceOne
{

	public int printIt(String text);

	default public void printUtf8To(String text, OutputStream outputStream)
	{
		try
		{

			if (outputStream != null)
				outputStream.write(text.getBytes("UTF-8"));
			else
				System.out.println("ELSE Running");
		} catch (IOException e)
		{
			throw new RuntimeException("Error writing String as UTF-8 to OutputStream", e);
		}
	}

	public static void printItToSystemOut(String text)
	{
		System.out.println("Static " + text);
	}
}

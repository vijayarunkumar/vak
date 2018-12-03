package org.vak.lambda;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class FormatterUtility
{

	public static void main(String a[])
	{

		FormatterUtility formatterUtility = new FormatterUtility();
		formatterUtility.openSQlD();
		formatterUtility.readAllfile(new File("C:\\Users\\va87348\\Desktop\\SQL"));

	}

	private boolean startProcess(File file)
	{
		StringBuilder bu = readFile(file);
		pasteToSQLD(bu.toString());
		formatSQLD();
		String data = copyFromSQLD();
		writeTofile(file.getAbsolutePath(), data);
		return true;
	}

	private boolean readAllfile(File file)
	{

		if (file.isDirectory())
		{
			File[] filesDir = file.listFiles();

			for (File files : filesDir)
			{
				readAllfile(files);
			}

		} else
		{
			startProcess(file);

		}
		return false;

	}

	private boolean openSQlD()
	{
		try
		{
			Robot robot = new Robot();
			robot.mouseMove(760, 740);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);

		} catch (AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private String copyFromSQLD()
	{
		try
		{

			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_X);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getFromClipbord();
	}

	private boolean formatSQLD()
	{
		try
		{

			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_F7);
			robot.keyRelease(KeyEvent.VK_F7);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private boolean pasteToSQLD(String content)
	{
		try
		{
			setToClipbord(content);
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private StringBuilder readFile(File path)
	{

		StringBuilder template = new StringBuilder();
		try
		{

			Scanner scan = new Scanner(path);

			while (scan.hasNext())
				template.append(scan.nextLine() + "\n");
			scan.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return template;
	}

	private boolean writeTofile(String path, String content)
	{
		OutputStreamWriter writer = null;
		try
		{
			writer = new OutputStreamWriter(new FileOutputStream(path));

			writer.append(content);

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

				e.printStackTrace();
			}
		}

		return true;
	}

	private boolean setToClipbord(String input)
	{
		StringSelection selection = new StringSelection(input);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
		return true;
	}

	private String getFromClipbord()
	{
		String data = null;
		try
		{
			data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(data);

		return data;
	}

}

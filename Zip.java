import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip
{
	public static String sourceName = "";
	public static String destinationName = "";

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		for (;;)
		{
			try
			{
				if (input())
				{
					unzip(sourceName, destinationName);
					success();
				} else
				{
					fail();
				}
			} catch (Exception e)
			{
				fail();
			}
		}
	}

	public static void unzip(String srcZipFile, String outputPath)
			throws FileNotFoundException, IOException
	{

		ZipInputStream zip = null;
		FileInputStream fileReader = null;

		fileReader = new FileInputStream(srcZipFile);
		zip = new ZipInputStream(fileReader);

		ZipEntry entry = null;

		if (outputPath == null)
			outputPath = "";
		else
			outputPath += File.separator;

		File outputDirectory = new File(outputPath);

		if (outputDirectory.exists())
			outputDirectory.delete();

		outputDirectory.mkdir();

		int len;
		byte[] buffer = new byte[1024];

		while ((entry = zip.getNextEntry()) != null)
		{
			if (!entry.isDirectory())
			{
				File file = new File(outputPath + entry.getName());

				if (!new File(file.getParent()).exists())
					new File(file.getParent()).mkdirs();

				FileOutputStream fileWriter = new FileOutputStream(file);

				while ((len = zip.read(buffer)) > 0)
				{
					fileWriter.write(buffer, 0, len);
				}
				fileWriter.flush();
				fileWriter.close();
				fileReader.close();
				zip.close();
			}
		}
	}

	public static boolean input()
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		out.println(">Please enter zip file name and destination directory to unzip\n>(e.g. Enter:  a.zip destDir):");
		if (scanner.hasNext())
		{
			sourceName = String.valueOf(scanner.next());
			destinationName = String.valueOf(scanner.next());
			if (!sourceName.contains(".zip"))
			{
				sourceName += ".zip";
			}
			if (sourceName.length() <= 0 || destinationName.length() <= 0)
			{
				return false;
			}
			if ((sourceName.contains(File.separator) && !sourceName
					.contains(":"))
					|| (destinationName.contains(File.separator) && !destinationName
							.contains(":")))
			{
				return false;
			}
		} else
		{
			return false;
		}
		return true;
	}

	private static void success()
	{
		out.println("Successfully unzip " + sourceName + " to "
				+ destinationName + " !\n=====================");
	}

	private static void fail()
	{
		out.println("Fail to unzip! Please try again!\n=====================");
	}
}

import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip
{

	public static String sourceName = "";
	public static String destinationName = "";
	public static String separater = File.separator;

	public static void main(String args[]) throws IOException
	{
		for (;;)
		{
			try
			{
				if (input())
				{
					zipDirectory();
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

	private static void zipDirectory() throws Exception
	{
		FileOutputStream fileWriter = null;
		ZipOutputStream zip = null;

		fileWriter = new FileOutputStream(destinationName);
		zip = new ZipOutputStream(fileWriter);

		ZipFolder("", sourceName, zip);

		zip.flush();
		zip.close();
		fileWriter.flush();
		fileWriter.close();
	}

	private static void ZipFile(String path, String srcFile,
			ZipOutputStream ouputZip) throws Exception
	{

		File folder = new File(srcFile);
		if (folder.isDirectory())
		{
			ZipFolder(path, srcFile, ouputZip);
		} else
		{
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			ouputZip.putNextEntry(new ZipEntry(path + separater
					+ folder.getName()));
			while ((len = in.read(buf)) > 0)
			{
				ouputZip.write(buf, 0, len);
			}
			in.close();
		}
	}

	private static void ZipFolder(String path, String srcFolder,
			ZipOutputStream outputZip) throws Exception
	{
		File folder = new File(srcFolder);

		for (String fileName : folder.list())
		{
			if (path.equals(""))
			{
				ZipFile(folder.getName(), srcFolder + separater + fileName,
						outputZip);
			} else
			{
				ZipFile(path + separater + folder.getName(), srcFolder
						+ separater + fileName, outputZip);
			}
		}
	}

	public static boolean input()
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		out.println(">Please enter source directory and destination file name to zip source folder\n>(e.g. Enter: srcDir b.zip):");
		if (scanner.hasNext())
		{
			sourceName = String.valueOf(scanner.next());
			destinationName = String.valueOf(scanner.next());
			if (!destinationName.contains(".zip"))
			{
				destinationName += ".zip";
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
		out.println("Successfully zip " + sourceName + " to " + destinationName
				+ " !\n======================");
	}

	private static void fail()
	{
		out.println("Fail to zip the folder! Please try again!\n======================");
	}

}

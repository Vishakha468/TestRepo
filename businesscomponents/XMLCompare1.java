package businesscomponents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.custommonkey.xmlunit.*;

public class XMLCompare1 extends ExcelData 
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		createSheet();
		readfiles();
		statusSheet();
	}
	public static void readfiles() throws IOException
	{
		String relPath = new File("").getAbsolutePath();
		System.out.println("absolute path is :" +relPath);
		String srcFolderPath = relPath.concat("\\Source");
		String destFolderPath = relPath.concat("\\Dest");
		//System.out.println("source folder Path is :" +srcFolderPath);
		//System.out.println("destination folder Path is :" +destFolderPath);
		ArrayList<String> Files = getallfiles(srcFolderPath,destFolderPath);
		//System.out.println("Files array size is :" +Files.size());
		BufferedReader br1 = null;
		FileReader fr1 = null;
		BufferedReader br2 = null;
		FileReader fr2 = null;

		if(Files.contains("Files Not Found"))
		{
			Files.remove("Files Not Found");
			
			report("Files Not Found","Files not found in Desination folder are  :"+Files, "Files Not Found");
		}
		else if(Files.contains("Files Count Mismatch"))
		{
			Files.remove("Files Count Mismatch");
			report("Files Count Mismatch","Files count from Source and Destination folder Mismatch ", "Files Count Mismatch");
		}
		else
		{
			if(Files.size()!=0)
			{
				for(int k=0;k<Files.size();k++)
				{
					File sourceFile = new File(srcFolderPath.concat("\\"+Files.get(k)));
					File destFile  = new File(destFolderPath.concat("\\"+Files.get(k)));
					try
					{
						fr1 = new FileReader(sourceFile);
						fr2 = new FileReader(destFile);
						br1 = new BufferedReader(fr1);
						br2 = new BufferedReader(fr2);
						//XMLUnit.setIgnoreComments(Boolean.TRUE);
						//XMLUnit.setIgnoreWhitespace(Boolean.TRUE);
						//XMLUnit.setNormalizeWhitespace(Boolean.TRUE);
						XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
						//XMLUnit.setIgnoreAttributeOrder(Boolean.TRUE);
						Diff diff = new Diff(fr1, fr2);
						//	readXMLByNodes(sourceFile);
						Boolean identical = diff.identical();
						//System.out.println("identical value is : "+identical);
						Boolean sim = diff.similar();
						System.out.println("similar is :" +sim);
						
						if(sim.equals(true))
						{
							report(Files.get(k),"Two xmls are Similar" , "Similar");
						}
						else
						{
							DetailedDiff detDiff = new DetailedDiff(diff);
							List differences = detDiff.getAllDifferences();
							int n = differences.size();
							if(n!=0)
							{
								//	System.out.println("size of differences list is :" +n);
								for (Object object : differences)
								{
									Difference difference = (Difference)object;
									if(difference.toString().contains("Expected sequence of child nodes"))
									{
										int indnode = difference.toString().indexOf("nodes");
										//System.out.println("Index is :  "+indnode);
										int srcnumber = Integer.parseInt(difference.toString().substring(indnode+7, indnode+8));
										//System.out.println("Source Number is :" +srcnumber);
										int inddestnode = difference.toString().indexOf("was");
										int destnumber = Integer.parseInt(difference.toString().substring(inddestnode+5, inddestnode+6));
										//System.out.println("Dest Number is :"+destnumber);
										//	readXMLByNodes(sourceFile,srcnumber+1);
										//readXMLByNodes(destFile,destnumber+1);
										//report.updateTestLog("XML Differences for "+Files.get(k),"differences are ***"+difference.toString(), Status.FAIL);
										report(Files.get(k),"differences are ***"+difference.toString(), "Done");
										//"XML Differences for "
									}
									else
										report(Files.get(k),"differences are ***"+difference.toString(), "FAIL");
								}
							}
							else
								report(Files.get(k),"Two xmls are identical result" , "PASS");	
						}
					}
					catch(Exception e)
					{
						System.out.println("Exception is : " +e);
					}
				}
			}
		}
	}
	public static ArrayList<String> getallfiles(String srcfolderPath,String destFolderpath)
	{
		File srcfolder = new File(srcfolderPath);
		File destFolder = new File(destFolderpath);
		ArrayList<String> srcFiles = new ArrayList<String>();
		ArrayList<String> destFiles = new ArrayList<String>();
		int no_Srcfiles = 0;
		int no_DestFiles =0;
		File[] lstofsrcFiles = srcfolder.listFiles();
		File[] lstofdestFiles = destFolder.listFiles();
		//if((lstofsrcFiles.length)==(lstofdestFiles.length))
		//	{
		for(int i=0;i<lstofsrcFiles.length;i++)
		{
			if(lstofsrcFiles[i].isFile())
			{ 
				no_Srcfiles++;
				//	System.out.println(lstofsrcFiles[i].getName());
				srcFiles.add(lstofsrcFiles[i].getName());
			}

		}
		for(int i=0;i<lstofdestFiles.length;i++)
		{
			if(lstofdestFiles[i].isFile())
			{ 
				no_DestFiles++;
			}

		}
		/*System.out.println("Source files count is:" +no_Srcfiles);
		System.out.println("Dest files count is:" +no_DestFiles);*/

		/*	if(no_Srcfiles==no_DestFiles)

		{*/
		for(int i=0;i<lstofdestFiles.length;i++)
		{
			if(lstofdestFiles[i].isFile())
			{

				for(int j=0;j<no_Srcfiles;j++)
				{
					if(srcFiles.get(j).contains(lstofdestFiles[i].getName()))
					{
						destFiles.add(lstofdestFiles[i].getName());
						break;
					}
				}
			}
		}
		for(int i=destFiles.size()-1;i>=0;i--)	
		{
			if(srcFiles.get(i).contains(destFiles.get(i)))
			{
				srcFiles.remove(i);
			}
		}
		if(srcFiles.size()==0)
		{
			return 	destFiles;
		}
		else
		{
			srcFiles.add("Files Not Found");
			return 	srcFiles;
		}
		//}
		/*else
			{
			srcFiles.add("Files Count Mismatch");

			return srcFiles;
			}*/


	}


}

package businesscomponents;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.soap.Node;

import org.custommonkey.xmlunit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import oracle.xml.diff.XmlUtils;
import com.cognizant.framework.Status;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class xmlTest extends ReusableLibrary 
{
	public xmlTest(ScriptHelper scriptHelper) 
	{
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}





	public static void readfiles()
	{
		
		
		String relPath = new File("").getAbsolutePath();
		//System.out.println("absolute path is :" +relPath);
		String srcFolderPath = relPath.concat("\\Source");
		String destFolderPath = relPath.concat("\\Dest");
		//System.out.println("source folder Path is :" +srcFolderPath);
		//System.out.println("destination folder Path is :" +destFolderPath);
		
		ArrayList<String> Files = getallfiles(srcFolderPath,destFolderPath);
		//System.out.println("Files array is :" +Files);
		BufferedReader br1 = null;
		FileReader fr1 = null;
		BufferedReader br2 = null;
		FileReader fr2 = null;

		
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
			
			
			  XMLUnit.setIgnoreComments(Boolean.TRUE);
		        XMLUnit.setIgnoreWhitespace(Boolean.TRUE);
		        XMLUnit.setNormalizeWhitespace(Boolean.TRUE);
		        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
		        XMLUnit.setIgnoreAttributeOrder(Boolean.TRUE);
		      //  XMLUnit.setCompareUnmatched(Boolean.FALSE);

			Diff diff = new Diff(fr1, fr2);
		//	readXMLByNodes(sourceFile);
			
			Boolean identical = diff.identical();
			//System.out.println("identical value is : "+identical);
			Boolean sim = diff.similar();
			//System.out.println("similar is :" +sim);
			
			DetailedDiff detDiff = new DetailedDiff(diff);
			
			List differences = detDiff.getAllDifferences();
			int n = differences.size();
			if(n!=0)
			{
		//	System.out.println("size of differences list is :" +n);
			for (Object object : differences)
			{
				Difference difference = (Difference)object;
				
				//System.out.println("diffs are : " +difference.toString());
				//System.out.println(difference);
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
					
					report.updateTestLog("XML Differences for "+Files.get(k),"differences are ***"+difference.toString(), Status.FAIL);
					
				}
				else
					report.updateTestLog("XML Differences for "+Files.get(k),"differences are ***"+difference.toString(), Status.FAIL);
			}
			}
			else
				report.updateTestLog("XML Differences for"+Files.get(k),"Two xmls are identical result" , Status.PASS);
			



			String sCurrentLine1;
			String sCurrentLine2;
			String Sourcetxt = null;
			String Desttxt = null;
			br1 = new BufferedReader(new FileReader(sourceFile));
			br2 = new BufferedReader(new FileReader(destFile));

			while ((sCurrentLine1 = br1.readLine())!= null) 
			{
				if(Sourcetxt==null)
					Sourcetxt =  sCurrentLine1;
				else
					Sourcetxt = Sourcetxt + sCurrentLine1;
				//	System.out.println(sCurrentLine1);

			}
			//	System.out.println("Source text is : " +Sourcetxt);
		//	getNodeValuesandType(Sourcetxt);
			while ((sCurrentLine2 = br2.readLine())!= null) 
			{
				if(Desttxt==null)
					Desttxt =  sCurrentLine2;
				else
					Desttxt = Desttxt + sCurrentLine2;
			}
			//System.out.println("Destination Text is : " +Desttxt);

			//	Diff diff = new Diff();
		}
		catch(Exception e)
		{
			System.out.println("Exception is : " +e);
		}
			}
	}
	}

	/*public void getNodevalues()
	{

		Document doc = db.parse(new File(args[0]));
		Node n = doc.getFirstChild();
		NodeList nl = n.getChildNodes();   
		Node an,an2;

		for (int i=0; i < nl.getLength(); i++) {
		    an = nl.item(i);

		    if(an.getNodeType()==Node.ELEMENT_NODE) {
		        NodeList nl2 = an.getChildNodes();

		        for(int i2=0; i2<nl2.getLength(); i2++) {
		            an2 = nl2.item(i2);

		            // DEBUG PRINTS
		            System.out.println(an2.getNodeName() + ": type (" + an2.getNodeType() + "):");

		            if(an2.hasChildNodes())
		                System.out.println(an2.getFirstChild().getTextContent());

		            if(an2.hasChildNodes())
		                System.out.println(an2.getFirstChild().getNodeValue());

		            System.out.println(an2.getTextContent());
		            System.out.println(an2.getNodeValue());
		        }
		    }
		}
	}*/

	public static void getNodeValuesandType(String s) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();


		ByteArrayInputStream bis = new ByteArrayInputStream(s.getBytes());
		Document doc = db.parse(bis);
		Node n =  doc.getFirstChild();
		NodeList nl = n.getChildNodes();
		Node an,an2;

		for (int i=0; i < nl.getLength(); i++) {
			an =  nl.item(i);
			if(an.getNodeType()==Node.ELEMENT_NODE) {
				NodeList nl2 = an.getChildNodes();

				for(int i2=0; i2<nl2.getLength(); i2++) {
					an2 =  nl2.item(i2);
					// DEBUG PRINTS
					System.out.println(an2.getNodeName() + ": type (" + an2.getNodeType() + "):");
					if(an2.hasChildNodes()) System.out.println(an2.getFirstChild().getTextContent());
					if(an2.hasChildNodes()) System.out.println(an2.getFirstChild().getNodeValue());
					System.out.println(an2.getTextContent());
					System.out.println(an2.getNodeValue());
				}
			}
		}	
	}



	public static void readXMLByNodes(File f,int nodeindx) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(f);
		String rootElemnt = document.getDocumentElement().getNodeName();
		System.out.println("Root element :"+ document.getDocumentElement().getNodeName());
		System.out.println("===============================");
		if (document.hasChildNodes()) {
			printNodeList(document.getChildNodes(),rootElemnt,nodeindx);

		}

	}

	private static void printNodeList(NodeList nodeList,String rootEle,int indexNode) 
	{
		Node n = nodeList.item(0);
		int nodeCount = nodeList.getLength();
		if(!(n.getNodeName().equals(rootEle)))
		{
			System.out.println("count of nodes is :" +nodeCount);
			for (int count = 0; count < nodeList.getLength(); count++)
			{
				Node elemNode = nodeList.item(count);
				if (elemNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					System.out.println("Index of node is : "+count);
					System.out.println("Node Name =" + elemNode.getNodeName());
					System.out.println("Node Content =" + elemNode.getTextContent());
					
				}
			}
			
		}
		if (n.hasChildNodes()) 
		{

			//recursive call if the node has child nodes
			printNodeList(n.getChildNodes(),rootEle,indexNode);
		}
	
	/*	for (int count = 0; count < nodeList.getLength(); count++)
		{
			Node elemNode = nodeList.item(count);
			if (elemNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				System.out.println("\nNode Name =" + elemNode.getNodeName() + " [OPEN]");
				System.out.println("Node Content =" + elemNode.getTextContent());
				if (elemNode.hasAttributes()) {
					NamedNodeMap nodeMap = elemNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) 
					{
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : "+ node.getNodeValue());

					}
				}
				if (elemNode.hasChildNodes()) 
				{

					//recursive call if the node has child nodes
					printNodeList(elemNode.getChildNodes(),rootEle);
				}
				System.out.println("Node Name =" + elemNode.getNodeName()+ " [CLOSE]");

			}
		}*/
	}
	
	public static ArrayList<String> getallfiles(String srcfolderPath,String destFolderpath)
	{
		File srcfolder = new File(srcfolderPath);
		File destFolder = new File(destFolderpath);
		ArrayList<String> srcFiles = new ArrayList<String>();
		ArrayList<String> destFiles = new ArrayList<String>();
		int c = 0;
		File[] lstofsrcFiles = srcfolder.listFiles();
		File[] lstofdestFiles = destFolder.listFiles();
		
		for(int i=0;i<lstofsrcFiles.length;i++)
		{
			if(lstofsrcFiles[i].isFile())
			{ 
				c++;
		//	System.out.println(lstofsrcFiles[i].getName());
			srcFiles.add(lstofsrcFiles[i].getName());
			}
			
		}
//		System.out.println("value of c is: " +c);
	//	System.out.println(srcFiles);
		for(int i=0;i<lstofdestFiles.length;i++)
		{
			if(lstofdestFiles[i].isFile())
			{
				for(int j=0;j<c;j++)
				{
					if(srcFiles.get(j).contains(lstofdestFiles[i].getName()))
					{
						destFiles.add(lstofdestFiles[i].getName());
						break;
					}
				}
			}
		}
		//System.out.println(destFiles);
		
		return 	destFiles;
	}

}
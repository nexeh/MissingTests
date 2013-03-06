package lmig.util.missingtests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissingTests {

	private String sourceBaseDir = "";
	private String testBaseDir = "";

	private List missingTests = new ArrayList();
	private List extentions = new ArrayList();
	private List filesToIgnore = new ArrayList();
	
	int sourceCount = 0;

	public void find(String srcDir, String testDir, String ext, String ignore) {
		sourceBaseDir = srcDir;
		testBaseDir = testDir;
		
		extentions = Arrays.asList(ext.split(","));
		filesToIgnore = Arrays.asList(ignore.split(","));
		
		walk(sourceBaseDir);
		printResults();
	}

	private void walk(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list != null) {
			for (File f : list) {
				if (f.isDirectory()) {
					if(!isOnIgnoreList(f.getAbsolutePath().toString())) {
						walk(f.getAbsolutePath());
					}
				} else {
					if (!f.getName().toString().startsWith(".") && !isOnIgnoreList(f.getName().toString()) && checkFileExtention(f.getName().toString())) {
						sourceCount++;
						checkForTest(f);
					}
				}
			}
		} else {
			System.out.println("No source files found");
		}
	}
	
	private boolean isOnIgnoreList(String fileName) {
		boolean isOnIgnoreList = false;
		
		for (int i = 0; i < filesToIgnore.size(); i++) {
			String file = (String) filesToIgnore.get(i);
			if(fileName.contains(file)) {
				//System.out.println("IGNORE: file: " + file + " fileName: " + fileName);
				isOnIgnoreList = true;
			}
		}
		
		return isOnIgnoreList;
	}

	private void checkForTest(File sourceFile) {

		boolean isTestFound = false;

		// Get the folder structure inside the source root
		String fileLocation = sourceFile.getAbsoluteFile().toString();
		fileLocation = fileLocation.substring(sourceBaseDir.length());


		// Store the file name that we are looking for, without the extension
		String fileName = sourceFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf("."));

		// Strip out the file name and add the testing root
		fileLocation = fileLocation.substring(0, fileLocation.lastIndexOf(System.getProperty("file.separator")));
		fileLocation = testBaseDir + fileLocation;

		// Open the folder the test should live in
		File root = new File(fileLocation);
		File[] list = root.listFiles();

		if (list != null) {

			// Check each file to see if it is our test.
			for (File testFile : list) {
				if (testFile.isFile()) {
					
					if (checkFileName(testFile.getName(), fileName)) {
						isTestFound = true;
						break;
					}
				}
			}
		}

		// if we havn't found a test then add it to the list
		if (!isTestFound) {
			missingTests.add(sourceFile);
		}
	}
	
	private boolean checkFileName(String testFileName, String srcFileName) {
		boolean isExpectedTestName = false;
		
		if(testFileName.toLowerCase().startsWith(srcFileName.toLowerCase())) {
			
			testFileName = testFileName.substring(srcFileName.length());
			testFileName = testFileName.substring(0, testFileName.lastIndexOf("."));
			
			if (testFileName.equalsIgnoreCase("Test") || testFileName.equalsIgnoreCase("Tests")) {
				isExpectedTestName = true;
			} 
			else if (testFileName.equalsIgnoreCase("UnitTest") || testFileName.equalsIgnoreCase("UnitTests")) {
				isExpectedTestName = true;
			}
			else if (testFileName.equalsIgnoreCase("AcceptanceTest") || testFileName.equalsIgnoreCase("AcceptanceTests")) {
				isExpectedTestName = true;
			}
		}
		
		return isExpectedTestName;
	}

	private void printResults() {
		System.out.println(missingTests.size() + " of " + sourceCount + " tests missing");
		System.out.println("=============================");
		for (int i = 0; i < missingTests.size(); i++) {
			File missingTest = (File) missingTests.get(i);
			System.out.println(missingTest.getAbsolutePath());
		}
	}
	
	private boolean checkFileExtention(String strFileName) {
		boolean isCorrectExtention = false;
		
		String extention = strFileName.substring(strFileName.lastIndexOf(".") + 1);
		
		for(int i = 0; i < extentions.size(); i++) {
			if(extentions.get(i).toString().equals(extention)) {
				isCorrectExtention = true;
			}
		}
		return isCorrectExtention;
	}
	
	private void processArgs(String ext, List list) {
		list = Arrays.asList(ext.split(","));
	}

}
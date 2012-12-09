package lmig.util.missingtests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissingTests {

	private String sourceBaseDir = "";
	private String testBaseDir = "";

	private List missingTests = new ArrayList();
	private List extentions = new ArrayList();

	public void find(String srcDir, String testDir, String extentions) {
		sourceBaseDir = srcDir;
		testBaseDir = testDir;
		
		processExtentions(extentions);
		
		walk(sourceBaseDir);
		printResults();
	}

	private void walk(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list != null) {
			for (File f : list) {
				if (f.isDirectory()) {
					walk(f.getAbsolutePath());
				} else {
					if (!f.getName().toString().startsWith(".") && checkFileExtention(f.getName().toString())) {
						checkForTest(f);
					}
				}
			}
		} else {
			System.out.println("No source files found");
		}
	}

	private void checkForTest(File sourceFile) {

		boolean isTestFound = false;

		// Get the folder structure inside the source root
		String fileLocation = sourceFile.getAbsoluteFile().toString();
		fileLocation = fileLocation.substring(sourceBaseDir.length());

		// Store the file name that we are looking for
		String fileName = sourceFile.getName();
		fileLocation.substring(fileLocation.lastIndexOf("/"),fileLocation.length());
		fileName = fileName.substring(0, fileName.lastIndexOf("."));

		// Strip out the file name and add the testing root
		fileLocation = fileLocation.substring(0, fileLocation.lastIndexOf("/"));
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
		System.out.println(missingTests.size() + " tests missing");
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
	
	private void processExtentions(String ext) {
		extentions = Arrays.asList(ext.split(","));
	}

}
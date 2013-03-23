package lmig.util.plot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class PlotData {

	private boolean fileFound = false;
	private String[] strHeaders;
	private String[] strValues;

	private String defaultHeaders = "Tests,Missing Files,Failures,Errors";
	private String defaultValues = "0,0,0,0";

	public void updateValue(String fileName, String key, String value) {
		loadCSV(fileName);

		// if the file was not found then create it
		if (!fileFound) {
			createDefaultValues(fileName);
		}

		updateCellValue(key, value);

		writeFile(fileName);
	}

	private void loadCSV(String fileName) {
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int lineNumber = 1;
			while ((strLine = br.readLine()) != null) {

				// first line is our headers
				if (lineNumber == 1) {
					strHeaders = strLine.split(",");
				}

				// second line is our values
				else if (lineNumber == 2) {
					strValues = strLine.split(",");
				}

				// we only expect 2 lines and ignore the rest
				lineNumber++;
			}

			// at this point its safe to say we found the file
			fileFound = true;

			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void createDefaultValues(String fileName) {
		strHeaders = defaultHeaders.split(",");
		strValues = defaultValues.split(",");
	}

	private void updateCellValue(String key, String value) {

		boolean headerFound = false;
		int index = -1;

		for (int i = 0; i < strHeaders.length; i++) {
			if (key.equals(strHeaders[i])) {
				headerFound = true;
				index = i;
				break;
			}
		}

		if (!headerFound) {
			System.out.println("Field Not found");
		} else {
			strValues[index] = value;
		}
	}

	private void writeFile(String fileName) {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(getCSVFromArray(strHeaders)+"\n");
			out.write(getCSVFromArray(strValues)+"\n");
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private String getCSVFromArray(String[] strArray) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < strArray.length; i++) {
			builder.append(strArray[i] +",");
		}
		
		return builder.toString();
	}

}

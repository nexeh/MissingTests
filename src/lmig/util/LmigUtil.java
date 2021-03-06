package lmig.util;

import lmig.util.missingtests.MissingTests;
import lmig.util.plot.PlotData;

public class LmigUtil {
	public static void main(String[] args) {
		
		if(args[0].equalsIgnoreCase("missingtests")) {
			MissingTests mt = new MissingTests();
			mt.find(args[1], args[2], args[3], args[4]);
		}
		
		else if(args[0].equalsIgnoreCase("plot")) {
			PlotData pt = new PlotData();
			pt.updateValue(args[1], args[2], args[3]);
		}
	}
}
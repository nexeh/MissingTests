package lmig.util;

import lmig.util.missingtests.MissingTests;

public class LmigUtil {
	public static void main(String[] args) {
		System.out.println(args[0]);
		
		if(args[0].equalsIgnoreCase("missingtests")) {
			MissingTests mt = new MissingTests();
			mt.find(args[1], args[2], args[3]);
		}
	}
}
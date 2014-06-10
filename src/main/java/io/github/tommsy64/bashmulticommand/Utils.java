package io.github.tommsy64.bashmulticommand;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String[] removeFirst(String[] strings) {
		String[] newStrings = new String[strings.length];
		for (int i = 0; i + 1 < strings.length; i++) {
			newStrings[i] = strings[i + 1];
		}
		return newStrings;
	}

	public static String[] remove(String str, String[] array) {
		List<String> myList = new ArrayList<String>();

		int x = 0;
		for (int i = 0; i < array.length; i++) {
			if (!array[i].equalsIgnoreCase(str))
				myList.add(array[i + x]);
		}
		return myList.toArray(new String[0]);
	}
}

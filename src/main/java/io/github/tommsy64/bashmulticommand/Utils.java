package io.github.tommsy64.bashmulticommand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String[] removeFirst(String[] array) {
		List<String> newStrings = new ArrayList<String>();
		for (int i = 0; i + 1 < array.length; i++) {
			newStrings.add(array[i + 1]);
		}
		return newStrings.toArray(new String[0]);
	}

	public static String[] remove(String str, String[] array) {
		List<String> newArray = new ArrayList<String>();

		int x = 0;
		for (int i = 0; i < array.length; i++) {
			if (!array[i].equalsIgnoreCase(str))
				newArray.add(array[i + x]);
		}
		return newArray.toArray(new String[0]);
	}

	public static void createDirectory(String directoryPath) {
		File f = new File(directoryPath);
		if (f.exists() && f.isDirectory())
			return;
		else {
			f.delete();
			f.mkdirs();
		}
	}

	public static void createDirectoryFromFile(String filePath) {
		createDirectory(new File(filePath).getParent());
	}
}

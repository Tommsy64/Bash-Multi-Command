package io.github.tommsy64.bashmulticommand.config;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.Utils;
import io.github.tommsy64.bashmulticommand.locale.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SLAPI = Saving/Loading API API for Saving and Loading Objects. You can use
 * this API in your projects, but please credit the original author of it.
 * 
 * @author Tomsik68<tomsik68@gmail.com>
 * @author Tommsy64
 */
public class SLAPI {
	private static <T extends Object> void save(T obj, String path)
			throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				path));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	private static <T extends Object> T load(String path) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		@SuppressWarnings("unchecked")
		T result = (T) ois.readObject();
		ois.close();
		return result;
	}

	public static <T extends Object> void saveObject(T obj, String filePath) {
		try {
			SLAPI.save(obj, filePath);
		} catch (FileNotFoundException e) {
			Utils.createDirectoryFromFile(filePath);
			try {
				SLAPI.save(obj, filePath);
			} catch (Exception e1) {
				BashMultiCommand.plugin.getLogger()
						.severe(Strings.replaceAll(
								BashMultiCommand.plugin.strings
										.get("errorSavingFile"), "%filepath%",
								filePath)[0]);
				e1.printStackTrace();
			}
		} catch (Exception e) {
			BashMultiCommand.plugin
					.getLogger()
					.severe(Strings.replaceAll(BashMultiCommand.plugin.strings
							.get("errorSavingFile"), "%filepath%", filePath)[0]);
			e.printStackTrace();
		}
	}

	public static <T extends Object> T loadFile(String filePath) {
		Utils.createDirectoryFromFile(filePath);
		if (!new File(filePath).exists())
			return null;

		try {
			return SLAPI.load(filePath);
		} catch (Exception e) {
			BashMultiCommand.plugin
					.getLogger()
					.severe(Strings.replaceAll(BashMultiCommand.plugin.strings
							.get("errorLoadingFile"), "%filepath%", filePath)[0]);
			e.printStackTrace();
		}
		return null;
	}
}
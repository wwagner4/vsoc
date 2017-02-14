package vsoc.util;

import java.io.File;

public class TestUtil {

	public static File tmp(String fileName) {
		String propName = "java.io.tmpdir";
		String dirName = System.getProperty(propName);
		if (dirName == null) {
			throw new IllegalStateException("There is something wrong with the system property '" + propName + "'");
		}
		File d = new File(dirName);
		return new File(d, fileName);
	}

}

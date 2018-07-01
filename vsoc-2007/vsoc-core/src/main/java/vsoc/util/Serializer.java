package vsoc.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Serializer {

	private static Serializer current = null;

	private Serializer() {
		super();
	}

	public static Serializer current() {
		if (current == null) {
			current = new Serializer();
		}
		return current;
	}

	public void serialize(Serializable p, OutputStream out) {
		SerializationUtils.serialize(p, out);
	}

	public void serialize(Serializable p, File file) throws IOException {
		OutputStream out = new FileOutputStream(file);
		serialize(p, out);
	}

	public Object deserializeFromScheduled(String prefix) {
		try {
			File sDir = getSchedulerDir();
			if (!sDir.exists()) {
				return null;
			}
			String[] list = sDir.list();
			SortedSet<String> names = new TreeSet<>();
			for (int i = 0; i < list.length; i++) {
				if (list[i].startsWith(prefix)) {
					names.add(list[i]);
				}
			}
			if (names.isEmpty()) {
				return null;
			}
			String name = (String) names.last();
			return deserialize(new File(sDir, name));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public Object deserialize(File file) throws IOException {
		Object re = null;
		if (file.exists()) {
			InputStream in = new FileInputStream(file);
			re = deserialize(in);
		} else {
			throw new IOException("File " + file + " does not exist");
		}
		return re;
	}

	public Object deserialize(InputStream in) {
		return SerializationUtils.deserialize(in);
	}

	public void startScheduledSerialization(String prefix, int seconds, Serializable obj) {
		Scheduler sched = new Scheduler(prefix, seconds, obj);
		Thread thread = new Thread(sched, "VsocScheduler");
		thread.start();
	}

	File getSchedulerDir() {
		return new File("scheduler");
	}

	private class Scheduler implements Runnable {

		private int id = 0;

		private String tstamp = createTstamp();

		private String prefix;

		private Serializable obj;

		private int seconds;

		public Scheduler(String prefix, int seconds, Serializable obj) {
			super();
			this.prefix = prefix;
			this.seconds = seconds;
			this.obj = obj;
		}

		public void run() {
			int errorCount = 0;
			while (errorCount < 10) {
				pause();
				try {
					File file = createFile();
					Serializer.this.serialize(this.obj, file);
					this.id++;
				} catch (Exception e) {
					errorCount++;
					throw new IllegalStateException("Could not serialize " + this.obj + ". Reason: " + e.getMessage(), e);
				}
			}
		}

		private String createTstamp() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
			Date now = new Date(System.currentTimeMillis());
			return sdf.format(now);
		}

		private File createFile() {
			File sDir = getSchedulerDir();
			if (!sDir.exists()) {
				sDir.mkdirs();
			}
			String fName = this.prefix + "_" + this.tstamp + "_" + this.id + ".ser";
			return new File(sDir, fName);
		}

		private synchronized void pause() {
			try {
				wait(this.seconds * 1000);
			} catch (InterruptedException ex) {
				// continue
			}
		}

	}

}
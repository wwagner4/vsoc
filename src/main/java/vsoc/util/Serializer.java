package vsoc.util;

/**
 * Static Methods to be used for Serializable Objects.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;

public class Serializer {

    private static Serializer current = null;

    private static int schedulerCount = 0;

    static Logger log = Logger.getLogger(Serializer.class);

    private Serializer() {
        super();
    }

    public static Serializer current() {
        if (current == null) {
            current = new Serializer();
        }
        return current;
    }

    public void serialize(Serializable p, String fileName) throws IOException {
        OutputStream out = new FileOutputStream(fileName);
        serialize(p, out);
    }

    public void serialize(Serializable p, OutputStream out) {
        SerializationUtils.serialize(p, out);
    }

    public void serialize(Serializable p, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        serialize(p, out);
    }

    public Object deserializeFromScheduled(String prefix)  {
        try {
            File sDir = getSchedulerDir();
            if (!sDir.exists()) {
                throw new FileNotFoundException("The scheduler directory " + sDir.getAbsolutePath()
                        + " does not exist.");
            }
            String[] list = sDir.list();
            SortedSet<String> names = new TreeSet<>();
            for (int i = 0; i < list.length; i++) {
                if (list[i].startsWith(prefix)) {
                    names.add(list[i]);
                }
            }
            if (names.isEmpty()) {
                throw new FileNotFoundException("No file with prefix " + prefix
                        + " found in " + sDir.getAbsolutePath() + ".");
            }
            String name = (String) names.last();
            log.info("Deserializing from " + name);
            return deserialize(new File(sDir, name));
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public Object deserialize(String fileName) throws IOException {
        File file = new File(fileName);
        Object re = null;
        if (file.exists()) {
            InputStream in = new FileInputStream(file);
            re = deserialize(in);
        } else {
            URL url = getClass().getClassLoader().getResource(fileName);
            if (url != null) {
                InputStream in = url.openStream();
                re = deserialize(in);
            } else {
                log.info("file or url " + fileName + " not found");
            }
        }
        return re;
    }

    public Object deserialize(File file) throws IOException {
        Object re = null;
        if (file.exists()) {
            InputStream in = new FileInputStream(file);
            re = deserialize(in);
        }
        return re;
    }

    public Object deserialize(InputStream in) {
        return SerializationUtils.deserialize(in);
    }

    public void startScheduledSerialization(String prefix, int seconds,
            Serializable obj) {
        Scheduler sched = new Scheduler(prefix, seconds, obj);
        Thread thread = new Thread(sched, "Scheduler-" + schedulerCount);
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
            log.info("Started scheduling " + this.obj + ". Interval "
                    + this.seconds + " s.");
            int errorCount = 0;
            while (errorCount < 10) {
                pause();
                try {
                    File file = createFile();
                    Serializer.this.serialize(this.obj, file);
                    log.info("Serialized " + this.obj + " to "
                            + file.getAbsolutePath() + ".");
                    this.id++;
                } catch (Exception e) {
                    errorCount++;
                    String msg = "Could not serialize " + this.obj
                            + ". Reason: " + e.getMessage();
                    log.warn(msg);
                    if (log.isDebugEnabled()) {
                        log.warn(msg, e);
                    } else {
                        log.warn(msg);
                    }
                }
            }
            log.error("Stopped scheduling because of too much errors.");
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
            String fName = this.prefix + "_" + this.tstamp + "_" + this.id
                    + ".ser";
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
package backtype.hadoop;

import java.io.File;

import java.io.IOException;
import java.io.FileNotFoundException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;


import org.apache.hadoop.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: sritchie
 * Date: 11/15/11
 * Time: 1:08 AM
 */

public class ElasticUtil {
    /**
     Recursively converts an arbitrary object into the appropriate writable. Please enlighten me if there is an existing
     method for doing this.
     */
    public static Writable toWritable(Object thing) {
        if (thing instanceof String) {
            return new Text((String)thing);
        } else if (thing instanceof Long) {
            return new LongWritable((Long)thing);
        } else if (thing instanceof Integer) {
            return new IntWritable((Integer)thing);
        } else if (thing instanceof Double) {
            return new DoubleWritable((Double)thing);
        } else if (thing instanceof Float) {
            return new FloatWritable((Float)thing);
        } else if (thing instanceof Boolean) {
            return new BooleanWritable((Boolean)thing);
        } else if (thing instanceof Map) {
            MapWritable result = new MapWritable();
            for (Map.Entry<String,Object> entry : ((Map<String,Object>)thing).entrySet()) {
                result.put(new Text(entry.getKey().toString()), toWritable(entry.getValue()));
            }
            return result;
        } else if (thing instanceof List) {
            if (((List)thing).size() > 0) {
                Object first = ((List)thing).get(0);
                Writable[] listOfThings = new Writable[((List)thing).size()];
                for (int i = 0; i < listOfThings.length; i++) {
                    listOfThings[i] = toWritable(((List)thing).get(i));
                }
                return new ArrayWritable(toWritable(first).getClass(), listOfThings);
            }
        }
        return NullWritable.get();
    }

        /**
       Upload a local file to the cluster
     */
    public static void uploadLocalFile(Path localsrc, Path hdfsdest, Configuration conf) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(hdfsdest) && fs.getFileStatus(hdfsdest).isDir()) {
            fs.delete(hdfsdest, true);
        }
        fs.copyFromLocalFile(false, true, localsrc, hdfsdest);
    }


    /**
       Upload a local file to the cluster, if it's newer or nonexistent
     */
    public static void uploadLocalFileIfChanged(Path localsrc, Path hdfsdest, Configuration conf) throws IOException {
        long l_time = new File(localsrc.toUri()).lastModified();
        try {
            long h_time = FileSystem.get(conf).getFileStatus(hdfsdest).getModificationTime();
            if ( l_time > h_time ) {
                uploadLocalFile(localsrc, hdfsdest, conf);
            }
        }
        catch (FileNotFoundException e) {
            uploadLocalFile(localsrc, hdfsdest, conf);
        }
    }


    /**
       Fetches a file with the basename specified from the distributed cache. Returns null if no file is found
     */
    public static String fetchFileFromCache(String basename, Configuration conf) throws IOException {
        Path[] cacheFiles = DistributedCache.getLocalCacheFiles(conf);
        if (cacheFiles != null && cacheFiles.length > 0) {
            for (Path cacheFile : cacheFiles) {
                if (cacheFile.getName().equals(basename)) {
                    return cacheFile.toString();
                }
            }
        }
        return null;
    }

    /**
       Fetches a file with the basename specified from the distributed cache. Returns null if no file is found
     */
    public static String fetchArchiveFromCache(String basename, Configuration conf) throws IOException {
        Path[] cacheArchives = DistributedCache.getLocalCacheArchives(conf);
        if (cacheArchives != null && cacheArchives.length > 0) {
            for (Path cacheArchive : cacheArchives) {
                if (cacheArchive.getName().equals(basename)) {
                    return cacheArchive.toString();
                }
            }
        }
        return null;
    }

    /**
       Takes a path on the hdfs and ships it in the distributed cache if it is not already in the distributed cache
     */
    public static void shipFileIfNotShipped(Path hdfsPath, Configuration conf) throws IOException {
        if (fetchFileFromCache(hdfsPath.getName(), conf) == null) {
            try {
                DistributedCache.addCacheFile(hdfsPath.toUri(), conf);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

        /**
       Takes a path on the hdfs and ships it in the distributed cache if it is not already in the distributed cache
     */
    public static void shipArchiveIfNotShipped(Path hdfsPath, Configuration conf) throws IOException {
        if (fetchArchiveFromCache(hdfsPath.getName(), conf) == null) {
            try {
                DistributedCache.addCacheArchive(hdfsPath.toUri(), conf);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
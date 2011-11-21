package backtype.hadoop;

import cascading.flow.hadoop.HadoopUtil;
import cascading.tap.Tap;
import cascading.tap.TapException;
import cascading.tap.hadoop.TapCollector;
import cascading.tap.hadoop.TapIterator;
import cascading.tuple.TupleEntryCollector;
import cascading.tuple.TupleEntryIterator;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: sritchie
 * Date: 11/15/11
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ElasticTap extends Tap {
    String dbDriver;
    String dbUrl;
    String username;
    String pwd;
    String tableName;
    String pkColumn;
    String[] columnNames;
    int numChunks;

    private String _accessKeyId;
    private String _secretAccessKey;
    private String _baseDomainName;

    public static final String SCHEME = "es";

    private URI getURI() {
        try {
            return new URI(SCHEME, "//" + _accessKeyId + "/" + _baseDomainName, null);
        } catch (URISyntaxException exception) {
            throw new TapException("unable to create uri", exception);
        }
    }

    @Override public Path getPath() {
        return new Path(getURI().toString());
    }

    @Override public TupleEntryIterator openForRead(JobConf jobConf) throws IOException {
        Map<Object, Object> properties = HadoopUtil.createProperties(jobConf);

        properties.remove( "mapred.input.dir" );

        jobConf = HadoopUtil.createJobConf( properties, null );

        return new TupleEntryIterator( getSourceFields(), new TapIterator( this, jobConf ) );
    }

    @Override public TupleEntryCollector openForWrite(JobConf jobConf) throws IOException {
        return new TapCollector( this, jobConf );
    }

    @Override public boolean makeDirs(JobConf jobConf) throws IOException {
        return true;
    }

    @Override public boolean deletePath(JobConf jobConf) throws IOException {
        return false;
    }

    @Override public boolean pathExists(JobConf jobConf) throws IOException {
        return true;
    }

    @Override public long getPathModified(JobConf jobConf) throws IOException {
        return System.currentTimeMillis();
    }
}

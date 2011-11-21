package backtype.hadoop;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputSplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ElasticSearchSplit implements InputSplit {

    private String queryString;
    private long from;
    private long size;

    public ElasticSearchSplit() {}

    public ElasticSearchSplit(String queryString, long from, long size) {
        this.queryString = queryString;
        this.from = from;
        this.size = size;
    }

    public String getQueryString() {
        return queryString;
    }

    public long getFrom() {
        return from;
    }

    public long getSize() {
        return size;
    }

    public String[] getLocations() throws IOException {
        return new String[]{};
    }

    public long getLength() throws IOException {
        return 0;
    }

    public void readFields(DataInput in) throws IOException {
        queryString = Text.readString(in);
        from = in.readLong();
        size = in.readLong();
    }

    public void write(DataOutput out) throws IOException {
        Text.writeString(out, queryString);
        out.writeLong(from);
        out.writeLong(size);
    }
}
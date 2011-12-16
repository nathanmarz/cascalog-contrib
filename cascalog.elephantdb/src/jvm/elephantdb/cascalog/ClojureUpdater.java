package elephantdb.cascalog;

import elephantdb.hadoop.ElephantUpdater;
import elephantdb.persistence.LocalPersistence;
import java.io.IOException;
import cascalog.Util;
import clojure.lang.IFn;

public class ClojureUpdater implements ElephantUpdater {
    private Object[] spec;
    private transient IFn fn = null;

    public ClojureUpdater(Object[] spec) {
        this.spec = spec;
    }

    public void updateElephant(LocalPersistence lp, byte[] newKey,
                               byte[] newVal) throws IOException {
        if(this.fn==null) {
            this.fn = Util.bootFn(this.spec);
        }
        try {
            this.fn.invoke(lp, newKey, newVal);
        } catch(IOException ioe) {
            throw ioe;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}

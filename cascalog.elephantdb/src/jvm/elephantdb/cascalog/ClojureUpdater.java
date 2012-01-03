package elephantdb.cascalog;

import elephantdb.index.Indexer;
import elephantdb.document.Document;
import elephantdb.persistence.Persistence;
import java.io.IOException;
import cascalog.Util;
import clojure.lang.IFn;

public class ClojureIndexer implements Indexer {
    private Object[] spec;
    private transient IFn fn = null;

    public ClojureIndexer(Object[] spec) {
        this.spec = spec;
    }

    public void index(Persistence lp, Document doc) throws IOException {
        if(this.fn==null)
            this.fn = Util.bootFn(this.spec);
        
        try {
            this.fn.invoke(lp, doc);
        } catch(IOException ioe) {
            throw ioe;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}

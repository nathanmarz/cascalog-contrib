(ns elephantdb.cascalog.impl
  (:use cascalog.api)
  (:import [cascalog.ops IdentityBuffer]
           [elephantdb.hadoop Common]
           [elephantdb Utils]
           [elephantdb.persistence LocalPersistenceFactory]
           [elephantdb.cascading ElephantDBTap ElephantDBTap$Args]
           [java.util ArrayList HashMap]
           [org.apache.hadoop.io BytesWritable]))

(defn- serializable-persistence-options
  [options]
  (HashMap. options))

(defn- serializable-list [l]
  (when l (ArrayList. l)))

(defn convert-clj-args
  [a {:keys [persistence-options tmp-dirs updater timeout-ms deserializer version]}]
  (let [ret (ElephantDBTap$Args.)]
    (set! (.persistenceOptions ret) (HashMap. persistence-options))
    (set! (.tmpDirs ret)            (serializable-list tmp-dirs))
    (set! (.updater ret) updater)
    (when timeout-ms (set! (.timeoutMs ret) timeout-ms))
    (set! (.deserializer ret) deserializer)
    (set! (.version ret) version)
    ret))

(defmapop [shardify [^Integer num-shards]] [k]
  (Utils/keyShard (Common/serializeElephantVal k)
                  num-shards))

;; TODO: Replace this serialization method with the kryo ObjectBuffer.
(defmapop [mk-sortable-key [^LocalPersistenceFactory fact]]
  "TODO: We can't just use Common/serializeElephantVal; we need to use
  a serializer from the LocalPersistenceFactory."
  [k]
  (BytesWritable.
   (.getSortableKey
    (.getKeySorter fact)
    (Common/serializeElephantVal k))))

(defn elephant<-
  "Keys are serialized for you. You have to go ahead and serialize
  values. TODO: Change this."
  [elephant-tap kv-src]
  (let [spec        (.getSpec elephant-tap)
        lp-factory  (.getLPFactory spec)
        shard-count (.getNumShards spec)]
    (<- [!shard !key !value]
        (kv-src !keyraw !valueraw)
        (mk-sortable-key [lp-factory] !keyraw :> !sort-key)
        (shardify [shard-count] !keyraw :> !shard)
        (:sort !sort-key)
        ((IdentityBuffer.) !keyraw !valueraw :> !key !value))))

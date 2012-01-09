(ns cascalog.elephantdb.impl
  (:use cascalog.api)
  (:import [cascalog.ops IdentityBuffer]
           [elephantdb Utils]
           [elephantdb.partition ShardingScheme]
           [elephantdb.serialize Serializer]
           [org.apache.hadoop.io BytesWritable]))

(defmapop [shard [^ShardingScheme scheme shard-count]]
  "Returns the shard to which the supplied shard-key should be
  routed."
  [shard-key]
  (.shardIndex scheme shard-key shard-count))

;; TODO: Replace this serialization method with the kryo ObjectBuffer.
(defmapop [mk-sortable-key [^Serializer serializer]]
  [shard-key]
  (BytesWritable.
   (.serialize serializer shard-key)))

(defn elephant<-
  [elephant-tap kv-src]
  (let [spec        (.getSpec elephant-tap)
        scheme      (.getShardScheme spec)
        shard-count (.getNumShards spec)
        serializer  (Utils/makeSerializer spec)]
    (<- [!shard !key !value]
        (kv-src !keyraw !valueraw)
        (shard [scheme shard-count] !keyraw :> !shard)
        (mk-sortable-key [serializer] !keyraw :> !sort-key)
        (:sort !sort-key)
        ((IdentityBuffer.) !keyraw !valueraw :> !key !value))))

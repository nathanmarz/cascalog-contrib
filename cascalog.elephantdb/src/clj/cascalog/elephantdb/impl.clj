(ns cascalog.elephantdb.impl
  (:use cascalog.api)
  (:import [cascalog.ops IdentityBuffer]
           [elephantdb Utils]
           [elephantdb.partition ShardingScheme]
           [elephantdb.serialize Serializer]
           [org.apache.hadoop.io BytesWritable]))

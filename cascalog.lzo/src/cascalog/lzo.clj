(ns cascalog.lzo
  (:use cascalog.api)
  (:require [cascalog.lzo.impl :as lzo]
            [clojure.string :as s]
            [cascalog.io :as io])
  (:import cascading.tuple.Fields))

(defn hfs-lzo-textline [path & opts]
  (let [scheme (->> (:outfields (apply array-map opts) Fields/ALL)
                    (lzo/text-line ["line"]))]
    (apply hfs-tap scheme path opts)))

(defn lfs-lzo-textline
  [path & opts]
  (let [scheme (->> (:outfields (apply array-map opts) Fields/ALL)
                    (lzo/text-line ["line"]))]
    (apply lfs-tap scheme path opts)))

(defn hfs-lzo-thrift
  [path klass & opts]
  (let [scheme (lzo/thrift-b64-line klass)]
    (apply hfs-tap scheme path opts)))

(defn hfs-lzo-protobuf
  [path klass & opts]
  (let [scheme (lzo/proto-b64-line klass)]
    (apply hfs-tap scheme path opts)))

(def lzo-settings
  {"mapred.map.output.compression.codec" "com.hadoop.compression.lzo.LzoCodec"
   "io.compression.codec.lzo.class" "com.hadoop.compression.lzo.LzoCodec"
   "io.compression.codecs"
   (s/join "," ["org.apache.hadoop.io.compress.GzipCodec"
                "org.apache.hadoop.io.compress.DefaultCodec"
                "org.apache.hadoop.io.compress.BZip2Codec"
                "com.hadoop.compression.lzo.LzoCodec"
                "com.hadoop.compression.lzo.LzopCodec"])})

(defn test-read []
  (io/with-fs-tmp [_ tmp]
    (?- (hfs-lzo-textline tmp) [["a line of text!"]])
    (with-job-conf lzo-settings
      (= [["a line of text!"]]
         (first (??- (hfs-lzo-textline tmp)))))))

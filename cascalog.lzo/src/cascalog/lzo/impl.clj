(ns cascalog.lzo.impl
  (:require [cascalog.workflow :as w])
  (:import [com.twitter.elephantbird.cascading2.scheme
            LzoTextLine LzoTextDelimited LzoThriftB64LineScheme
            LzoProtobufB64LineScheme]))

(defn text-line
  ([] (LzoTextLine.))
  ([field-names]
     (text-line field-names field-names))
  ([source-fields sink-fields]
     (LzoTextLine. (w/fields source-fields)
                   (w/fields sink-fields))))

(defn delimited [field-names klasses]
  (let [klasses (when klasses (into-array klasses))]
    (-> (w/fields field-names)
        (LzoTextDelimited. "\t" klasses))))

(defn thrift-b64-line [klass]
  (LzoThriftB64LineScheme. klass))

(defn proto-b64-line [klass]
  (LzoProtobufB64LineScheme. klass))

(ns elephantdb.cascalog.integration-test
  (:use clojure.test
        elephantdb.cascalog.core
        [cascalog api testing])
  (:require [elephantdb.testing :as t]
            [elephantdb.config :as config]
            [cascalog.ops :as c])
  (:import [org.apache.hadoop.io BytesWritable]
           [elephantdb.persistence JavaBerkDB]
           [elephantdb Utils]))

(defn mk-writable-pairs [pairs]
  (map (fn [[k v]]
         [(BytesWritable. k)
          (BytesWritable. v)])
       pairs))

(defn merge-updater
  [lp k v]
  (let [ov (.get lp k)
        nv (if ov
             (byte-array (concat (seq ov) (seq v)))
             v)]
    (.add lp k nv)))

(t/def-fs-test test-all [fs tmp]
  (let [data [[(t/barr 0) (t/barr 1)]
              [(t/barr 1) (t/barr 2 2)]
              [(t/barr 3) (t/barr 3 3 4)]
              [(t/barr 1 1) (t/barr 2 2)]
              [(t/barr 2 2 2) (t/barr 3 3 3)]]
        data2 [[(t/barr 0) (t/barr 10)]
               [(t/barr 3) (t/barr 3)]
               [(t/barr 10) (t/barr 10)]]
        data3 [[(t/barr 0) (t/barr 1 10)]
               [(t/barr 1) (t/barr 2 2)]
               [(t/barr 3) (t/barr 3 3 4 3)]
               [(t/barr 1 1) (t/barr 2 2)]
               [(t/barr 2 2 2) (t/barr 3 3 3)]
               [(t/barr 10) (t/barr 10)]]]
    (with-tmp-sources [source (mk-writable-pairs data)
                       source2 (mk-writable-pairs data2)]
      (?- (elephant-tap tmp :domain-spec {:num-shards 4
                                          :persistence-factory (JavaBerkDB.)})
          source)
      (t/with-single-service-handler [handler {"domain" tmp}]
        (t/check-domain "domain" handler data))
      (?- (elephant-tap tmp :args {:updater (mk-clj-updater #'merge-updater)})
          source2)
      (t/with-single-service-handler [handler {"domain" tmp}]
        (t/check-domain "domain" handler data3)))))

(defn test-to-int [bw]
  (int (first (.get bw))))

(deftest test-source
  (let [pairs [[(t/barr 0) (t/barr 1)]
               [(t/barr 1) (t/barr 2)]
               [(t/barr 2) (t/barr 3)]
               [(t/barr 3) (t/barr 0)]
               [(t/barr 4) (t/barr 0)]
               [(t/barr 5) (t/barr 1)]
               [(t/barr 6) (t/barr 3)]
               [(t/barr 7) (t/barr 9)]
               [(t/barr 8) (t/barr 99)]
               [(t/barr 9) (t/barr 4)]
               [(t/barr 10) (t/barr 3)]]]
    (t/with-sharded-domain [dpath
                            {:num-shards 3
                             :persistence-factory (JavaBerkDB.)}
                            pairs]
      (test?<- [[1 2] [2 1] [3 3] [0 2] [9 1] [99 1] [4 1]]
               [?intval ?count]
               ((elephant-tap dpath) _ ?value)
               (test-to-int ?value :> ?intval)
               (c/count ?count))
      (test?<- [[0 1] [1 1] [2 1] [3 1] [4 1] [5 1] [6 1] [7 1] [8 1] [9 1] [10 1]]
               [?intval ?count]
               ((elephant-tap dpath) ?key _)
               (test-to-int ?key :> ?intval)
               (c/count ?count)))))

;; TODO: test read specific version using a deserializer

(deftest test-deserializer
  (let [pairs [[(Utils/serializeString "aaa") (t/barr 1)]]]
    (t/with-sharded-domain [dpath
                            {:num-shards 3
                             :persistence-factory (JavaBerkDB.)}
                            pairs]
      (test?<- [["aaa" 1]]
               [?key ?intval]
               ((elephant-tap dpath :args {:deserializer (string-deserializer)})
                ?key ?value)
               (test-to-int ?value :> ?intval)))))

(t/def-fs-test test-reshard [fs tmpout1 tmpout2]
  (let [pairs [[(t/barr 0) (t/barr 1)]
               [(t/barr 1) (t/barr 2)]
               [(t/barr 2) (t/barr 3)]
               [(t/barr 3) (t/barr 0)]
               [(t/barr 4) (t/barr 0)]
               [(t/barr 5) (t/barr 1)]]]
    (t/with-sharded-domain [dpath
                            {:num-shards 3
                             :persistence-factory (JavaBerkDB.)}
                            pairs]
      (reshard! dpath tmpout1 1)
      (is (= 1 (:num-shards (config/read-domain-spec fs tmpout1))))
      (t/with-single-service-handler [handler {"domain" tmpout1}]
        (t/check-domain "domain" handler pairs))
      (reshard! dpath tmpout2 2)
      (is (= 1 (:num-shards (config/read-domain-spec fs tmpout1))))
      (t/with-single-service-handler [handler {"domain" tmpout2}]
        (t/check-domain "domain" handler pairs)))))

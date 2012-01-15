(ns cascalog.elephantdb.keyval-test
  (:use cascalog.elephantdb.keyval
        cascalog.elephantdb.core
        cascalog.api
        ;; cascalog.testing ;; do we need this?
        [midje sweet cascalog])
  (:require [hadoop-util.test :as test]
            [elephantdb.test.common :as t]
            [elephantdb.common.config :as config]
            [cascalog.ops :as c])
  (:import [elephantdb.persistence JavaBerkDB KeyValPersistence]
           [elephantdb.partition HashModScheme]
           [elephantdb.document KeyValDocument]))

(defn mk-spec [num-shards]
  {:num-shards  num-shards
   :coordinator (JavaBerkDB.)
   :shard-scheme (HashModScheme.)})

(defn merge-indexer
  [^KeyValPersistence lp doc]
  (let [key     (.key doc)
        val     (.val doc)
        new-val (if-let [original (.get lp key)]
                  (concat original val)
                  val)]
    (->> (KeyValDocument. key new-val)
         (.index lp))))

(fact "test all!"
  (test/with-fs-tmp [fs tmp]
    (let [data [[0        1]
                [1        [2 2]]
                [3        [3 3 4]]
                [[1 1]    [2 2]]
                [[2 2 2]  [3 3 3]]]
          data2 [[0       10]
                 [3       3]
                 [10      10]]
          data3 [[0       [1 10]]
                 [1       [2 2]]
                 [3       [3 3 4 3]]
                 [[1 1]   [2 2]]
                 [[2 2 2] [3 3 3]]
                 [10      10]]]
      (with-tmp-sources [source  data
                         source2 data2]
        (?- (elephant-tap tmp :domain-spec (mk-spec 4))
            source)
        (t/with-single-service-handler [handler {"domain" tmp}]
          (t/check-domain "domain" handler data))
        (?- (elephant-tap tmp :args {:indexer (mk-indexer #'merge-indexer)})
            source2)
        (t/with-single-service-handler [handler {"domain" tmp}]
          (t/check-domain "domain" handler data3))))))

(defn test-to-int [bw]
  (int (first (.get bw))))

(fact "test-source"
  (let [pairs [[0 1]
               [1 2]
               [2 3]
               [3 0]
               [4 0]
               [5 1]
               [6 3]
               [7 9]
               [8 99]
               [9 4]
               [10 3]]]
    (t/with-sharded-domain [dpath
                            (mk-spec 3)
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

(fact "test-reshard"
  (test/with-fs-tmp [fs tmpout1 tmpout2]
    (let [pairs [[(t/barr 0) (t/barr 1)]
                 [(t/barr 1) (t/barr 2)]
                 [(t/barr 2) (t/barr 3)]
                 [(t/barr 3) (t/barr 0)]
                 [(t/barr 4) (t/barr 0)]
                 [(t/barr 5) (t/barr 1)]]]
      (t/with-sharded-domain [dpath
                              (mk-spec 3)
                              pairs]
        (reshard! dpath tmpout1 1)
        (:num-shards (config/read-domain-spec fs tmpout1)) => 1
        (t/with-single-service-handler [handler {"domain" tmpout1}]
          (t/check-domain "domain" handler pairs))
        (reshard! dpath tmpout2 2)
        (:num-shards (config/read-domain-spec fs tmpout1)) => 1
        (t/with-single-service-handler [handler {"domain" tmpout2}]
          (t/check-domain "domain" handler pairs))))))

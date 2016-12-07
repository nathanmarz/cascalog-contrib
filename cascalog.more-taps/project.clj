(defproject cascalog-more-taps "0.2.1"
  :description "Taps for Cascalog"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [cascalog "1.9.0"]
                 [cascading/cascading-hadoop "2.0.2-wip-324"]
                 [org.pingles/cascading.protobuf "0.0.1"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]
  :repositories {"conjars" "http://conjars.org/repo"})

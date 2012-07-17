(defproject cascalog-more-taps "0.3.0-SNAPSHOT"
  :description "Taps for Cascalog"
  :url "https://github.com/nathanmarz/cascalog-contrib"
  :min-lein-version "2.0.0"
  :dependencies [[org.pingles/cascading.protobuf "0.0.1"]] 
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.4.0"]
                                  [cascalog "1.10.0"]
                                  [cascading/cascading-hadoop "2.0.2-wip-324"]
                                  [org.apache.hadoop/hadoop-core "0.20.2-dev"]]}}
  :repositories {"conjars.org" "http://conjars.org/repo"}
  :dev-dependencies [[org.clojure/clojure "1.4.0"]
                     [cascalog "1.10.0"]
                     [cascading/cascading-hadoop "2.0.2-wip-324"]
                     [org.pingles/cascading.protobuf "0.0.1"]
                     [org.apache.hadoop/hadoop-core "0.20.2-dev"]])

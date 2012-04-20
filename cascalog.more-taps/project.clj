(defproject cascalog-more-taps "0.1.0"
  :description "Taps for Cascalog"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.7"]
                 [cascading/cascading-core "1.2.4"
                  :exclusions [org.codehaus.janino/janino
                               thirdparty/jgrapht-jdk1.6
                               riffle/riffle]]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]])
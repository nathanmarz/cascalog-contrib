(defproject cascalog-contrib "1.0.0-SNAPSHOT"
  :description "Useful extensions to Cascalog."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.3"]
                 [hadoop-util "0.2.4"]
                 [incanter/incanter-charts "1.3.0-SNAPSHOT"]]
  :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]
                     [org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]]
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.8.3"]
                      [hadoop-util "0.2.4"]
                      [incanter/incanter-charts "1.3.0-SNAPSHOT"]]})

(defproject cascalog-checkpoint "0.1.1-SNAPSHOT"
  :description "Workflow checkpoints for the masses."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.5"]
                 [jackknife "0.1.2"]
                 [hadoop-util "0.2.6"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [midje-cascalog "0.3.0"]]
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.8.5"]
                      [jackknife "0.1.2"]
                      [hadoop-util "0.2.6"]]
               "1.4" [[org.clojure/clojure "1.4.0-alpha3"]
                      [cascalog "1.8.5"]
                      [jackknife "0.1.2"]
                      [hadoop-util "0.2.6"]]})



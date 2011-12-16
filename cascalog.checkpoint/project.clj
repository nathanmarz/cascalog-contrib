(defproject cascalog-checkpoint "0.1.1-SNAPSHOT"
  :description "Workflow checkpoints for the masses."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.4"]
                 [hadoop-util "0.2.4"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [lein-marginalia "0.6.1"]
                     [lein-midje "1.0.4"]
                     [midje-cascalog "0.3.0"]]
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.8.4"]
                      [hadoop-util "0.2.4"]]
               "1.4" [[org.clojure/clojure "1.4.0-alpha2"]
                      [cascalog "1.8.4"]
                      [hadoop-util "0.2.4"]]})



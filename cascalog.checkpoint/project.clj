(def shared
  '[[cascalog "1.9.0"]
    [jackknife "0.1.2"]
    [hadoop-util "0.2.8"]])

(defproject cascalog-checkpoint/cascalog-checkpoint "0.3.0-SNAPSHOT" 
  :min-lein-version "2.0.0"
  :description "Workflow checkpoints for the masses."
  :url "https://github.com/nathanmarz/cascalog-contrib/tree/master/cascalog.checkpoint"
  :dependencies ~(conj shared '[org.clojure/clojure "1.4.0"])
  :profiles {:all {:dependencies ~shared}
             :1.3
             {:dependencies
              [[org.clojure/clojure "1.3.0"]]},
             :1.2
             {:dependencies
              [[org.clojure/clojure "1.2.1"]]},
             :dev
             {:dependencies
              [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
               [midje-cascalog "0.4.0"]]}})
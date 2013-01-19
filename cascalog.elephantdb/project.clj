(def shared
  '[[cascalog "1.10.1-SNAPSHOT"]
    [yieldbot/elephantdb-cascading "0.3.5-SNAPSHOT"]])

(defproject yieldbot/cascalog-elephantdb "0.3.5-SNAPSHOT"
  :min-lein-version "2.0.0"
  :source-paths ["src/clj"]
  :java-source-paths ["src/jvm"]
  :javac-options ["-source" "1.6" "-target" "1.6"]
  :jvm-opts ["-server" "-Xmx768m"]
  :dependencies ~(conj shared '[org.clojure/clojure "1.4.0"])
  :profiles {:1.2 {:dependencies [[org.clojure/clojure "1.2.1"]]}
             :1.3 {:dependencies [[org.clojure/clojure "1.3.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.0-RC1"]]}
             :dev
             {:dependencies
              [[org.apache.hadoop/hadoop-core "1.0.3"]
               [midje-cascalog "0.4.0"
                :exclusions [org.clojure/clojure]]
               [hadoop-util "0.2.8"]]
              :plugins [[lein-midje "2.0.3"
                         lein-pedantic "0.0.5"]]}}
  :pedantic :warn)

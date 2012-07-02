(def shared
  '[[cascalog "1.9.0"]
    [elephantdb/elephantdb-cascading "0.3.2"]])

(defproject cascalog-elephantdb "0.3.3-SNAPSHOT"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.4.0"]
                     [hadoop-util "0.2.8"]]
  :dependencies ~(conj shared '[org.clojure/clojure "1.4.0"])
  :multi-deps {"1.2" ~(conj shared '[org.clojure/clojure "1.2.1"])
               "1.3" ~(conj shared '[org.clojure/clojure "1.3.0"])})

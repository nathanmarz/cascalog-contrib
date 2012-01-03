(defproject cascalog-elephantdb "0.2.0"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cascalog "1.8.5-SNAPSHOT"]
                 [elephantdb/elephantdb-cascading "0.2.0"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.3.0"]]
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.8.5-SNAPSHOT"]
                      [elephantdb/elephantdb-cascading "0.2.0"]]
               "1.4" [[org.clojure/clojure "1.4.0-alpha2"]
                      [cascalog "1.8.5-SNAPSHOT"]
                      [elephantdb/elephantdb-cascading "0.2.0"]]})

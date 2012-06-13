(defproject cascalog-elasticsearch "0.1.1-SNAPSHOT"
  :description "Elasticsearch integration for Cascading and Cascalog."
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :jvm-opts ["-Xmx768m" "-server"]
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [cascalog "1.9.0"]]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [lein-multi "1.1.0-SNAPSHOT"]
                     [midje-cascalog "0.4.0"]]
  :multi-deps {"1.2" [[org.clojure/clojure "1.2.1"]
                      [cascalog "1.9.0"]]
               "1.3" [[org.clojure/clojure "1.3.0"]
                      [cascalog "1.9.0"]]})


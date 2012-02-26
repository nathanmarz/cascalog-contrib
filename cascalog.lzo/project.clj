(defproject cascalog-lzo "0.1.0-wip7"
  :description "Lzo compression taps for Cascalog."
  :repositories {"bird" "https://raw.github.com/kevinweil/elephant-bird/master/repo"}
  :dependencies [[cascalog "1.9.0-wip7"]
                 [cascalog/elephant-bird "2.1.9"]
                 [hadoop-lzo "0.4.14"]]
  :exclusions [yamlbeans
               com.google/protobuf-java
               com.hadoop/hadoop-lzo
               org.apache.thrift/libthrift
               org.apache.pig/pig
               org.apache.hive/hive-serde
               org.apache.hadoop/hadoop-core
               org.apache.mahout/mahout-core]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.4.0"]])

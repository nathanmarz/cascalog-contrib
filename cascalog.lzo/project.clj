(defproject cascalog-lzo "0.1.1"
  :description "Lzo compression taps for Cascalog."
  :repositories {"bird" "https://raw.github.com/kevinweil/elephant-bird/master/repo"}
  :dependencies [[cascalog "1.9.0"]
                 [cascalog/elephant-bird "2.2.3-SNAPSHOT"]
                 [hadoop-lzo "0.4.15"]]
  :exclusions [yamlbeans
               com.hadoop/hadoop-lzo
               org.apache.thrift/libthrift
               org.apache.pig/pig
               org.apache.pig/piggybank
               org.apache.hive/hive-serde
               org.apache.hive/hive-exec
               org.apache.hadoop/hadoop-core
               org.apache.mahout/mahout-core
               org.apache.hcatalog/hcatalog]
  :dev-dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]
                     [midje-cascalog "0.4.0"]])

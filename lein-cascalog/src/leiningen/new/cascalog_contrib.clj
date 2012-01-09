(ns leiningen.new.cascalog-contrib
  (:use leiningen.new.templates))

(def render (renderer "cascalog_contrib"))

(defn cascalog-contrib
  "Leiningen template generator for Cascalog-Contrib modules."
  [name]
  (let [data {:name name
              :sanitized (sanitize name)}]
    (println "Generating a cascalog-contrib module called" name ".")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["src/clj/cascalog/{{sanitized}}.clj" (render "module.clj" data)]
             ["test/cascalog/{{sanitized}}_test.clj" (render "test.clj" data)])))

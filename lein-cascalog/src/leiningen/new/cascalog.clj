(ns leiningen.new.cascalog
  (:use leiningen.new.templates))

(def render (renderer "cascalog"))

(defn cascalog
  "Leiningen template generator for Cascalog projects."
  [name]
  (let [data {:name name
              :sanitized (sanitize name)}]
    (println "Generating a cascalog project called" name ".")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["src/clj/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["test/{{sanitized}}/core_test.clj" (render "test.clj" data)])))

(defproject assert-json "0.1.1-SNAPSHOT"
  :description "Small library to test JSON objects"
  :url "https://github.com/manuelp/assert-json"
  :license {:name "Mozilla Public License 2.0"
            :url "http://www.mozilla.org/MPL/2.0/"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [cheshire "5.1.1"]]
  
  :source-paths ["src/clojure"]
  :test-paths ["test/clojure"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.6" "-source" "1.6"]
  :aot [assert-json.java])

;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.

(ns assert-json.core
  (:use [cheshire.core :as cheshire :only [parse-string]]
        [assert-json.exceptions :only [property-not-found
                                       wrong-property-type
                                       wrong-property-value]]
        [clojure.pprint :only [pprint]]))

(defn- get-key 
  "Get a key from a map, returning the value `::nothing-here` if no such key was found."
  [m key]
  (get m key ::nothing-here))

(defn- both-instance? 
  "Check if both `a` and `b` values are instances of the same `clazz` class."
  [clazz a b]
  (and (instance? clazz a)
       (instance? clazz b)))

(defn- same-type?
  "Check if two values are of the same exact type, or either a `java.util.List` or `java.util.Map`."
  [a b]
  (or (= (type a) (type b))
      (both-instance? java.util.List a b)
      (both-instance? java.util.Map a b)))

(defn- assert-property 
  "Assert that the map `m` contains the given `property` with the desired `value` and type."
  [m property value]
  (let [m-val (get-key m property)]
    (cond (= ::nothing-here m-val) (throw (property-not-found property))
          (not (same-type? m-val value)) (throw (wrong-property-type property m-val value))
          (not= value m-val) (throw (wrong-property-value property m-val value))
          :default true)))

(defmacro assert-json-values 
  "Assert JSON properties (and corresponding values) in a convenient form.
  
  Example:
  
  (assert-json \"{\\\"prop\\\":1,\\\"other\\\":[1,2]}\"
               \"prop\" 1)
               \"other\" [1 2])"
  [json & body]
  `(let [m# (parse-string ~json)] 
     (map (fn [c#] (assert-property m# (first c#) (second c#))) 
          (partition 2 (vector ~@body)))))

(defn assert-json
  "Assert JSON properties on a `json-string`, setting expectations via an `expected` map. 
  
  Properties are asserted for presence, and their values for type and equality."
  [json-string expected]
  (let [json (parse-string json-string)]
    (doall (map #(assert-property json (key %) (val %))
                expected))))

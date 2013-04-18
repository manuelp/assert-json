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

(defn assert-property 
  "Assert that the map `m` contains the given `property` with the desired `value` and type."
  [m property value]
  (let [m-val (get-key m property)]
    (cond (= ::nothing-here m-val) (throw (property-not-found property))
          (not= (type m-val) (type value)) (throw (wrong-property-type property m-val value))
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
    (doall (map #(assert-property json (first %) (second %))
                expected))))



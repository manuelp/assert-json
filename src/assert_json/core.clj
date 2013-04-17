(ns assert-json.core
  (:use [cheshire.core :as json :only [parse-string]]))

(defn- has-key [m key]
  (get m key ::nothing-here))

(defn- property-not-found [property]
  (Exception. (str "Property not found: " (name property))))

(defn- wrong-property-type
  [property m-val value]
  (Exception. (str "Wrong type value for property " 
                   (name property) ": expected " (type value) 
                   ", found: " (type m-val))))

(defn- wrong-property-value
  [property m-val value]
  (Exception. (str "Wrong value for property " (name property)
                   ": expected " value
                   ", found: " m-val)))

(defn map-has? [m property value]
  (let [m-val (has-key m property)]
    (cond (= ::nothing-here m-val) (throw (property-not-found property))
          (not= (type m-val) (type value)) (throw (wrong-property-type property m-val value))
          (not= value m-val) (throw (wrong-property-value property m-val value))
          :default true)))


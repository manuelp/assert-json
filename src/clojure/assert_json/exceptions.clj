(ns assert-json.exceptions)

(defn property-not-found 
  "Generate an `Exception` for a property that is not found in a JSON."
  [property]
  (Exception. (str "Property not found: " property)))

(defn wrong-property-type
  "Generate an `Exception` for a property with a value of the wrong type."
  [property actual expected]
  (Exception. (str "Wrong type value for property " 
                   property ": expected " (type expected) 
                   ", found: " (type actual))))

(defn wrong-property-value
  "Generate an `Exception` for a value of a property in a JSON which is different from the expected one."
  [property m-val value]
  (Exception. (str "Wrong value for property " property
                   ": expected " value
                   ", found: " m-val)))


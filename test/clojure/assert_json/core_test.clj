(ns assert-json.core-test
  (:use clojure.test
        assert-json.core
        assert-json.exceptions))

(deftest assert-json-successfully
  (testing "Simple values"
    (is (assert-json "{\"prop\":1}" {"prop" (int 1)}))
    (is (assert-json "{\"prop\":\"_\"}" {"prop" "_"}))
    (is (assert-json "{\"prop\":1.2}" {"prop" 1.2})))
  (testing "Composite values"
    (is (assert-json "{\"prop\":[1,2,3]}" {"prop" [1 2 3]}))
    (is (assert-json "{\"prop\":{\"sub\":[42]}}" {"prop" {"sub" [42]}}))))

(deftest assert-json-with-errors
  (is (thrown-with-msg? Exception #"Property not found: _" 
                        (assert-json "{\"prop\":1}" {"_" (int 1)})))
  (is (thrown-with-msg? Exception 
                        #"Wrong type value for property prop: expected.*java.lang.String, found:.*java.lang.Integer" 
                        (assert-json "{\"prop\":1}" {"prop" "1"})))
  (is (thrown-with-msg? Exception #"Wrong value for property prop: expected 2, found: 1" 
                        (assert-json "{\"prop\":1}" {"prop" (int 2)}))))

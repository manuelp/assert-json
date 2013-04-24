;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.

(ns assert-json.core-test
  (:use clojure.test
        assert-json.core
        assert-json.exceptions)
  (:import (java.util HashMap ArrayList)))

(deftest assert-json-successfully
  (testing "Simple values"
    (is (assert-json "{\"prop\":1}" {"prop" (int 1)}))
    (is (assert-json "{\"prop\":\"_\"}" {"prop" "_"}))
    (is (assert-json "{\"prop\":1.2}" {"prop" 1.2}))
    
    (testing "Java data"
      (is (assert-json "{\"prop\":1}" (doto (HashMap.)
                                            (.put "prop" (Integer. 1)))))))
  
  (testing "Composite values"
    (is (assert-json "{\"prop\":[1,2,3]}" {"prop" [1 2 3]}))
    (is (assert-json "{\"prop\":{\"sub\":[42]}}" {"prop" {"sub" [42]}}))
    
    (testing "Java data"
      (is (assert-json "{\"prop\":[1,2,3]}" (doto (HashMap.)
                                                  (.put "prop" (doto (ArrayList.)
                                                                     (.add 1)
                                                                     (.add 2)
                                                                     (.add 3))))))
      (is (assert-json "{\"prop\":{\"a\":1}}" (doto (HashMap.)
                                                  (.put "prop" (doto (HashMap.)
                                                                     (.put "a" 1)))))))))

(deftest assert-json-with-errors
  (is (thrown-with-msg? Exception #"Property not found: _" 
                        (assert-json "{\"prop\":1}" {"_" (int 1)})))
  (is (thrown-with-msg? Exception 
                        #"Wrong type value for property prop: expected.*java.lang.String, found:.*java.lang.Integer" 
                        (assert-json "{\"prop\":1}" {"prop" "1"})))
  (is (thrown-with-msg? Exception #"Wrong value for property prop: expected 2, found: 1" 
                        (assert-json "{\"prop\":1}" {"prop" (int 2)}))))

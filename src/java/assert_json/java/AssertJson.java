/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 */
package assert_json.java;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

import java.util.Map;

public class AssertJson {
  private static final Var REQUIRE = RT.var("clojure.core", "require");

  static {
    REQUIRE.invoke(Symbol.intern("assert-json.core"));
  }

  private static final Var ASSERT_JSON = RT.var("assert-json.core", "assert-json");

  /**
   * Assert that a given JSON (contained into a {@link String}), has all the expected properties,
   * with an expected <em>value</em> and <em>type</em>.
   *
   * @param jsonString JSON string
   * @param expected A map containing (property, value) pairs.
   */
  public static void assertJson(String jsonString, Map<String, Object> expected) {
    ASSERT_JSON.invoke(jsonString, expected);
  }
}

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

  private static final Var INTO = RT.var("clojure.core", "into");
  private static final Var HASH_MAP = RT.var("clojure.core", "hash-map");
  private static final Var ASSERT_JSON = RT.var("assert-json.core", "assert-json");

  /**
   * Assert that a given JSON (contained into a {@link String}), has all the expected properties,
   * with an expected <em>value</em> and <em>type</em>.
   *
   * @param jsonString JSON string
   * @param expected A map containing (property, value) pairs.
   */
  public static void assertJson(String jsonString, Map<String, Object> expected) {
    Object expectedMap = INTO.invoke(HASH_MAP.invoke(), expected);
    ASSERT_JSON.invoke(jsonString, expectedMap);
  }
}

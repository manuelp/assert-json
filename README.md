# assert-json

This is a small library useful to make assertions on the content of a JSON string. It's usable from Clojure and Java, and it may be useful for example as an aid to implement [semantic versioning](http://semver.org/) for web APIs.

It uses [Cheshire](https://github.com/dakrone/cheshire) under the hood to parse a JSON string and match the resulting data structure against a map that contains expected values.

## Features ##

- Supports primitive values and nested arrays and objects.
- API for Java (it can be used with [JUnit](http://junit.org/)).
- Tests, for any given property:
  - if it *exists* in the JSON.
  - if its value has the same (or compatible) type of the expected one.
  - if the actual and expected values are the same.
- Significant error messages for every case in the previous point, so that they are informative (at runtime or in a testing context, in both Clojure and Java-land).

## Project Maturity ##
This library right now should be considered *alpha quality*.

It's API, could be subject to change (even if right now it features only one generic public function).

## Supported Clojure Versions ##
This library was built on *Clojure 1.5.1*, however it should work with Clojure 1.3 and up.

The most recent stable Clojure release is highly recommended.

## Artifacts ##

assert-json artifacts are released to [Clojars](https://clojars.org/assert-json). See that page for updated dependency configuration for both Leiningen and Maven.

### Leiningen ###

Add dependency in your *project.clj*:

```clojure
[assert-json "0.1.0"]
```

### Maven ###

Add Clojars repository definition to your *pom.xml*:

```xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>
```

and then the dependency:

```xml
<dependency>
  <groupId>assert-json</groupId>
  <artifactId>assert-json</artifactId>
  <version>0.1.0</version>
</dependency>
```

## Getting Started

### Clojure
Assertions on a JSON string can be simply made passing a regular Clojure map to the `assert-json` function along with the string:

```clojure
(ns ...
  (:use [assert-json.core :only [assert-json]]))
  
(assert-json "{\"json\":\"string\",\"num\":1,\"values\":[42,\"universe\"]}" 
             {"json" "string"
              "num" (int 1)
              "values" [42 "universe"]})
```

However, pay attention to some caveats:

- Properties names should be regular strings.
- For the way Cheshire works right now (or I use if), integer numbers, when are *single values of root properties*, should be converted to `int` objects.

### Java
It is available a (very) small API for making assertions in the `assert_json.java.AssertJson` class, that can be used in a JUnit test method like regular assertions:

```java
import static assert_json.java.AssertJson.assertJson;

// ...

  public class SomeVeryImportantObject {
    @Test
    public void shouldAssertJsonProperties() {
      assertJson("{\"a\":1," +
                 "\"list\":[1,\"something\"]," +
                 "\"other\":{\"sub\":[42]}}", new HashMap<String, Object>() {
        {
          put("a", 1);
          put("list", Arrays.asList(1, "something"));
          put("other", new HashMap<String, Object>() {
            {
              put("sub", Arrays.asList(42));
            }
          });
        }
      });
    }
  }

```

See the complete example project [here](https://github.com/manuelp/assert-json-client).

## License

Copyright © 2013 Manuel Paccagnella

Distributed under the [Mozilla Public License 2.0](http://www.mozilla.org/MPL/2.0/).

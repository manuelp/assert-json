# assert-json

This is a small library useful to make assertions on the content of a JSON string. It's usable from Clojure and Java, and it may be useful for example as an aid to implement [semantic versioning](http://semver.org/) for web APIs.

It uses [Cheshire](https://github.com/dakrone/cheshire) under the hood to parse a JSON string and match the resulting data structure against a map that contains expected values.

*Warning: this library isn't really deployed anywhere right now, coming soon...*

## Features ##

- Supports primitive values and nested arrays and objects.
- API for Java (it can be used with [JUnit](http://junit.org/)).
- Tests, for any given property:
  - if it *exists* in the JSON.
  - if its value has the same (or compatible) type of the expected one.
  - if the actual and expected values are the same.
- Significant error messages for every case in the previous point, so that they are informative (at runtime or in a testing context, in both Clojure and Java-land).

## Usage

* Last version: **[assert-json "0.1.0-SNAPSHOT"]**

### Clojure
Simply add this library as a dependency in [Leiningen](http://leiningen.org/) (see *Last version* before), and use it to make assertions:

```clojure
(ns ...
  (:use [assert-json.core :only [assert-json]]))
  
(assert-json "{\"json\":\"string\",\"values\":[42,\"universe\"]}" 
             {"json" "string"
              "value" [42 "universe"]})
```

### Java
Add *assert-json* as a Maven dependency in your pom.xml file:

```xml
<dependency>
  <groupId>assert-json</groupId>
  <artifactId>assert-json</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

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

See a complete example [here](https://github.com/manuelp/assert-json-client).

## License

Copyright © 2013 Manuel Paccagnella

Distributed under the Eclipse Public License, the same as Clojure.

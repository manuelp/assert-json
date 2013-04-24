## 0.1.1-SNAPSHOT ##

### Bugfix ###
- Type of object values is compared on whether both actual and expectes ones are `java.util.Map`s, not on actual concrete type. In other words, objects are treated internally in the same way arrays are (instances of `java.util.List`).

## 0.1.0 ##
First public version.
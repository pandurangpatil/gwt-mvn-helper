gwt-mvn-helper
==============

This plugin will help generate Request factory entity proxy and value proxy interfaces as well as Entity request interface to expose service methods to GWT code. This plugin whlie generating proxy interfaces can copy validation annotations from entity to proxy interface. So that GWT client side validation can be done using GWT validation framework. This plugin can also generate Overlay type interface from pojo class. I am planning to write details usage help, for time being you can refer to mvn-helper-test module for the useage. I have tried all possible use cases with that module, you can checkout the module and try making the build. For pom.xml usage help refer the same modules  [pom.xml](https://github.com/pandurangpatil/gwt-mvn-helper/blob/master/mvn-helper-test/pom.xml)


To use this depedency add below Maven repository.
```xml
   <repositories>
      <repository>
         <id>Pandurang repo</id>
         <url>https://github.com/pandurangpatil/mvn-repo/raw/master/releases</url>
      </repository>
   </repositories>
```

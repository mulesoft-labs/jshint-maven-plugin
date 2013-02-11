# jshint-maven-plugin
Validates javascript files using [jshint-node](https://github.com/jshint/node-jshint).

# Build

    mvn clean install

# Usage

Add the following snippet inside build -> plugins:

```xml
<plugin>
    <groupId>org.mule.tools.javascript</groupId>
    <artifactId>jshint-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <phase>validate</phase>
            <goals>
                <goal>jshint</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

By default Javascript files should be placed in `src/main/js` but any other path can be specified with the 'outputDirectory' parameter. 

You may need to add this to your pom.xml (sorry, no releases yet):

```xml
  <pluginRepositories>
    <pluginRepository>
      <id>mulesoft-snapshots</id>
      <name>MuleSoft Snapshot Repository</name>
      <url>https://repository.mulesoft.org/snapshots/</url>
      <layout>default</layout>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
    </pluginRepository>
  </pluginRepositories>
```

# Authors
Alberto Pose (@thepose)

# License
Copyright 2012 MuleSoft, Inc.

Licensed under the Common Public Attribution License (CPAL), Version 1.0.
    
### Happy hacking!

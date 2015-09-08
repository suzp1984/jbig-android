# jbig-android
This is a [JBIG](https://en.wikipedia.org/wiki/JBIG) codec toolkit in Android platform.

# implementation detail

jbig-android is a android level wrapper above the [JBIG-KIT](http://www.cl.cam.ac.uk/~mgk25/jbigkit/).
So it include [JBIG-KIT](http://www.cl.cam.ac.uk/~mgk25/jbigkit/)'s source code inside of
it in Android's JNI level.

# API document

```java
 # encode code
    Bitmap[] bitmaps = ...;

    JbigCodec jbigCodec = JbigCodecFactory.getJbigCodec(JbigCodecFactory.CODEC.JNI_CODEC);
    byte[] jbigData = jbigCodec.encode(bitmaps);

```

Above code is what I said jbig encoding from multiple layers of Bitmaps.


```java
    # jbigData is the above byte[] jbigData.

    JbigCodec jbigCodec = JbigCodecFactory.getJbigCodec(JbigCodecFactory.CODEC.JNI_CODEC);
    Bitmap[] bms = jbigCodec.decode(jbigData);

```

And this is the decoder part, the reverting process of above encoding part, here it
decode the jbigData byte array into multiple layers of Bitmaps.

# Sample app ScreenShot

![sample app](https://github.com/suzp1984/jbig-android/raw/master/img/Screenshot-20150908-171942.gif)

# Include into your project

## for Android Studio or Gradle user

```gradle
allprojects {
    repositories {
        jcenter()

        maven {
            url  "http://dl.bintray.com/suzp1984/maven"
        }
    }
}
```

Add my bintray repo into your root **build.gradle**.

```gradle
dependencies {
  compile(group: 'org.jacob.lib.jbig', name: 'jbig-android', version: '1.0', ext: 'aar')
}
```

Then, add above line into your modules's dependencies scope.

## for maven user

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>

<profiles>
    <profile>
        <repositories>
            <repository>
                <snapshots>
                    <enabled>false</enabled>
                </snapshots>
                <id>bintray-suzp1984-maven</id>
                <name>bintray</name>
                <url>http://dl.bintray.com/suzp1984/maven</url>
            </repository>
        </repositories>

        <pluginRepositories>
            <pluginRepository>
                <snapshots>
                    <enabled>false</enabled>
                </snapshots>
                <id>bintray-suzp1984-maven</id>
                <name>bintray-plugins</name>
                <url>http://dl.bintray.com/suzp1984/maven</url>
            </pluginRepository>
        </pluginRepositories>

        <id>bintray</id>
    </profile>
</profiles>

<activeProfiles>
    <activeProfile>bintray</activeProfile>
</activeProfiles>

</settings>
```

Frist, setup the maven's xml file to use this repository.


```xml
<dependency>
    <groupId>org.jacob.lib.jbig</groupId>
    <artifactId>jbig-android</artifactId>
    <version>1.0</version>
    <type>aar</type>
</dependency>
```

Then, setup the dependency.

# Opensource code included

[JBIG-KIT](http://www.cl.cam.ac.uk/~mgk25/jbigkit/): GPL licence

# Licence

```
                     GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
```

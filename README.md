#####The project is in Java 11

#### About
This project has the main goal to generate all possible combinations giving an input os packages. The algorithm should select the lighter and expensive combination of items.

#### How to RUN
To run the API you just need to import the API in your project and call the method `Packer.pack(filePath)` passing a valid input file. Use the example_input file in the project folder to test.

> Add the dependency:
~~~XML
<dependency>
    <groupId>com.mobiquity</groupId>
    <artifactId>packer</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
~~~
~~~ shell script
mvn clean install
~~~
~~~java
public static void main(String[] args) throws APIException {
    System.out.println(Packer.pack("path/to/file"));
}
~~~
>
>
##### If the process generates any exception, the API will return an APIException with the stacktrace

#### Decisions, patterns
##### Design
I separated the hole flux in 4 steps:
1. Read the file
2. Validate the packages
3. Generate all items possibilities
4. Format the output 
##### Facade
To call those steps I used the Facade pattern to make my code fluent
##### Tests
- I used JUnit for unit tests and Mockito to mock the integration tests, specifically the file reading process
- The Fixture library is being used to generate the test templates and model cases 
##### Log
To log infos the logback and SLF4j are being used together
##### File reading
To read the file, I used Regex to match the pattern of packages and Commons-IO lib to extract the file info
##### Combinations
To generate all items combinations I used the **Forward-Backward Algorithm**
 
#####
#### Technologies and libraries
> Used Maven to manage dependencies

- Java 11
- Lombok
- Commons-IO
- Logback
- SLF4J
- JUnit
- Mockito


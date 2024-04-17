javac -cp lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar;lib/slf4j-api-1.7.25.jar *.java
java -cp .;lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore EdgeConnectorTest
java RunEdgeConvert

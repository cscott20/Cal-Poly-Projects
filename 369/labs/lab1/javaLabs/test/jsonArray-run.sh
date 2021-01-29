#!/bin/bash

# this script compiles and runs jsonArrayTest.java program
# it assumes that you placed org.json-2010521.jar file 
# (org.json library) in the same directory as jsonArrayTest.java


javac -cp org.json-20120521.jar  jsonArrayTest.java
java -cp org.json-20120521.jar:. jsonArrayTest


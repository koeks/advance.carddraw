@echo off
echo Compiling the main program...
javac -d . src\main\java\*.java

echo Compiling the test classes...
javac -cp .;lib\junit-4.x.jar -d . src\test\java\*.java

echo Running the main program...
java -cp . main.java.PokerGame

echo Running unit tests...
java -cp .;lib\junit-4.x.jar org.junit.runner.JUnitCore main.java.DeckTest
java -cp .;lib\junit-4.x.jar org.junit.runner.JUnitCore main.java.HandTest


echo Build completed.
@pause
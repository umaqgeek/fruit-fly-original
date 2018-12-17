clear
javac -Xlint:unchecked models/*.java helpers/*.java controllers/*.java main/*.java
java main/MainPage $1

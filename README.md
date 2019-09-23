# satTv
Direct to home (D2H) operator SatTV wants you to design a software system for its customers. The base packages(packs) available to purchase are Bronze, Gold and Silver. The regional packages are Gujarati, Marathi and Tamil. When the user purchases a base pack, the regional pack is free and compulsory. User can purchase regional pack without a base pack. User can choose the channels separately which is called an A-La-Carte pack. The duration of the pack has to be entered in months and the minimum duration is 1 month. On the total price of the pack, 18% GST is applicable which will be the final amount. User will receive a 5% discount on the final amount if the duration is at least 3 months. User will receive a 10% discount on the final amount if duration is at least 6 months.

# How to Build and Run code ?

### To Build project
go to **\sattv** relative directory or move your current working directory where **pom.xml** are located.
   
```
mvn package install
```

### To Run project
go to **/sattv/target** relative directory.
```
java -jar sattv-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

# Local README for this program to test building of Job

cmd: java -jar jenkins.war

Setup Jenkins job:
SCM = GIT = https://github.com/YashD15/grad_YashDhavde_WI250.git

Triggers = Poll SCM = * * * * * (currently working on Poll SCM, as small repo, check for every minute)

Build steps =
D:
cd ++YASH++\+Grad Batch+\grad_YashDhavde_WI250\GitCalculatorJob
javac Calculator.java
javac CalculatorMain.java
java CalculatorMain

## EDIT HERE ANYTHING AND PUSH TO OBSERVE
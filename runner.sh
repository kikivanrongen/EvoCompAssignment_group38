#!/bin/bash
javac -cp contest.jar player38.java
jar cmf MainClass.txt submission.jar player38.class

echo "Sphere Evaluation Function:" > results.txt
java -jar testrun.jar -submission=player38 -evaluation=SphereEvaluation -seed=1 >> results.txt
echo "Bent Cigar Function: " >> results.txt
java -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1 >> results.txt
echo "Schaffers Evaluation Function: " >> results.txt
java -jar testrun.jar -submission=player38 -evaluation=SchaffersEvaluation -seed=1 >> results.txt
echo "Katsuura Evaluation Function: " >> results.txt
java -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> results.txt

#!/bin/bash
echo "Sphere Evaluation Function:" > results.txt
java -jar testrun.jar -submission=player38 -evaluation=SphereEvaluation -seed=1 >> results.txt
echo "Bent Cigar Function: " >> results.txt
java -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1 >> results.txt
echo "Schaffers Evaluation Function: " >> results.txt
java -jar testrun.jar -submission=player38 -evaluation=SchaffersEvaluation -seed=1 >> results.txt
echo "Katsuura Evaluation Function: " >> results.txt
java -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> results.txt

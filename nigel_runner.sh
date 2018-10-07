#!/bin/bash
echo "Compiling"
javac -cp contest.jar player38.java ParentSelection.java Recombination.java SurvivorSelection.java
jar cmf MainClass.txt submission.jar player38.class ParentSelection.class Recombination.class SurvivorSelection.class

echo "Evaluate Bent Cigar"
for i in {1..100}
do
  java -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1 >> data/cigar.txt
done

echo "Evaluate Schaffers"
for i in {1..100}
do
  java -jar testrun.jar -submission=player38 -evaluation=SchaffersEvaluation -seed=1 >> data/schaffers.txt
done

echo "Evaluate Katsuura"
for i in {1..100}
do
  java -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> data/katsuura.txt
done

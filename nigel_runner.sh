#!/bin/bash
echo "Compiling"
javac -cp contest.jar player38.java ParentSelection.java Recombination.java Mutation.java SurvivorSelection.java
jar cmf MainClass.txt submission.jar player38.class ParentSelection.class Recombination.class Mutation.class SurvivorSelection.class

# echo "Evaluate Bent Cigar"
# > data/cigar.txt
# for i in {1..100}
# do
#   echo $i
#   java -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1 >> data/cigar.txt
# done
# python data/score.py data/cigar.txt >> data/cigar.txt

echo "Evaluate Schaffers"
> data/schaffers.txt
for i in {1..100}
do
  echo $i
  java -jar testrun.jar -submission=player38 -evaluation=SchaffersEvaluation -seed=1 >> data/schaffers.txt
done
python data/score.py data/schaffers.txt >> data/schaffers.txt

# echo "Evaluate Katsuura"
# > data/katsuura.txt
# for i in {1..100}
# do
#   echo $i
#   java -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> data/katsuura.txt
# done
# python data/score.py data/katsuura.txt >> data/katsuura.txt

#!/bin/bash
echo "Compiling"
javac -cp contest.jar player38.java ParentSelection.java Recombination.java Mutation.java SurvivorSelection.java Individual.java
jar cmf MainClass.txt submission.jar player38.class ParentSelection.class Recombination.class Mutation.class SurvivorSelection.class Individual.class
export LD_LIBRARY_PATH=~/Documents/EvoCompAssignment_group38

start=`date`
echo $start

echo "Evaluate Katsuura"
for i in {0..23}
do
  if (($i % 10 == 0))
  then
    echo $i
  fi
  > sanne/katsuura_$i.txt
  for j in {1..100}
  do
    java -Diter=$i -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> sanne/katsuura_$i.txt
  done
done
# python data/score.py data/katsuura_0502.txt >> data/katsuura_0502.txt


end=`date`
echo $end

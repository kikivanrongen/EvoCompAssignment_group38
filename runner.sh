#!/bin/bash
echo "Compiling"
javac -cp contest.jar player38.java ParentSelection.java Recombination.java Mutation.java SurvivorSelection.java Individual.java
jar cmf MainClass.txt submission.jar player38.class ParentSelection.class Recombination.class Mutation.class SurvivorSelection.class Individual.class

start=`date +%s`
echo "Evaluate Bent Cigar"
# for i in {0..863}
# do
#   if (($i % 10 == 0))
#   then
#     echo $i
#   fi
#   > data2/cigar_$i.txt
#   for j in {0..100}
#   do
#     # java -Diter=$i -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1  >> data2/cigar_$i.txt
#     java -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1  >> results.txt
#   done
# done
# python data/score.py data/cigar_0502.txt >> data/cigar_0502.txt
#
# end=`date +%s`
# runtime=$((end-start))
# echo Finished, time: $runtime
# start=`date +%s`

# echo "Evaluate Schaffers"
# for i in {0..863}
# do
#   if (($i % 10 == 0))
#   then
#     echo $i
#   fi
#   > data2/schaffers_$i.txt
#   for j in {1..100}
#   do
#     java -Diter=$i -jar testrun.jar -submission=player38 -evaluation=SchaffersEvaluation -seed=1 >> data2/schaffers_$i.txt
#   done
# done
# python data/score.py data/schaffers_0502.txt >> data/schaffers_0502.txt

# end=`date +%s`
# runtime=$((end-start))
# echo Finished, time: $runtime
# start=`date +%s`

# echo "Evaluate Katsuura"
# > data/katsuura_0502.txt
# for i in {1..10}
# do
#   if (($i % 10 == 0))
#   then
#     echo $i
#   fi
#   java -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> data/katsuura_0502.txt
# done
# python data/score.py data/katsuura_0502.txt >> data/katsuura_0502.txt

# end=`date +%s`
# runtime=$((end-start))
# echo Finished, time: $runtime

# echo "Start sphere evaluation"
# echo "Sphere Evaluation Function:" >> results.txt
# java -jar testrun.jar -submission=player38 -evaluation=SphereEvaluation -seed=1 >> results.txt

echo "Start Cigar evaluation"
echo "Bent Cigar Function: " >> results.txt
java -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1

# echo "Start Schaffer evaluation"
# echo "Schaffers Evaluation Function: " >> results.txt
# java -jar testrun.jar -submission=player38 -evaluation=SchaffersEvaluation -seed=1

# echo "Start Katsuura evaluation"
# echo "Katsuura Evaluation Function: " >> results.txt
# java -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> results.txt

# jar cmf MainClass.txt submission.jar *.class #ParentSelection.class Recombination.class #SurvivorSelection.class
# java -jar testrun.jar -submission=player38 -evaluation=SphereEvaluation -seed=1

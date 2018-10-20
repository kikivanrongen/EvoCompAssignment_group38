echo "Compiling"
javac -cp contest.jar player38.java ParentSelection.java Recombination.java Mutation.java SurvivorSelection.java Individual.java
jar cmf MainClass.txt submission.jar player38.class ParentSelection.class Recombination.class Mutation.class SurvivorSelection.class Individual.class
export LD_LIBRARY_PATH=~/Documents/EvoCompAssignment_group38
start=`date +%s`
echo $start
# echo "Evaluate Bent Cigar for diversity"
# for i in {0..23}
# do
#   echo $i
#   > data_standard_parameters/cigar_$i.txt
#   for j in {1..100}
#   do
#     java -Diter=$i -jar testrun.jar -submission=player38 -evaluation=BentCigarFunction -seed=1 >> data_standard_parameters/cigar_$i.txt
#   done
# done
echo "Evaluate Schaffers for diversity LETOP heb je diversity uncomment in player38?"
for i in {0..14}
do
  echo $i
  > data_standard_parameters/schaffers_$i.txt
  for j in {1..100}
  do
    java -Diter=$i -jar testrun.jar -submission=player38 -evaluation=SchaffersEvaluation -seed=1 >> data_standard_parameters/schaffers_$i.txt
  done
done
# echo "Evaluate Katsuura for diversity"
# for i in {0..23}
# do
#   echo $i
#   > diversitydata/katsuura_$i.txt
#   for j in {1..30}
#   do
#     java -Diter=$i -jar testrun.jar -submission=player38 -evaluation=KatsuuraEvaluation -seed=1 >> data_standard_parameters/cigar_$i.txt
#   done
# done
end=`date +%s`
echo $end

import numpy as np

#Mutopts en recopts can be used to print the full names below
mutopts = {"un": "uniform", "ga": "gauss", "1": "uncorrelated-onestep", "n":"uncorrelated-nstep"}
recopts = {"dp":"discrete-pointwise", "dt":"discrete-tailswap", "aw":"arithmetic-whole", "as":"arithmetic-simple", "a1":"arithmetic-single", "bl":"blendcrossover"}
options = ["dp_un", "dt_un", "aw_un", "as_un", "a1_un", "bl_un", "dp_ga", "dt_ga", "aw_ga", "as_ga", "a1_ga", "bl_ga", "dp_1", "dt_1", "aw_1", "as_1", "a1_1", "bl_1", "dp_n" , "dt_n", "aw_n", "as_n", "a1_n", "bl_n"]


data = {}
for i in range(24):
    filename = "katsuura_" + str(i) + ".txt"
    f = open(filename,'r')
    lines = f.readlines()
    print(lines[0])
    print(lines[0].split())
    values = [line.split()[1] for line in lines if "Score" in line]
    values= np.array(values).astype(np.float)
    mean = np.mean(values)
    std = np.std(values)
    data[options[i]] = [mean,std]

print("Short name\tMean\tStandard Deviation")
for dat in data:
    print(dat, data[dat])

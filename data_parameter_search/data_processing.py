import numpy as np
# import matplotlib
# matplotlib.use("agg")
from tkinter import *
import matplotlib.pyplot as plt
import operator

function = "schaffers"

#Mutopts en recopts can be used to print the full names below
options = ["s_0.01_a_0.1", "s_0.01_a_0.3", "s_0.01_a_0.5", "s_0.05_a_0.1", "s_0.05_a_0.3", "s_0.05_a_0.5", "s_0.1_a_0.1", "s_0.1_a_0.3",  "s_0.1_a_0.5", "s_0.2_a_0.1", "s_0.2_a_0.3", "s_0.2_a_0.5", "s_0.5_a_0.1", "s_0.5_a_0.3","s_0.5_a_0.5"]

# BC
#algoptions = ["as_ga", "aw_1"]

# SCHAFFERS
algoptions = ["bl_un"]

# KATSUURA
#algoptions = ["a1_ga", "a1_1"]


alloptions = []
for alg in algoptions:
    for opt in options:
        alloptions.append(alg+" "+opt)

data = {}
means = []
stds = []
lowest_mean = 10.0
highest_mean = 0.0


counter = 0
for alg in algoptions:
    for i in range(15):
        filename = 'data_parameter_search' + function + "_" + alg + "_" + str(i) + ".txt"
        f = open(filename,'r')
        lines = f.readlines()
        values = [line.split()[1] for line in lines if "Score" in line]
        values= np.array(values).astype(np.float)
        mean = np.mean(values)
        std = np.std(values)
        means.append(mean)
        stds.append(std)
        data[alloptions[counter]] = [mean,std]
        counter += 1
        if mean < lowest_mean:
            lowest_mean = mean
            lowest_mean_param = alloptions[counter]
        if mean > highest_mean:
            highest_mean = mean
            highest_mean_param = alloptions[counter]

data = sorted(data.items(), key=operator.itemgetter(1))
print(data)
sorted_funcs = []
for function in data:
    sorted_funcs.append(function[0])

print(sorted_funcs)

print("Lowest mean " + str(lowest_mean) + " for " + lowest_mean_param)
print("Highest mean " + str(highest_mean) + " for " + highest_mean_param)


_,ax = plt.subplots()
ax.bar(range(24),means)
ax.errorbar(range(24),means,stds, color="000", ls= 'none', lw=2, capthick=2)
ax.set_xlabel("Different configurations")
ax.set_ylabel("Mean score")
ax.set_title("Katsuura results")


# plt.bar(means,height=10, width=.2,xerr=stds)
# # plt.errorbar(means,stds)
plt.show()

# print("Short name\tMean\tStandard Deviation")
# for dat in data:
#     print(dat, data[dat])

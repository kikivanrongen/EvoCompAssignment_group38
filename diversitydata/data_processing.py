import numpy as np
import pandas as pd
import seaborn as sbs
import matplotlib.pyplot as plt

#Mutopts en recopts can be used to print the full names below
mutopts = {"un": "uniform", "ga": "gauss", "1": "uncorrelated-onestep", "n":"uncorrelated-nstep"}
recopts = {"dp":"discrete-pointwise", "dt":"discrete-tailswap", "aw":"arithmetic-whole", "as":"arithmetic-simple", "a1":"arithmetic-single", "bl":"blendcrossover"}
options = ["dp_un", "dt_un", "aw_un", "as_un", "a1_un", "bl_un", "dp_ga", "dt_ga", "aw_ga", "as_ga", "a1_ga", "bl_ga", "dp_1", "dt_1", "aw_1", "as_1", "a1_1", "bl_1", "dp_n" , "dt_n", "aw_n", "as_n", "a1_n", "bl_n"]

# which options to plot
plotoptions = ["dp_un", "aw_ga"]
colors = ["#9b59b6", "#3498db", "#95a5a6", "#e74c3c", "#34495e", "#2ecc71"]

function = "cigar"


generations = 65
generationHelper = 68 # plus two extra to skip score and runtime

dataFrame = pd.DataFrame(columns=options, index=range(0,generations+1))
dataFrame = dataFrame.fillna(0)

for i in range(24):
    sampleIndex = 0
    filename = function + "_" + str(i) + ".txt"
    with open(filename, 'r') as f:
        lines = f.readlines()
        for n, line in enumerate(lines):

            runIndex = n % generationHelper

            if not "Score" in line and not "Runtime" in line:
                diversityValue = float(line)
                updatedValue = (dataFrame.at[runIndex, options[i]] * sampleIndex + diversityValue) / (sampleIndex + 1)
                dataFrame.at[runIndex, options[i]] = updatedValue

            else:
                sampleIndex += 1


dataFrame = dataFrame.reset_index()

# Plotting
f, ax = plt.subplots(1, 1)
for n, plot in enumerate(plotoptions):
    sbs.pointplot(ax=ax, data = dataFrame, x = 'index', y = plot, color=colors[n])


ax.set(xlabel="Generation", ylabel="Total Manhattan distance", title="Diversity of the population for different EAs")
ax.legend(handles=ax.lines[::len(dataFrame)+1], labels=plotoptions)
ax.set_xticklabels([t.get_text().split("T")[0] for t in ax.get_xticklabels()])
plt.xticks([0, 10, 20, 30, 40, 50, 60], labels=[0, 10, 20, 30, 40, 50, 60])

plt.show()

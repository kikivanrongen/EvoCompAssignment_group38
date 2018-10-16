import numpy as np
import pandas as pd
import seaborn as sbs
import matplotlib.pyplot as plt

#Mutopts en recopts can be used to print the full names below
mutopts = {"un": "uniform", "ga": "gauss", "1": "uncorrelated-onestep", "n":"uncorrelated-nstep"}
recopts = {"dp":"discrete-pointwise", "dt":"discrete-tailswap", "aw":"arithmetic-whole", "as":"arithmetic-simple", "a1":"arithmetic-single", "bl":"blendcrossover"}
options = ["dp_un", "dt_un", "aw_un", "as_un", "a1_un", "bl_un", "dp_ga", "dt_ga", "aw_ga", "as_ga", "a1_ga", "bl_ga", "dp_1", "dt_1", "aw_1", "as_1", "a1_1", "bl_1", "dp_n" , "dt_n", "aw_n", "as_n", "a1_n", "bl_n"]

# which options to plot QUALITATIVE PARAMETERS

# ordering worst-to-best for BentCigar
#plotoptions = ['dt_un', 'a1_un', 'aw_un', 'dp_un', 'bl_un', 'as_un', 'dt_n', 'dt_ga', 'a1_1', 'a1_ga', 'dt_1', 'a1_n', 'bl_n', 'dp_n', 'dp_1', 'bl_ga', 'bl_1', 'dp_ga', 'aw_ga', 'as_n', 'aw_n', 'as_ga', 'as_1', 'aw_1']
#bestworst = ["dt_un", "aw_1"] # worst and best for BentCigar

# ordering worst to best for SCHAFFERS
plotoptions = ['dt_un', 'aw_un', 'a1_un', 'as_un', 'dp_un', 'dt_n', 'a1_n', 'bl_n', 'dt_ga', 'dt_1', 'dp_n', 'dp_ga', 'a1_1', 'bl_ga', 'a1_ga', 'bl_1', 'dp_1', 'as_n', 'as_ga', 'aw_ga', 'as_1', 'aw_n', 'aw_1', 'bl_un']
bestworst = ["dt_un", "bl_un"] # worst and best for schaffers

# ordering worst-to-best for KATSUURA
#plotoptions = ['dt_un', 'a1_un', 'bl_un', 'dp_un', 'aw_un', 'as_un', 'bl_ga', 'bl_n', 'bl_1', 'dp_n', 'dp_ga', 'dp_1', 'aw_n', 'dt_n', 'aw_ga', 'as_n', 'dt_ga', 'aw_1', 'as_ga', 'dt_1', 'a1_n', 'as_1', 'a1_ga', 'a1_1']
#bestworst = ["dt_un", "a1_1"] # worst and best for katsuura


colors = ["#e41a1c", "#8ea6c1"]

function = "katsuura"


generations = 13333 #65 for BC, 665 for SCH, 13333 for KAT
generationHelper = 13336 # 68 for BC, 668 for SCH, 13336 for KAT

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

if function == "schaffers":
    dataFrame = pd.DataFrame(np.log(dataFrame))
dataFrame = dataFrame.reset_index()
if function == "katsuura":
    dataFrame = pd.DataFrame(np.log(dataFrame))

print(dataFrame.head())



# For coloring

def lighten_color(color, amount=0.5):
    """
    Lightens the given color by multiplying (1-luminosity) by the given amount.
    Input can be matplotlib color string, hex string, or RGB tuple.

    Examples:
    >> lighten_color('g', 0.3)
    >> lighten_color('#F034A3', 0.6)
    >> lighten_color((.3,.55,.1), 0.5)
    """
    import matplotlib.colors as mc
    import colorsys
    try:
        c = mc.cnames[color]
    except:
        c = color
    c = colorsys.rgb_to_hls(*mc.to_rgb(c))
    return colorsys.hls_to_rgb(c[0], 1 - amount * (1 - c[1]), c[2])


# Plotting
f, ax = plt.subplots(1, 1)
for n, plot in enumerate(plotoptions):
    if n < len(plotoptions) / 2:
        color = lighten_color(colors[0], 1-(n/ (len(plotoptions)/2)))
    else:
        color= lighten_color(colors[1], n/(len(plotoptions)/2))

    if function == "cigar":
        if plot in bestworst:
            scale = 0.6
        else:
            scale = 0.2
    else:
        if plot in bestworst:
            scale = 0.3
        else:
            scale = 0.05
    sbs.pointplot(ax=ax, data = dataFrame, x = 'index', y = plot, color=color, scale=scale)


ax.set(xlabel="Generation", ylabel="Total Manhattan distance (log)", title="Katsuura")
#ax.legend(handles=ax.lines[::len(dataFrame)+1], labels=plotoptions)
#ax.set_xticklabels([t.get_text().split("T")[0] for t in ax.get_xticklabels()])
if function == "cigar":
    plt.xticks([0, 10, 20, 30, 40, 50, 60], labels=[0, 10, 20, 30, 40, 50, 60])
elif function == "schaffers":
    plt.xticks([0, 100, 200, 300, 400, 500, 600], labels=[0, 100, 200, 300, 400, 500, 600])
elif function == "katsuura":
    plt.ticks([0, np.log(2000), np.log(4000), np.log(6000), np.log(8000), np.log(10000), np.log(12000)], labels=[0, 2000, 4000, 6000, 8000, 10000, 12000])


plt.show()

import numpy as np
import pandas as pd
import seaborn as sbs
import matplotlib.pyplot as plt

#Mutopts en recopts can be used to print the full names below
options = ["dp_un", "dt_un", "aw_un", "as_un", "a1_un", "bl_un", "dp_ga", "dt_ga", "aw_ga", "as_ga", "a1_ga", "bl_ga", "dp_1", "dt_1", "aw_1", "as_1", "a1_1", "bl_1", "dp_n" , "dt_n", "aw_n", "as_n", "a1_n", "bl_n"]

colors = ["#0038a8", "#e41a1c"]

function = "katsuura"

if function == "cigar":
    plotoptions = ['dt_un', 'a1_un', 'aw_un', 'dp_un', 'bl_un', 'as_un', 'dt_n', 'dt_ga', 'a1_1', 'a1_ga', 'dt_1', 'a1_n', 'bl_n', 'dp_n', 'dp_1', 'bl_ga', 'bl_1', 'dp_ga', 'aw_ga', 'as_n', 'aw_n', 'as_ga', 'as_1', 'aw_1']
    bestworst = ["dt_un", "aw_1"] # worst and best for BentCigar

    generations = 65 #65 for BC, 665 for SCH, 6665 for KAT
    generationHelper = 68 # 68 for BC, 668 for SCH, 6668 for KAT

elif function == "schaffers":
    # ordering worst to best for SCHAFFERS
    plotoptions = ['dt_un', 'aw_un', 'a1_un', 'as_un', 'dp_un', 'dt_n', 'a1_n', 'bl_n', 'dt_ga', 'dt_1', 'dp_n', 'dp_ga', 'a1_1', 'bl_ga', 'a1_ga', 'bl_1', 'dp_1', 'as_n', 'as_ga', 'aw_ga', 'as_1', 'aw_n', 'aw_1', 'bl_un']
    bestworst = ["dt_un", "bl_un"] # worst and best for schaffers

    generations = 665 #65 for BC, 665 for SCH, 6665 for KAT
    generationHelper = 668 # 68 for BC, 668 for SCH, 6668 for KAT

elif function == "katsuura":

    # ordering worst-to-best for KATSUURA
    plotoptions = ['dt_un', 'a1_un', 'bl_un', 'dp_un', 'aw_un', 'as_un', 'bl_ga', 'bl_n', 'bl_1', 'dp_n', 'dp_ga', 'dp_1', 'aw_n', 'dt_n', 'aw_ga', 'as_n', 'dt_ga', 'aw_1', 'as_ga', 'dt_1', 'a1_n', 'as_1', 'a1_ga', 'a1_1']
    bestworst = ["dt_un", "a1_1"] # worst and best for katsuura

    generations = 6665 #65 for BC, 665 for SCH, 6665 for KAT
    generationHelper = 6668 # 68 for BC, 668 for SCH, 6668 for KAT


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
                dataFrame.at[runIndex, options[i]] += diversityValue

            else:
                sampleIndex += 1

dataFrame = dataFrame / sampleIndex

if function == "schaffers":# or function == "katsuura":
    dataFrame = pd.DataFrame(np.log(dataFrame))

dataFrame = dataFrame.reset_index()

print(dataFrame.tail())



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
    print(plot)
    if n < len(plotoptions) / 2:
        color = lighten_color(colors[0], 1-(n/ (len(plotoptions)/2)))
    else:
        color= lighten_color(colors[1],  1-( (n/2) / (len(plotoptions)/2) ))


    if plot in bestworst:
        if plot == bestworst[0]:
            color = colors[0]
        else:
            color = colors[1]
        sbs.lineplot(ax=ax, data = dataFrame, x = 'index', y = plot, color=color, linewidth=2)#marker="o")
    else:
        sbs.lineplot(ax=ax, data = dataFrame, x = 'index', y = plot, color=color, linewidth=0.05) #0.05 fot katsuura



ax.set(xlabel="Generation", ylabel="Total Manhattan distance")

if function == "cigar":
    ax.set_title("Bent Cigar", fontsize=20)
    plt.xticks([0, 10, 20, 30, 40, 50, 60], labels=[0, 10, 20, 30, 40, 50, 60])
elif function == "schaffers":
    ax.set_title("Schaffers", fontsize=20)
    plt.xticks([0, 100, 200, 300, 400, 500, 600], labels=[0, 100, 200, 300, 400, 500, 600])
elif function == "katsuura":
    ax.set_title("Katsuura", fontsize=20)
    plt.xticks([0, 1000, 2000, 3000, 4000, 5000, 6000], labels=[0, 1000, 2000, 3000, 4000, 5000, 6000])

plt.show()

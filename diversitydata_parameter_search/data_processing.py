import numpy as np
import pandas as pd

# import matplotlib
# matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
import seaborn as sbs

sbs.set_context(rc={'lines.markeredgewidth': 0.1})
#Mutopts en recopts can be used to print the full names below
options = ["s_0.01_a_0.1", "s_0.01_a_0.3", "s_0.01_a_0.5", "s_0.05_a_0.1", "s_0.05_a_0.3", "s_0.05_a_0.5", "s_0.1_a_0.1", "s_0.1_a_0.3",  "s_0.1_a_0.5", "s_0.2_a_0.1", "s_0.2_a_0.3", "s_0.2_a_0.5", "s_0.5_a_0.1", "s_0.5_a_0.3","s_0.5_a_0.5"]



colors = ["#0038a8", "#e41a1c"]

function = "katsuura"

if function == "cigar":

    plotoptions = ['as_ga s_0.01_a_0.1', 'aw_1 s_0.01_a_0.5', 'aw_1 s_0.01_a_0.3', 'aw_1 s_0.01_a_0.1', 'as_ga s_0.05_a_0.1', 'aw_1 s_0.05_a_0.5', 'as_ga s_0.01_a_0.3', 'aw_1 s_0.05_a_0.3', 'aw_1 s_0.05_a_0.1', 'as_ga s_0.01_a_0.5', 'as_ga s_0.05_a_0.3', 'aw_1 s_0.1_a_0.5', 'as_ga s_0.1_a_0.1', 'as_ga s_0.05_a_0.5', 'aw_1 s_0.1_a_0.3', 'aw_1 s_0.1_a_0.1', 'as_ga s_0.5_a_0.1', 'aw_1 s_0.5_a_0.1', 'as_ga s_0.5_a_0.3', 'as_ga s_0.5_a_0.5', 'aw_1 s_0.5_a_0.3', 'aw_1 s_0.5_a_0.5', 'as_ga s_0.1_a_0.3', 'as_ga s_0.2_a_0.1', 'as_ga s_0.1_a_0.5', 'aw_1 s_0.2_a_0.1', 'as_ga s_0.2_a_0.3', 'as_ga s_0.2_a_0.5', 'aw_1 s_0.2_a_0.5', 'aw_1 s_0.2_a_0.3']
    bestworst = ['as_ga s_0.01_a_0.1', "aw_1 s_0.2_a_0.3"] # worst and best for BentCigar
    algoptions = ["as_ga", "aw_1"]

    generations = 65 #65 for BC, 665 for SCH, 6665 for KAT
    generationHelper = 68 # 68 for BC, 668 for SCH, 6668 for KAT

elif function == "schaffers":

    plotoptions = ['bl_un s_0.5_a_0.1', 'bl_un s_0.05_a_0.1', 'bl_un s_0.2_a_0.1', 'bl_un s_0.1_a_0.1', 'bl_un s_0.01_a_0.1', 'bl_un s_0.05_a_0.3', 'bl_un s_0.5_a_0.3', 'bl_un s_0.1_a_0.3', 'bl_un s_0.01_a_0.3', 'bl_un s_0.2_a_0.3', 'bl_un s_0.5_a_0.5', 'bl_un s_0.05_a_0.5', 'bl_un s_0.01_a_0.5', 'bl_un s_0.2_a_0.5', 'bl_un s_0.1_a_0.5']
    bestworst = ['bl_un s_0.5_a_0.1', 'bl_un s_0.1_a_0.5'] # worst and best for schaffers
    algoptions = ["bl_un"]

    generations = 665 #65 for BC, 665 for SCH, 6665 for KAT
    generationHelper = 668 # 68 for BC, 668 for SCH, 6668 for KAT

elif function == "katsuura":

    plotoptions = ['a1_1 s_0.2_a_0.3', 'a1_1 s_0.2_a_0.1', 'a1_1 s_0.5_a_0.1', 'a1_1 s_0.1_a_0.3', 'a1_ga s_0.5_a_0.1', 'a1_1 s_0.1_a_0.1', 'a1_ga s_0.5_a_0.5', 'a1_ga s_0.1_a_0.5', 'a1_1 s_0.2_a_0.5', 'a1_1 s_0.05_a_0.1', 'a1_ga s_0.05_a_0.5', 'a1_1 s_0.1_a_0.5', 'a1_1 s_0.5_a_0.3', 'a1_ga s_0.1_a_0.1', 'a1_1 s_0.05_a_0.3', 'a1_1 s_0.5_a_0.5', 'a1_ga s_0.5_a_0.3', 'a1_ga s_0.2_a_0.1', 'a1_ga s_0.1_a_0.3', 'a1_ga s_0.2_a_0.5', 'a1_ga s_0.2_a_0.3', 'a1_ga s_0.05_a_0.3', 'a1_1 s_0.05_a_0.5', 'a1_ga s_0.05_a_0.1', 'a1_ga s_0.01_a_0.1', 'a1_ga s_0.01_a_0.5', 'a1_ga s_0.01_a_0.3', 'a1_1 s_0.01_a_0.1', 'a1_1 s_0.01_a_0.5', 'a1_1 s_0.01_a_0.3']

    bestworst = ['a1_1 s_0.2_a_0.3', 'a1_1 s_0.01_a_0.3'] # worst and best for katsuura
    algoptions = ["a1_ga", "a1_1"]

    generations = 6665 #65 for BC, 665 for SCH, 6665 for KAT
    generationHelper = 6668 # 68 for BC, 668 for SCH, 6668 for KAT




# quickfix ...
for n, option in enumerate(plotoptions):
    plotoptions[n] = option.replace(" ", "_")

for n, alg in enumerate(bestworst):
    bestworst[n] = alg.replace(" ", "_")


alloptions = []
for alg in algoptions:
    for opt in options:
        alloptions.append(alg+"_"+opt)

dataFrame = pd.DataFrame(columns=alloptions, index=range(0,generations+1))
dataFrame = dataFrame.fillna(0)

counter = 0
for alg in algoptions:
    print(alg)
    for i in range(15):
        sampleIndex = 0
        filename = function + "_" + alg + "_" + str(i) + ".txt"

        with open(filename, 'r') as f:
            lines = f.readlines()
            for n, line in enumerate(lines):

                runIndex = n % generationHelper

                if not "Score" in line and not "Runtime" in line:
                    diversityValue = float(line)
                    dataFrame.at[runIndex, alloptions[counter]] += diversityValue

                else:
                    sampleIndex += 1
        counter += 1

dataFrame = dataFrame / sampleIndex

# if function == "schaffers":# or function == "katsuura":
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
        color = lighten_color(colors[0], (n/(len(plotoptions)/2)))
    else:
        color = lighten_color(colors[1], 1-( (n/2) / (len(plotoptions)/2) ))


    if plot in bestworst:
        if plot == bestworst[0]:
            color = colors[0]
        else:
            color = colors[1]
        sbs.lineplot(ax=ax, data = dataFrame, x = 'index', y = plot, color=color, linewidth=2)#marker="o")
        ax.set_xlabel('Generation', fontsize=12)
        ax.set_ylabel('Total Manhatten distance (log)', fontsize=12)
        ax.tick_params(labelsize=12)
        ax.set_ylim(6.1, 12.3)
    else:
        sbs.lineplot(ax=ax, data = dataFrame, x = 'index', y = plot, color=color, linewidth=0.05) #0.05 for katsuura


if function == "cigar":
    ax.set_title("Bent Cigar", fontsize=20)
    # plt.xticks([0, 10, 20, 30, 40, 50, 60], labels=[0, 10, 20, 30, 40, 50, 60])
elif function == "schaffers":
    ax.set_title("Schaffers", fontsize=20)
    # plt.xticks([0, 100, 200, 300, 400, 500, 600], labels=[0, 100, 200, 300, 400, 500, 600])
elif function == "katsuura":
    ax.set_title("Katsuura", fontsize=20)
    # plt.xticks([0, 1000, 2000, 3000, 4000, 5000, 6000], labels=[0, 1000, 2000, 3000, 4000, 5000, 6000])

plt.tight_layout()
plt.show()

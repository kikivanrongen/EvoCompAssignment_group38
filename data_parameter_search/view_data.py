import matplotlib
matplotlib.use("TkAgg")
import matplotlib.pyplot as plt
import seaborn as sns

import numpy as np
import scipy as sp
import csv
import sys

# Define all filenames data is stored in
filenames_c_asga = []
filenames_c_aw1 = []
filenames_s = []
filenames_k_a11 = []
filenames_k_a1ga = []

for i in range(15):
    filenames_c_asga.append("data_parameter_search/cigar_as_ga_" + str(i) + ".txt")
    filenames_c_aw1.append("data_parameter_search/cigar_aw_1_" + str(i) + ".txt")
    filenames_s.append("data_parameter_search/schaffers_bl_un_" + str(i) + ".txt")
    filenames_k_a11.append("data_parameter_search/katsuura_a1_1_" + str(i) + ".txt")
    filenames_k_a1ga.append("data_parameter_search/katsuura_a1_ga_" + str(i) + ".txt")


# Define variable data will be stored in (mean/std, recombination, mutation)
data = np.ndarray((5, 2, 3, 5))

for files, filenames in enumerate([filenames_c_asga,
                                    filenames_c_aw1,
                                    filenames_s,
                                    filenames_k_a11,
                                    filenames_k_a1ga]):
    # Load data from cigar files
    for i, item in enumerate(filenames):

        # Open file
        with open(item, "r") as csvfile:
            score = []
            spamreader = csv.reader(csvfile, delimiter="\n")
            for j, row in enumerate(spamreader):
                if j % 2 == 0:
                    score.append(float(row[0][7::]))

            score = np.array(score)

            # print(item, np.mean(score))

            # print(score)
            data[files, 0, i%3, i//3] = np.mean(score)
            data[files, 1, i%3, i//3] = sp.stats.sem(score)

data = data.transpose((0, 1, 3, 2))
sns.set(font_scale = 1.2)

y_labels = ["0.01", "0.05", "0.1", "0.2", "0.5"]
x_labels = ["0.1", "0.3", "0.5"]
titles = ["Cigar as-ga", "Cigar aw-1", "Schaffers bl-un", "Katsuura a1-1", "Katsuura a1-ga"]


f, axes = plt.subplots(1, 5, figsize=(12, 3), sharex=True, sharey=False)
sns.set_context("notebook", rc={"axes.labelsize":36})
for i in range(5):
    if i == 2:
        pass
    else:
        sns.heatmap(data[i,0], annot=True, vmin=0, vmax=10, ax=axes[i if i < 2 else i-1],
                        xticklabels=x_labels, yticklabels=y_labels,
                        cbar=True)# if i != 4 else True)
        if i == 0:
            axes[i if i < 2 else i-1].set(ylabel=r"$\sigma$")

        axes[i if i < 2 else i-1].set(title=titles[i], xlabel=r"$\alpha$")
        axes[i if i < 2 else i-1].invert_yaxis()


# sns.set(font_scale = 1.2)
# f, axes = plt.subplots(1, 1, figsize=(2, 3), sharex=True, sharey=True)
sns.heatmap(data[2,0], annot=True, vmin=0, vmax=10, ax=axes[4],
                xticklabels=x_labels, yticklabels=y_labels,
                cbar=True)

axes[4].set(title=titles[2], xlabel=r"$\alpha$", ylabel="Threshold")
axes[4].invert_yaxis()

f.tight_layout()
plt.show()

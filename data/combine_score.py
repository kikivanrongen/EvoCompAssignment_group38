import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
import seaborn as sns


import numpy as np
import scipy as sp
import csv
import sys

# Create all possible combinations of functions
# parents = np.array([0])
# recobinations = np.linspace(0, 5, 6, dtype=int)
# mutations = np.linspace(0, 3, 4, dtype=int)
# survival = np.array([2])
# x = np.transpose(np.meshgrid(parents, recobinations, mutations, survival)).reshape(-1, 4)

# Define all filenames data is stored in
filenames_cigar = []
filenames_schaffers = []
filenames_katsuura = []
for i in range(24):#, item in enumerate(x):
    # strings = [str(y) for y in item]
    # run = ''.join(strings)
    # filenames_cigar.append('cigar_' + run + '.txt')
    # filenames_schaffers.append('schaffers_' + run + '.txt')
    filenames_cigar.append('data_standard_parameters/cigar_' + str(i) + '.txt')
    filenames_schaffers.append('data_standard_parameters/schaffers_' + str(i) + '.txt')
    filenames_katsuura.append('data_standard_parameters/katsuura_' + str(i) + '.txt')
# print(filenames)

# Define variable data will be stored in (mean/std, recombination, mutation)
data_cigar = np.ndarray((2, 6, 4))
data_schaffers = np.ndarray((2, 6, 4))
data = np.ndarray((3, 2, 6, 4))

for files, filenames in enumerate([filenames_cigar,
                                    filenames_schaffers,
                                    filenames_katsuura]):
    # Load data from cigar files
    for i, item in enumerate(filenames):

        # Open file
        with open(item, 'r') as csvfile:
            score = []
            spamreader = csv.reader(csvfile, delimiter='\n')
            for j, row in enumerate(spamreader):
                if j % 2 == 0:
                    score.append(float(row[0][7::]))

            score = np.array(score)

            # print(score)
            data[files, 0, i%6, i//6] = np.mean(score)
            data[files, 1, i%6, i//6] = sp.stats.sem(score)


sns.set(font_scale = 1.2)
x_labels = ["uniform", "gauss", "onestep", "nstep"]
y_labels = ["discrete pointwise", "discrete tailswap", "arithmetic whole", "arithmetic simple", "arithmetic single", "blend crossover"]
f, axes = plt.subplots(1, 3, figsize=(12, 3), sharex=True, sharey=True)
# sns.set_context("notebook", rc={"axes.labelsize":36})

sns.heatmap(data[0,0], annot=True, vmin=0, vmax=10, ax=axes[0], xticklabels=x_labels, yticklabels=y_labels)
# sns.heatmap(data[0,1], annot=True, vmin=0, vmax=1, ax=axes[1,0], xticklabels=x_labels, yticklabels=y_labels)
sns.heatmap(data[1,0], annot=True, vmin=0, vmax=10, ax=axes[1], xticklabels=x_labels, yticklabels=y_labels)
# sns.heatmap(data[1,1], annot=True, vmin=0, vmax=1, ax=axes[1,1], xticklabels=x_labels, yticklabels=y_labels)
sns.heatmap(data[2,0], annot=True, vmin=0, vmax=10, ax=axes[2], xticklabels=x_labels, yticklabels=y_labels)

axes[0].set(title="Bent Cigar")
axes[1].set(title="Schaffers")
axes[2].set(title="Katsuura")
# axes[1,0].set(title="Bent Cigar - std")
# axes[1,1].set(title="Schaffers - std")

# f, ax = plt.subplots(figsize=(12, 6))
# sns.heatmap(data_cigar[0], annot=True, vmin=0, vmax=10, ax=ax, xticklabels=x_labels, yticklabels=y_labels)
plt.show()

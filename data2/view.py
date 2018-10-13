import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
import seaborn as sns
sns.set()

import numpy as np
import csv
import sys

# Create all possible combinations of functions
alpha = np.linspace(0, 5, 6, dtype=int)
sigma = np.linspace(0, 5, 6, dtype=int)
alg = np.linspace(0, 23, 24, dtype=int)
x = np.transpose(np.meshgrid(alg, alpha, sigma)).reshape(-1, 3)
y = np.linspace(0, len(x) - 1, len(x), dtype=int).tolist()


# Define all filenames data is stored in
filenames_cigar = []
filenames_schaffers = []
for i, item in enumerate(y):
    # print(item)
    # strings = [str(z) for z in item]/
    # run = ''.join(strings)
    filenames_cigar.append('cigar_' + str(item) + '.txt')
    # filenames_schaffers.append('schaffers_' + run + '.txt')
# print(filenames)

# Define variable data will be stored in (mean/std, recombination, mutation)
data_cigar = np.ndarray((2, 24, 6, 6))
# data_schaffers = np.ndarray((2, 6, 4))
# print(x)
# Load data from cigar files
for i, item in enumerate(filenames_cigar):

    # Open file
    with open(item, 'r') as csvfile:
        score = []
        spamreader = csv.reader(csvfile, delimiter='\n')
        for j, row in enumerate(spamreader):
            if j % 2 == 0:
                score.append(float(row[0][7::]))

        score = np.array(score)
        # print(x[i])
        # print(x[i, 0], x[i, 1], x[i, 2])

        # print(item)
        # print('mean:', np.mean(score), '|| std:', np.std(score))
        # data_cigar[1, int(item[7]), int(item[8])] = np.std(score)
        data_cigar[0, x[i,0], x[i, 1], x[i, 2]] = np.mean(score)
        data_cigar[1, x[i,0], x[i, 1], x[i, 2]] = np.std(score)



# Load data from all files
# for i, item in enumerate(filenames_schaffers):
#     try:
#         # Open file
#         with open(item, 'r') as csvfile:
#             score = []
#             spamreader = csv.reader(csvfile, delimiter='\n')
#             for i, row in enumerate(spamreader):
#                 if i % 2 == 0:
#                     score.append(float(row[0][7::]))
#
#             score = np.array(score)
#
#             # print(item)
#             # print('mean:', np.mean(score), '|| std:', np.std(score))
#             data_schaffers[0, int(item[11]), int(item[12])] = np.mean(score)
#             data_schaffers[1, int(item[11]), int(item[12])] = np.std(score)
#
#     except:
#         continue
data = data_cigar.reshape(2, 24, -1)
# print(data.shape)

f, axes = plt.subplots(1, 1, figsize=(24, 12))
sns.heatmap(data[0], vmin=0, vmax=10, ax=axes, annot=True)

# print(data_cigar.reshape(2, 24, -1))
# x_labels = ["uniform", "gauss", "onestep", "nstep"]
# y_labels = ["discrete pointwise", "discrete tailswap", "arithmetic whole", "arithmetic simple", "arithmetic single", "blend crossover"]
# f, axes = plt.subplots(2, 2, figsize=(12, 12), sharex=True, sharey=True)
#
# sns.heatmap(data_cigar[0], annot=True, vmin=0, vmax=10, ax=axes[0,0], xticklabels=x_labels, yticklabels=y_labels)
# sns.heatmap(data_cigar[1], annot=True, vmin=0, vmax=1, ax=axes[1,0], xticklabels=x_labels, yticklabels=y_labels)
# sns.heatmap(data_schaffers[0], annot=True, vmin=0, vmax=10, ax=axes[0,1], xticklabels=x_labels, yticklabels=y_labels)
# sns.heatmap(data_schaffers[1], annot=True, vmin=0, vmax=1, ax=axes[1,1], xticklabels=x_labels, yticklabels=y_labels)
#
# axes[0,0].set(title="Bent Cigar - score")
# axes[0,1].set(title="Schaffers - score")
# axes[1,0].set(title="Bent Cigar - std")
# axes[1,1].set(title="Schaffers - std")
#
# # f, ax = plt.subplots(figsize=(12, 6))
# # sns.heatmap(data_cigar[0], annot=True, vmin=0, vmax=10, ax=ax, xticklabels=x_labels, yticklabels=y_labels)
plt.show()

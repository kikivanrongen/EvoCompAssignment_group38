import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt

import numpy as np
import csv
import sys

with open(sys.argv[1], 'r') as csvfile:
    score = []
    spamreader = csv.reader(csvfile, delimiter='\n')
    for i, row in enumerate(spamreader):
        if i % 2 == 0:
            score.append(float(row[0][7::]))

    score = np.array(score)
    print('mean:', np.mean(score), '|| std:', np.std(score))

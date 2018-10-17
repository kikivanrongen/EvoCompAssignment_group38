import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt

import numpy as np
import pandas as pd
import csv
import sys

scores = []

for index in range(15):
    name = 'schaffers_bl_un_{i}.txt'.format(i=index)

    with open(name, 'r') as csvfile:
        score = []
        spamreader = csv.reader(csvfile, delimiter='\n')
        for i, row in enumerate(spamreader):
            if i % 2 == 0:
                score.append(float(row[0][7::]))

        score = np.array(score)
        print('mean:', np.mean(score), '|| std:', np.std(score))
        scores.append(score)

with open('schaffers_bl_un_par.csv', 'w') as fp:
    writer = csv.writer(fp, delimiter=',')
    for ar in scores:
        writer.writerows(zip(ar))

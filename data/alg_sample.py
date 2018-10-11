import numpy as np

parents = np.array([0])
recobinations = np.linspace(0, 5, 6, dtype=int)
mutations = np.linspace(0, 3, 4, dtype=int)
survival = np.array([2])

print(parents, recobinations, mutations, survival)
x = np.transpose(np.meshgrid(parents, recobinations, mutations, survival)).reshape(-1, 4)
print(x)

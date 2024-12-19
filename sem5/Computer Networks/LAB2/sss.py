import numpy as np

Ht = [[1, 0, 1],
      [1, 1, 1],
      [1, 1, 0],
      [0, 1, 1],
      [1 ,0, 0],
      [0, 1, 0],
      [0, 0, 1]]

def transpose(M):
    return [[M[j][i] for j in range(len(M))] for i in range(len(M[0]))]

H = transpose(Ht)
q,n= len(H),len(H[0])
k=n-q

Pt = [[H[i][j] for j in range(k)] for i in range(q)]
P = transpose(Pt)

def standardize(q):
    for i in range(len(q)):
        q[i] %= 2

for m1 in range(2):
    for m2 in range(2):
        for m3 in range(2):
            for m4 in range(2):
                m = [m1, m2, m3,m4]  # Vector with 3 elements to match P's dimensions
                q = np.dot(m, P)
                standardize(q)
                print(q)

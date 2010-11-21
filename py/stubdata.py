
'''

  A           X
      G
B          Y

'''

import math, random


def dist(x, y):
    return math.sqrt((x[0] - y[0])**2 +
                     (x[1] - y[1])**2)


A = [40.732190, -73.998569]
B = [40.731021, -73.999604]
X = [40.730698, -73.995551]
Y = [40.729556, -73.996562]
G = [40.730765, -73.997413]

PARK = (dist(X, Y) + dist(A, B)) / 2

def coordStr(x):
    return '%.9f' % x


def line(x, y, n=10):
    arr = []
    for i in range(n):
        t = float(i) / (n - 1)
        arr.append([
            coordStr(x[0] + (y[0] - x[0]) * t),
            coordStr(x[1] + (y[1] - x[1]) * t),
        ])
    return arr


def solidSquare(x, size, n):
    arr = []
    for i in xrange(n):
        arr.append([
            coordStr(x[0] + ((random.random() * size) - (0.5 * size))),
            coordStr(x[1] + ((random.random() * size) - (0.5 * size))),
        ])
    return arr


def points(xs):
    arr = []
    for x in xs:
        arr.append([
            coordStr(x[0]),
            coordStr(x[1])
        ])
    return arr


STUB_MAP_INFO = {
    'dotPoints': line(X, Y) + line(A, B) + line(A, X) + line(B, Y),
    'basePoints': solidSquare(G, PARK / 4, 50),
    'powerPelletPoints': points([X, Y, A, B]),
}


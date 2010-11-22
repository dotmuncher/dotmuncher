
'''
    A           
             
       GGG       X
B     GGG    

            Y
'''

import math, random


def dist(x, y):
    return math.sqrt((x[0] - y[0])**2 +
                     (x[1] - y[1])**2)


A = [40.732161, -73.99853]
B = [40.731031, -73.99959]
X = [40.730714, -73.99562]
Y = [40.729625, -73.99654]
G = [40.730765, -73.997413]

PARK = (dist(X, Y) + dist(A, B)) / 2

def coordStr(x):
    return '%.9f' % x


def line(x, y, n):
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
    'dotPoints': line(X, Y, 10) + line(A, B, 10) + line(A, X, 20) + line(B, Y, 20),
    'basePoints': solidSquare(G, PARK / 4, 50),
    'powerPelletPoints': points([X, Y, B]),
}


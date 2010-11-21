
'''

             X

          Y
'''

X = [40.730667,-73.995632]
Y = [40.729499, -73.996587]


def coordStr(x):
    return '%.9f' % x


def makePoints(x, y, n=10):
    arr = []
    for i in range(n):
        t = float(i) / (n - 1)
        p = [
            coordStr(x[0] + (y[0] - x[0]) * t),
            coordStr(x[1] + (y[1] - x[1]) * t),
        ]
        arr.append(p)
    return arr


STUB_MAP_INFO = {
    'dotPoints': makePoints(X, Y),
    'basePoints': [
        
    ],
    'powerPelletPoints': [
        
    ],
}


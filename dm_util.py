
import struct

import keyjson


def invertedDict(d):
    d2 = {}
    for k, v in d.items():
        d2[v] = k
    return d2


def coordScore(coord):
    n = int(float(coord) + 200) * 2**62 / 400
    data = struct.pack('>Q', n)
    return keyjson.b64encode(data)



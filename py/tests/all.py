
# Add ROOT to sys.path
import sys, os
def parentOf(path, n=1):
    return '/'.join(path.rstrip('/').split('/')[:-n])
ROOT = parentOf(os.path.abspath(__file__), n=4)
sys.path.append(ROOT)


import unittest

from dotmuncher.py.tests.system import *


if __name__ == '__main__':
    unittest.main()

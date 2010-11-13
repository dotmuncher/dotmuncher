
# Add ROOT to sys.path
def parentOf(path, n=1):
    return '/'.join(path.rstrip('/').split('/')[:-n])
ROOT = parentOf(os.path.abspath(__file__), n=3)
import sys
sys.path.append(ROOT)



import unittest

from dotmuncher.tests.system import *


if __name__ == '__main__':
    unittest.main()

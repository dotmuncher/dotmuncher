
import os, sys


os.environ['APP'] = 'dotmuncher'


def parentOf(path, n=1):
    return '/'.join(path.rstrip('/').split('/')[:-n])
REPO = parentOf(os.path.abspath(__file__), n=1)
sys.path.append('%s/lib/redis-py' % REPO)


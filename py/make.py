from __future__ import absolute_import

import os
os.environ['APP'] = 'dotmuncher'

import sys
from subprocess import check_call

import pj.api

from dotmuncher.py.dm_util import simpleGet
from dotmuncher.urls import REPO


def main():
    
    for (type, filename) in (
            ('js', 'dotmuncher.js'),
            ('css', 'dotmuncher.css')):
        destDir = '%s/static/%s' % (REPO, type)
        check_call(['mkdir', '-p', destDir])
        sys.stdout.write('%s... ' % filename)
        
        data = simpleGet('http://localhost:8000/static/%s/%s' % (type, filename))
        
        if type == 'js':
            #data = pj.api.closureCompile(data, 'simple')
            data = pj.api.closureCompile(data, 'pretty')
            #sys.path.append('TODO/gordian-minifier')
            #import gordian_minifier
            #data = gordian_minifier.minify(data)
        
        destPath = '%s/%s' % (destDir, filename)
        with open(destPath, 'wb') as f:
            f.write(data)
        sys.stdout.write('done.\n')
    


if __name__ == '__main__':
    main()

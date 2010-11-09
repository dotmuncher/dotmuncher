from __future__ import absolute_import

import os
os.environ['APP'] = 'dotmuncher'

import sys
from subprocess import check_call

import pj.api

from dotmuncher.dm_util import simpleGet


def main():
    
    destDir = 'static/js'
    
    check_call(['mkdir', '-p', destDir])
    
    for filename in ['dotmuncher.js']:
        sys.stdout.write('%s... ' % filename)
        
        js = simpleGet('http://localhost:8000/static/js/dotmuncher.js')
        
        #js = pj.api.closureCompile(js, 'simple')
        js = pj.api.closureCompile(js, 'pretty')
        
        #sys.path.append('TODO/gordian-minifier')
        #import gordian_minifier
        #js = gordian_minifier.minify(js)
        
        destPath = '%s/%s' % (destDir, filename)
        with open(destPath, 'wb') as f:
            f.write(js)
        
        sys.stdout.write('done.\n')


if __name__ == '__main__':
    main()

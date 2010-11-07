from __future__ import absolute_import

import sys
from subprocess import check_call

import pj.api

#LATER: generalize
sys.path.append('/Users/a/repos/gordian-minifier')
import gordian_minifier

from util.slurp import simpleGet


def main():
    
    destDir = 'static/js'
    
    check_call(['mkdir', '-p', destDir])
    
    for filename in ['dotmuncher.js']:
        sys.stdout.write('%s... ' % filename)
        
        js = simpleGet('http://localhost:8000/static/js/dotmuncher.js')
        
        js = pj.api.closureCompile(js, 'simple')
        
        js = gordian_minifier.minify(js)
        
        destPath = '%s/%s' % (destDir, filename)
        with open(destPath, 'wb') as f:
            f.write(js)
        
        sys.stdout.write('done.\n')


if __name__ == '__main__':
    main()

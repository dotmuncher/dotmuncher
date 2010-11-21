
import os
from subprocess import check_call


def main():
    
    ROOT = parentOf(os.path.abspath(__file__), n=3)
    
    for repoName in ['pyxc-pj', 'dev_deployment', 'pj-core', 'pj-closure']:
        repoUrl = 'http://github.com/andrewschaaf/%s.git' % repoName
        check_call(['git', 'clone', repoUrl], cwd=ROOT)
    
    print('Done.')


def parentOf(path, n=1):
    return '/'.join(path.rstrip('/').split('/')[:-n])


if __name__ == '__main__':
    main()

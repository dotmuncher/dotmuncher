
import struct, urllib, urllib2, json, sys, traceback, StringIO, random

import keyjson

from django.http import HttpResponse
from django.shortcuts import render_to_response



TOKEN_ALPHABET = ''.join(
                        c
                        for c in set('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789')
                        if c not in set('Iil1 Oo0D'))

def randomToken(n):
    return ''.join(random.choice(TOKEN_ALPHABET) for i in range(n))




def jsonView(**outerKwargs):
    def outer(f):
        def f2(r, *args, **kwargs):
            x = f(r, *args, **kwargs)
            if isinstance(x, HttpResponse):
                return x
            else:
                return jsonReponse(r, x)
        return f2
    return outer


def jsonReponse(r, x):
    b = json.dumps(x)
    if 'jsonp' in r.GET:
        b = r.GET['jsonp'] + '(' + b + ')'
    response = HttpResponse(b, mimetype='text/javascript')
    response['Pragma'] = 'no-cache'
    response['Cache-Control'] = 'max-age=0, no-cache, no-store'
    return response


def view(templateName):
    def outer(f):
        def f2(r, *args, **kwargs):
            x = f(r, *args, **kwargs)
            if isinstance(x, HttpResponse):
                response = x
            else:
                x = x or {}
                response = render_to_response(templateName, x)
            return response
        return f2
    return outer


def invertedDict(d):
    d2 = {}
    for k, v in d.items():
        d2[v] = k
    return d2


def ppJsonDumps(x, indent=2):
    return json.JSONEncoder(
                    ensure_ascii=True,
                    allow_nan=False,
                    sort_keys=True,
                    indent=indent,
                    separators=(', ', ': ')).encode(x)


def exceptionStr(exc_info=None):
    if not exc_info:
        exc_info = sys.exc_info()
    f = StringIO.StringIO()
    traceback.print_exception(exc_info[0], exc_info[1], exc_info[2], file=f)
    return f.getvalue()


def coordScore(coord):
    n = int(float(coord) + 200) * 2**62 / 400
    data = struct.pack('>Q', n)
    return keyjson.b64encode(data)


def encodeURIComponent(s):
    if isinstance(s, unicode):
        return urllib.quote(s.encode('utf-8'))
    if isinstance(s, str):
        return urllib.quote(s)
    elif isinstance(s, int) or isinstance(s, long):
        return str(s)
    else:
        raise Exception(s)


def simpleGet(url, GET=None, userAgent='Python-urllib/2.6'):
    
    if GET:
        url += '?' + '&'.join((k + '=' + encodeURIComponent(v)) for k, v in GET.items())
    req = urllib2.Request(url, headers={
        'User-Agent': userAgent,
    })
    response = urllib2.urlopen(req)
    b = response.read()
    return b


def simplePost(url, POST={}):
    POST = dict((str(k), str(v)) for k, v in POST.items())
    data = urllib.urlencode(POST)
    req = urllib2.Request(url, data)
    response = urllib2.urlopen(req)
    data = response.read()
    return data



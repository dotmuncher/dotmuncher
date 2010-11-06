
from __future__ import absolute_import

import datetime, time, copy, json, struct, random

from django.db import models
from django.core.cache import cache

from util.random import randomToken

from dotmuncher.util import invertedDict


TABLE_PREFIX = 'dotmuncher_'


TYPENAME_TYPENUM_MAP = {
    'POSITION_EVENT': 1,
}
TYPENUM_TYPENAM_MAP = invertedDict(TYPENAME_TYPENUM_MAP)



class Event(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'event'
    
    gameId = models.IntegerField(default=-1)
    eventType = models.IntegerField()
    eventJson = models.CharField(max_length=1000)
    
    @property
    def name(self):
        return TYPENUM_TYPENAM_MAP[self.eventType]


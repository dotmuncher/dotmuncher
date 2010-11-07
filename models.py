
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


class Phone(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'phone'
    
    token = models.CharField(max_length=50, unique=True)
    name_utf8 = models.CharField(max_length=100)


class Map(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'map'
    
    token = models.CharField(max_length=50, unique=True)
    createdAtUtc = models.DateTimeField()
    
    deleted = models.BooleanField(default=False)
    completed = models.BooleanField(default=False)
    
    lat = models.FloatField(null=True)
    lng = models.FloatField(null=True)
    
    infoJson = models.TextField(null=True)
    
    @property
    def info(self):
        return json.loads(self.infoJson)


class Game(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'game'
    
    token = models.CharField(max_length=50, unique=True)
    createdAtUtc = models.DateTimeField()
    
    map = models.ForeignKey(Map)
    
    infoJson = models.TextField(null=True)
    
    @property
    def info(self):
        return json.loads(self.infoJson)


class Event(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'event'
    
    gameId = models.IntegerField(default=-1)
    eventType = models.IntegerField()
    eventJson = models.CharField(max_length=1000)
    
    @property
    def name(self):
        return TYPENUM_TYPENAM_MAP[self.eventType]


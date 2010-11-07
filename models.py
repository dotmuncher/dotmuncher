
from __future__ import absolute_import

import datetime, time, copy, json, struct, random

from django.db import models
from django.core.cache import cache
from base64 import b64encode, b64decode

from util.random import randomToken

from dotmuncher.dm_util import invertedDict


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
    # utf8_64
    
    @classmethod
    def forToken(cls, token):
        
        try:
            m = cls.objects.get(token=token)
        except cls.DoesNotExist:
            m = cls(
                    token=token,
                    name_utf8=b64encode(u''.encode('utf-8')))
            m.save()
        
        return m


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
        return json.loads(self.infoJson) if self.infoJson else {}
    
    @classmethod
    def create(cls):
        m = cls(
                token=randomToken(8),
                createdAtUtc=datetime.datetime.utcnow(),
                infoJson=json.dumps({
                    'points': [],
                }))
        m.save()
        return m


class Game(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'game'
    
    token = models.CharField(max_length=50, unique=True)
    createdAtUtc = models.DateTimeField()
    
    map = models.ForeignKey(Map)
    
    infoJson = models.TextField(null=True)
    
    @classmethod
    def create(cls, map):
        m = cls(
                token=randomToken(8),
                createdAtUtc=datetime.datetime.utcnow(),
                map=map)
        m.save()
        return m
    
    @property
    def info(self):
        return json.loads(self.infoJson) if self.infoJson else {}


class Event(models.Model):
    
    class Meta:
        db_table = TABLE_PREFIX + 'event'
    
    gameId = models.IntegerField(default=-1)
    eventType = models.IntegerField()
    eventJson = models.CharField(max_length=1000)
    
    @property
    def name(self):
        return TYPENUM_TYPENAM_MAP[self.eventType]


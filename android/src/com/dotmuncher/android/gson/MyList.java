package com.dotmuncher.android.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.InstanceCreator;

// http://sites.google.com/site/gson/gson-user-guide#TOC-InstanceCreator-for-a-Parameterized
class MyList<T> extends ArrayList<T> {
}

class MyListInstanceCreator implements InstanceCreator<MyList<?>> {
    @SuppressWarnings("unchecked")
  public MyList<?> createInstance(Type type) {
    // No need to use a parameterized list since the actual instance will have the raw type anyway.
    return new MyList();
  }
}


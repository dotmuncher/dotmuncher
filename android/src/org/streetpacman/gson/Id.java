package org.streetpacman.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class Id<T> {
	  private final Class<T> classOfId;
	  private final int value;
	  public Id(Class<T> classOfId, int value) {
	    this.classOfId = classOfId;
	    this.value = value;
	  }
	  
}

/*
class IdInstanceCreator implements InstanceCreator<Id<?>> {
	public Id<?> createInstance(Type type) {
	  Type[] typeParameters = ((ParameterizedType)type).getActualTypeArguments();
	  Type idType = typeParameters[0]; // Id has only one parameterized type T
	  return Id.get((Class)idType, 0);
	}
}
*/
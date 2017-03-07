package com.redhat.empowered.generic.helpers;

public class InstanceFactory {

	public static Object createObject(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{

		Class classDefinition = Class.forName(className);
		Object object = classDefinition.newInstance();
		return object;

	}

}

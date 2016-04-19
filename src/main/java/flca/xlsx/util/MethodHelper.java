package flca.xlsx.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class MethodHelper {

	// private static final Logger LOGGER =
	// Logger.getLogger(MethodHelper.class.getName()) ;

	/*
	 * key is the classname, values are all properties that have getters and
	 * setters.
	 */
	private static Map<Class<?>, Map<String, PropertyDescriptor>> classProperties = new HashMap<Class<?>, Map<String,PropertyDescriptor>>();

	private MethodHelper() {
	}

	public static Set<String> getAllProperties(Object aSourceObject) throws IntrospectionException {
		return getAllProperties(aSourceObject.getClass());
	}

	public static Set<String> getAllProperties(Class<?> aClass) throws IntrospectionException {
		fillMapIfNeeded(aClass);
		return classProperties.get(aClass).keySet();
	}

	public static Method getGetter(Object aSourceObject, String aPropertyName) throws IntrospectionException {
		return getGetter(aSourceObject.getClass(), aPropertyName);
	}

	public static Method getGetter(Class<?> aClass, String aPropertyName) throws IntrospectionException {
		fillMapIfNeeded(aClass);

		if (classProperties.get(aClass).containsKey(aPropertyName)) {
			return classProperties.get(aClass).get(aPropertyName).getReadMethod();
		} else {
			return null;
		}
	}

	public static Method getSetter(Object aSourceObject, String aPropertyName) throws IntrospectionException {
		return getSetter(aSourceObject.getClass(), aPropertyName);
	}

	public static Method getSetter(Class<?> aClass, String aPropertyName) throws IntrospectionException {
		fillMapIfNeeded(aClass);

		if (classProperties.get(aClass).containsKey(aPropertyName)) {
			return classProperties.get(aClass).get(aPropertyName).getWriteMethod();
		} else {
			return null;
		}
	}

	public static Class<?> getActualOrGenericType(final PropertyDescriptor propDescriptor) {
		Class<?> gentyp = getGenericType(propDescriptor.getWriteMethod());
		if (gentyp == null) {
			return propDescriptor.getPropertyType();
		} else {
			return gentyp;
		}
	}
	
	private static Class<?> getGenericType(Method setter) {
		if (setter != null) {
			Type[] genericParameterTypes = setter.getGenericParameterTypes();
			for (int i = 0; i < genericParameterTypes.length; i++) {
				if (genericParameterTypes[i] instanceof ParameterizedType) {
					Type[] parameters = ((ParameterizedType) genericParameterTypes[i]).getActualTypeArguments();
					if (parameters != null && parameters.length == 1) {
						return (Class<?>) parameters[0];
					}
				}
			}
		}
		return null;
	}
	
	
	private static void fillMapIfNeeded(Class<?> aClass) throws IntrospectionException {
		if (!classProperties.containsKey(aClass)) {
			classProperties.put(aClass, getProperties(aClass));
		}
	}

	private static Map<String, PropertyDescriptor> getProperties(Class<?> clz) throws IntrospectionException {
		Map<String, PropertyDescriptor> r = new HashMap<String, PropertyDescriptor>();
		for (PropertyDescriptor prop : Introspector.getBeanInfo(clz, Object.class).getPropertyDescriptors()) {
			r.put(prop.getName(), prop);
		}
		return r;
	}

}
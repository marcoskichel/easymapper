package util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reflection {
    
    public static <E> E newInstance(final Class<E> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public static List<Field> getAllFields(Class<?> clazz) {
		List<Class<?>> classes = getAllSuperclasses(clazz);
		classes.add(clazz);
		return Arrays.asList(getAllFields(classes));
	}
	
	public static Object getValue(Field f, Object bean) {
		f.setAccessible(true);
		try {
		    return f.get(bean);
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	}
	
	public static void setValue(Field f, Object fieldValue, Object bean) {
		f.setAccessible(true);
		try {
            f.set(bean, fieldValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public static Field getFieldByName(String name, Object bean) throws NoSuchFieldException {
		List<Field> fields = getAllFields(bean.getClass());
		for (Field field : fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		throw new NoSuchFieldException();
	}
	
	public static boolean containsFieldWithSameName(String fieldName, Object bean) {
		List<Field> fields = getAllFields(bean.getClass());
		for (Field f : fields) {
			if (f.getName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}
	
	private static Field[] getAllFields(List<Class<?>> classes) {
		Set<Field> fields = new HashSet<Field>();
		for (Class<?> clazz : classes) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		return fields.toArray(new Field[fields.size()]);
	}

	public static Field getField(String fieldName, Class<?> clazz) throws NoSuchFieldException {
		List<Field> fields = getAllFields(clazz);
		for (Field field : fields) {
			if(field.getName().equals(fieldName))
				return field;
		}
		throw new NoSuchFieldException();
	}
	
	public static List<Class<?>> getAllSuperclasses(Class<?> clazz) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		Class<?> superclass = clazz.getSuperclass();
		while (superclass != null) {
			classes.add(superclass);
			superclass = superclass.getSuperclass();
		}
		return classes;
	}
	
	public static <X> boolean equalLists(List<X> one, List<X> two){     
	    if (one == null && two == null){
	        return true;
	    }

	    if((one == null && two != null) 
	      || one != null && two == null
	      || one.size() != two.size()) {
	        return false;
	    }
	    
	    return one.containsAll(two) && two.containsAll(one);
	}
	
	public static boolean isPlain(final Class<?> type) {
	    return (type.isPrimitive() && type != void.class) || Collection.class.isAssignableFrom(type) || type.isArray() ||
	        type == Double.class || type == Float.class || type == Long.class || 
	        type == Integer.class || type == Short.class || type == Character.class ||
	        type == Byte.class || type == Boolean.class || type == String.class;
	}
	
	public static boolean isStatic(final Field field) {
		return Modifier.isStatic(field.getModifiers());
	}
	
}
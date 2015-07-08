package mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Reflection;

public class EasyMapper {
	
	private Map<String, Object> fieldValues;
	
	@SuppressWarnings("unchecked")
	public <T, E> void map(final T from, final Class<E> toClass) {
		Class<T> fromClass = (Class<T>) from.getClass();
		
		E to = Reflection.newInstance(toClass);
		
		List<Field> fromFields = Reflection.getAllFields(fromClass);
		List<Field> toFields = Reflection.getAllFields(toClass);
		
		look4SimpleMatches(from, to, fromFields, toFields);
		look4ComposeMatches(from, to, fromFields, toFields);
	}

	private <T, E> void look4SimpleMatches(final T from, E to, List<Field> fromFields, List<Field> toFields) {
		List<Field> toRemove = new ArrayList<>();
		for (Field field : fromFields) {
			if (containsFieldNamed(field.getName(), toFields)) {
                Reflection.setValue(field, to, from);
				toRemove.add(field);
			}
		}
		fromFields.removeAll(toFields);
	}
	
	private <T, E> void look4ComposeMatches(final T from, E to, List<Field> fromFields, List<Field> toFields) {
		fieldValues = new HashMap<>();
        loadFieldValues(fromFields, from, "");
	}
	
	private void loadFieldValues(List<Field> fromFields, Object from, String layer) {
		for (Field field : fromFields) {
			if (Reflection.isStatic(field)) {
				continue;
			} else if (Reflection.isPlain(field.getType())) {
				fieldValues.put(layer + field.getName(), Reflection.getValue(field, from));
			} else {
				Object value = Reflection.getValue(field, from);
				if (value != null) {
					layer += field.getName();
					loadFieldValues(Reflection.getAllFields(field.getType()), value, layer);
				}
			}
		}
	}

	private boolean containsFieldNamed(String name, List<Field> fieldsToSearch) {
		for (Field field : fieldsToSearch) {
			if (field.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}

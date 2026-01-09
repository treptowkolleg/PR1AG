package pr1.training;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AutoToString {

    @Override
    public String toString() {
        Class<?> clazz = this.getClass();
        List<Field> allAnnotatedFields = new ArrayList<>();

        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(ToStringInclude.class)) {
                    field.setAccessible(true);
                    allAnnotatedFields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        Collections.reverse(allAnnotatedFields);
        String parts = allAnnotatedFields.stream().map(field -> {
                    try {
                        ToStringInclude ann = field.getAnnotation(ToStringInclude.class);
                        String prefix = ann.prefix();
                        Object value = field.get(this);

                        return prefix + " " + value;
                    } catch (IllegalAccessException e) {
                        return "<error>";
                    }
                }).collect(Collectors.joining(" "));
        return this.getClass().getSimpleName() + " " + parts;
    }
}

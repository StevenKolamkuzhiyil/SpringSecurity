package com.stevenkolamkuzhiyil.TicTacToe.security.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String fieldName;
    private String targetName;
    private String message;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        fieldName = constraintAnnotation.field();
        targetName = constraintAnnotation.target();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        boolean valid = false;

        List<Field> fields = getAllDeclaredFieldsAndInheritedFields(obj.getClass());
        if (!fields.isEmpty()) {
            valid = fields.stream()
                    .filter(f -> fieldName.equals(f.getName()) || targetName.equals(f.getName()))
                    .map(f -> getValue(obj).apply(f))
                    .distinct().count() <= 1;
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }

    private List<Field> getAllDeclaredFieldsAndInheritedFields(Class<?> objectClass) {
        List<Field> fields = new ArrayList<>(Arrays.asList(objectClass.getDeclaredFields()));
        for (Class<?> c = objectClass.getSuperclass(); c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.stream(c.getDeclaredFields())
                    .filter(f -> Modifier.isProtected(f.getModifiers()) || Modifier.isPublic(f.getModifiers()))
                    .collect(Collectors.toList()));
        }
        return fields;
    }

    private Function<Field, Object> getValue(Object obj) {
        return f -> {
            try {
                f.setAccessible(true);
                return f.get(obj);
            } catch (IllegalAccessException ex) {
                return null;
            }
        };
    }
}

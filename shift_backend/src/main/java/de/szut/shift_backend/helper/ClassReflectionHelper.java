package de.szut.shift_backend.helper;

import org.springframework.util.ReflectionUtils;

import java.lang.module.ResolutionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

public class ClassReflectionHelper {

    public static <T, U> T UpdateFields(T baseObject, Map<String, Object> patchData) {

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(baseObject.getClass(), key);

            if (field == null)
                throw new ResolutionException("Field with name '" + key + "' does not exist");
            if (field.getType() == LocalDate.class) {
                value = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse((CharSequence) value));
            }
            if (field.getType() == LocalDateTime.class) {
                value = LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse((CharSequence) value));
            }

            if (field.getType().isEnum()) {
                Object[] enumVals = field.getType().getEnumConstants();
                try {
                    Method valOfMethod = field.getType().getMethod("valueOf", String.class);
                    Object enumVal = valOfMethod.invoke(null, value);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, baseObject, enumVal);
                    return;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return;
                }
            }

            field.setAccessible(true);
            ReflectionUtils.setField(field, baseObject, field.getType().cast(value)); //TODO: fix here @MessageController->update("status" => "unsend" || "type" => 0 )
        });

        return baseObject;
    }
}

package de.szut.shift_backend.helper;

import org.springframework.util.ReflectionUtils;

import java.lang.module.ResolutionException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ClassReflectionHelper {

    public static <T> T UpdateFields(T baseObject, Map<String,Object> patchData){

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(baseObject.getClass(), key);

            if (field == null)
                throw new ResolutionException("Field with name '" + key + "' does not exist");

            if (field.getType() == LocalDate.class) {
                value = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse((CharSequence) value));
            }
            else if (field.getType() == LocalDateTime.class) {
                value = LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse((CharSequence) value));
            }
            else if (field.getType().isEnum()) {
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
            ReflectionUtils.setField(field, baseObject, field.getType().cast(value));
        });

        return baseObject;
    }

    public static<T,U> T FastParamMap(T objToUpdate, U objToCopyFrom){
        Field[] fieldsToCopy = objToCopyFrom.getClass().getDeclaredFields();

        for(Field f : fieldsToCopy){
            Field field = ReflectionUtils.findField(objToUpdate.getClass(), f.getName());

            if(field == null || Collection.class.isAssignableFrom(field.getType()))
                continue;

            try{
                field.setAccessible(true);
                f.setAccessible(true);
                ReflectionUtils.setField(field, objToUpdate, f.get(objToCopyFrom));
            } catch(Exception e){
                System.out.println(e.getMessage()); //TODO: throw internal server error
            }
        }
        return objToUpdate;
    }
}

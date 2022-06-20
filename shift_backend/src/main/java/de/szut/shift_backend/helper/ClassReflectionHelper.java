package de.szut.shift_backend.helper;

import com.fasterxml.jackson.databind.JsonNode;
import de.szut.shift_backend.exceptionHandling.MappingException;
import org.springframework.util.ReflectionUtils;

import java.lang.module.ResolutionException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ClassReflectionHelper {

    private enum TYPES{
        LOCAL_DATE,
        LOCAL_DATE_TIME,
        LOCAL_TIME,
        LONG
    }

    @SuppressWarnings("rawtypes")
    private static final Map<Class, TYPES> TypeMap = Map.ofEntries(
            Map.entry(LocalDate.class, TYPES.LOCAL_DATE),
            Map.entry(LocalDateTime.class, TYPES.LOCAL_DATE_TIME),
            Map.entry(LocalTime.class, TYPES.LOCAL_TIME),
            Map.entry(Long.class, TYPES.LONG)
    );

    public static <T> T UpdateFields(T baseObject, Map<String,Object> patchData){

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(baseObject.getClass(), key);

            if (field == null)
                throw new ResolutionException("Field with name '" + key + "' does not exist");

            try {
                if (field.getType().isEnum()) {
                    Method valOfMethod = field.getType().getMethod("valueOf", String.class);
                    //noinspection JavaReflectionInvocation
                    Object enumVal = valOfMethod.invoke(null, value);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, baseObject, enumVal);
                    return;
                } else if (TypeMap.containsKey(field.getType())){
                    switch (TypeMap.get(field.getType())){
                        case LOCAL_DATE:
                            value = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse((CharSequence) value));
                            break;
                        case LOCAL_DATE_TIME:
                            value = LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse((CharSequence) value));
                            break;
                        case LOCAL_TIME:
                            value = LocalTime.from(DateTimeFormatter.ISO_LOCAL_TIME.parse((CharSequence) value));
                            break;
                        case LONG:
                            value = ((Integer) value).longValue();
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                throw new MappingException("An error occured in UpdateFields: " + e.getMessage());
            }
/*
                }
                if (field.getType() == LocalDate.class) {
                    value = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse((CharSequence) value));
                }
                else if (field.getType() == LocalDateTime.class) {
                    value = LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse((CharSequence) value));
                }
                else if (field.getType() == LocalTime.class) {
                    value = LocalTime.from(DateTimeFormatter.ISO_LOCAL_TIME.parse((CharSequence) value));
                }
                else if (field.getType().isEnum()) {
                        Method valOfMethod = field.getType().getMethod("valueOf", String.class);
                        Object enumVal = valOfMethod.invoke(null, value);
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, baseObject, enumVal);
                        return;
                }

 */


            field.setAccessible(true);
            ReflectionUtils.setField(field, baseObject, field.getType().cast(value));
        });

        return baseObject;
    }

    public static <T> T UpdateFieldsByObj(T baseObject, T updateObject) {
        Field[] baseFields = baseObject.getClass().getDeclaredFields();

        try{
            for(Field f : baseFields){
                Field updateField = updateObject.getClass().getDeclaredField(f.getName());
                updateField.setAccessible(true);
                f.setAccessible(true);
                ReflectionUtils.setField(f, baseObject, f.get(updateObject));
            }
        } catch (Exception e){
            throw new MappingException("An error occured in UpdateFieldsByObj: " + e.getMessage());
        }


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
               throw new MappingException("An error occured in FastParamMap: " + e.getMessage());
            }
        }
        return objToUpdate;
    }
}

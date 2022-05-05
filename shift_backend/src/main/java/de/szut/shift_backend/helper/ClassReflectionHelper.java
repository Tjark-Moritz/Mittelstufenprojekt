package de.szut.shift_backend.helper;

import org.springframework.util.ReflectionUtils;

import java.lang.module.ResolutionException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public class ClassReflectionHelper {

    public static <T> T UpdateFields(T baseObject, Map<String,Object> patchData){

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(baseObject.getClass(), key);

            if(field == null)
                throw new ResolutionException("Field with name '" + key + "' does not exist");

            field.setAccessible(true);
            ReflectionUtils.setField(field, baseObject, value);
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
                f.setAccessible(true);
                ReflectionUtils.setField(field, objToUpdate, f.get(objToCopyFrom));
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return objToUpdate;
    }
}

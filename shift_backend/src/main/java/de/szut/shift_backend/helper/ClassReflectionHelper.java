package de.szut.shift_backend.helper;

import org.springframework.util.ReflectionUtils;

import java.lang.module.ResolutionException;
import java.lang.reflect.Field;
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
}

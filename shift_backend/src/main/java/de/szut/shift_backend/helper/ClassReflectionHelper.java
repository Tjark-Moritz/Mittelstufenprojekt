package de.szut.shift_backend.helper;

import de.szut.shift_backend.exceptionHandling.DataIncompleteException;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.Map;

public class ClassReflectionHelper {

    public static <T> T UpdateFields(T baseObject, Map<String,Object> patchData){

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(baseObject.getClass(), key);

            if(field == null)
                throw new DataIncompleteException("Field with name '" + key + "' does not exist");

            field.setAccessible(true);
            ReflectionUtils.setField(field, baseObject, value);
        });

        return baseObject;
    }
    /*
    public static <T> T UpdateFieldsWhenNotNull(T baseObject, T updateObject) throws IllegalAccessException, NoSuchFieldException {

        Field[] updateFields = updateObject.getClass().getDeclaredFields();

        for (Field updateField : updateFields){
            updateField.setAccessible(true);
            if (updateField.get(updateObject) != null){
                Field baseFieldToUpdate = baseObject.getClass().getDeclaredField(updateField.getName());
                baseFieldToUpdate.setAccessible(true);
                baseFieldToUpdate.set(baseObject, updateField.get(updateObject));
            }
        }

        return baseObject;
    }

    */
}

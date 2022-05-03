package de.szut.shift_backend.helper;

import org.springframework.util.ReflectionUtils;

import java.lang.module.ResolutionException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

public class ClassReflectionHelper {

    public static <T> T UpdateFields(T baseObject, Map<String,Object> patchData){

        patchData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(baseObject.getClass(), key);

            if(field == null)
                throw new ResolutionException("Field with name '" + key + "' does not exist");
            if(field.getType() == LocalDate.class){
                value = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse((CharSequence) value));
            }


            field.setAccessible(true);
            ReflectionUtils.setField(field, baseObject, field.getType().cast(value)); //TODO: fix here @MessageController->update("status" => "unsend" || "type" => 0 )
        });

        return baseObject;
    }
}

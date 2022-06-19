package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.ShiftType;
import de.szut.shift_backend.repository.ShiftTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShiftTypeService {
    private final ShiftTypeRepository shiftTypeRepository;

    public ShiftTypeService(ShiftTypeRepository shiftTypeRepository) {
        this.shiftTypeRepository = shiftTypeRepository;
    }

    public ShiftType create(ShiftType s){
        return this.shiftTypeRepository.save(s);
    }

    public void delete(Long id) {
        this.shiftTypeRepository.deleteById(id);
    }

    public List<ShiftType> getAllShiftTypes(){
        return this.shiftTypeRepository.findAll();
    }

    public ShiftType getShiftTypeById(Long shiftTypeId){
        Optional<ShiftType> shiftType = shiftTypeRepository.findById(shiftTypeId);

        if (shiftType.isEmpty())
            throw new ResourceNotFoundException("ShiftType with id '" + shiftType + "'could not be found");

        return shiftType.get();
    }

    public ShiftType updateShiftTypeWithObj(Long targetId, ShiftType updateObj){
        ShiftType st = this.getShiftTypeById(targetId);

        return ClassReflectionHelper.UpdateFieldsByObj(st, updateObj);
    }

    public ShiftType updateShiftType(Long shiftTypeId, Map<String, Object> shiftTypeUpdate){
        ShiftType st = this.getShiftTypeById(shiftTypeId);

        ShiftType stUpdated = ClassReflectionHelper.UpdateFields(st, shiftTypeUpdate);

        return this.shiftTypeRepository.save(stUpdated);
    }
}

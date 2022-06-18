package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.ShiftType;
import de.szut.shift_backend.repository.ShiftTypeRepository;
import org.springframework.stereotype.Service;

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

    public ShiftType getShiftTypeById(Long shiftTypeId){
        Optional<ShiftType> shiftType = shiftTypeRepository.findById(shiftTypeId);

        if (shiftType.isEmpty())
            throw new ResourceNotFoundException("ShiftType with id '" + shiftType + "'could not be found");

        return shiftType.get();
    }

}

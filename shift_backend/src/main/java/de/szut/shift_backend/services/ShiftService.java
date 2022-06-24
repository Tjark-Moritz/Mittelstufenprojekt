package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Shift;
import de.szut.shift_backend.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShiftService {
    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public Shift create(Shift shift)
    {
       return shiftRepository.save(shift);
    }

    public Shift getShiftById(Long shiftId){
        Optional<Shift> shift = shiftRepository.findById(shiftId);

        if (shift.isEmpty())
            throw new ResourceNotFoundException("Shift with id '" + shiftId + "'could not be found");

        return shift.get();
    }

    public void swapShifts(Employee requestingEmployee, Shift oldShift, Employee replyingEmployee, Shift newShift) {
        oldShift.getActiveEmployees().remove(requestingEmployee);
        oldShift.getActiveEmployees().add(replyingEmployee);

        newShift.getActiveEmployees().remove(replyingEmployee);
        newShift.getActiveEmployees().add(requestingEmployee);

        this.shiftRepository.save(oldShift);
        this.shiftRepository.save(newShift);
    }
}

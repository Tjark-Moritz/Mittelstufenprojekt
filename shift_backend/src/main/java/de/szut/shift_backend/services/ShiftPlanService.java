package de.szut.shift_backend.services;

import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.ShiftPlan;
import de.szut.shift_backend.repository.ShiftPlanRepository;
import org.springframework.stereotype.Service;

@Service
public class ShiftPlanService {
    private final ShiftPlanRepository shiftPlanRepository;
    private final DepartmentService departmentService;

    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository, DepartmentService departmentService) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.departmentService = departmentService;
    }

    public ShiftPlan create(ShiftPlan newEmployee) {
        return this.shiftPlanRepository.save(newEmployee);
    }

    public ShiftPlan generateShiftPlan(ShiftPlan shiftPlan) {
        return shiftPlan;
    }
}

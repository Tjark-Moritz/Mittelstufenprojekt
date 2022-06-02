package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.ShiftPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftPlanRepository extends JpaRepository<ShiftPlan, Long> {
    List<ShiftPlan> findShiftPlansByDepartment_DepartmentId(Long department_departmentId);
}

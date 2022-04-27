package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftTypeRepository extends JpaRepository<ShiftType, Long> {
}

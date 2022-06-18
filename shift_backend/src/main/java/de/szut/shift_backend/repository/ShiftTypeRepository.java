package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface ShiftTypeRepository extends JpaRepository<ShiftType, Long> {
    Optional<ShiftType> findByShiftStartTimeAndShiftEndTime(LocalTime start, LocalTime end);
}

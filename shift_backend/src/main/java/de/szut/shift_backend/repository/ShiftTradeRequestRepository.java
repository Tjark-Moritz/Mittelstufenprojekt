package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.ShiftTradeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftTradeRequestRepository extends JpaRepository<ShiftTradeRequest, Long> {
    List<ShiftTradeRequest> findAllByReplyingEmployee_Id(Long employeeId);
    List<ShiftTradeRequest> findAllByRequestingEmployee_Id(Long employeeId);
    List<ShiftTradeRequest> findAllByRequestingEmployee_IdAndNewShift_ShiftDate(Long employeeId, LocalDate date);
    List<ShiftTradeRequest> findAllByReplyingEmployee_IdAndOldShift_ShiftDate(Long employeeId, LocalDate date);
}
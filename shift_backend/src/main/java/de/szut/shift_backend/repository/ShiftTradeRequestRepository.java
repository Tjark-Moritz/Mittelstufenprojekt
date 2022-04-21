package de.szut.shift_backend.repository;

import de.szut.shift_backend.model.GetShiftTradeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftTradeRequestRepository extends JpaRepository<GetShiftTradeRequest, Long> {
    List<GetShiftTradeRequest> findAllByReplyingEmployee_Id(Long employeeId);
    List<GetShiftTradeRequest> findAllByRequestingEmployee_Id(Long employeeId);
    List<GetShiftTradeRequest> findAllByRequestingEmployee_IdAndNewShift_ShiftDate(Long employeeId, LocalDate date);
    List<GetShiftTradeRequest> findAllByReplyingEmployee_IdAndOldShift_ShiftDate(Long employeeId, LocalDate date);
}
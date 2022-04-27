package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Shift;
import de.szut.shift_backend.model.ShiftTradeRequest;
import de.szut.shift_backend.repository.ShiftTradeRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShiftTradeRequestService {
    private final ShiftTradeRequestRepository shiftTradeRequestRepository;

    public ShiftTradeRequestService(ShiftTradeRequestRepository shiftTradeRequestRepository) {
        this.shiftTradeRequestRepository = shiftTradeRequestRepository;
    }

    public ShiftTradeRequest create(ShiftTradeRequest request){
        return shiftTradeRequestRepository.save(request);
    }

    public ShiftTradeRequest getShiftTradeRequestByRequestId(Long requestId){
        Optional<ShiftTradeRequest> request = shiftTradeRequestRepository.findById(requestId);

        if (request.isEmpty())
            throw new ResourceNotFoundException("shift trade request with id '" + requestId + "'could not be found");

        return request.get();
    }

    public void updateShiftTradeRequests(ShiftTradeRequest updateRequest, boolean accepted){
        if (!accepted){
            shiftTradeRequestRepository.deleteById(updateRequest.getId());
        } else {
            List<ShiftTradeRequest> conflictingRequesterRequests = shiftTradeRequestRepository.findAllByRequestingEmployee_IdAndNewShift_ShiftDate(updateRequest.getRequestingEmployee().getId(),
                                                                                                                                                   updateRequest.getNewShift().getShiftDate());
            List<ShiftTradeRequest> conflictingRespondentRequests = shiftTradeRequestRepository.findAllByReplyingEmployee_IdAndOldShift_ShiftDate(updateRequest.getReplyingEmployee().getId(),
                                                                                                                                                  updateRequest.getOldShift().getShiftDate());

            for(ShiftTradeRequest request : conflictingRequesterRequests){
                shiftTradeRequestRepository.deleteById(request.getId());
            }

            for(ShiftTradeRequest request : conflictingRespondentRequests){
                shiftTradeRequestRepository.deleteById(request.getId());
            }
        }
    }

    public List<ShiftTradeRequest> getShiftTradeRequestsForRespondingEmployee(Long employeeId){
        return this.shiftTradeRequestRepository.findAllByReplyingEmployee_Id(employeeId);
    }

    public List<ShiftTradeRequest> getShiftTradeRequestsForRequestingEmployee(Long employeeId){
        return this.shiftTradeRequestRepository.findAllByRequestingEmployee_Id(employeeId);
    }
}

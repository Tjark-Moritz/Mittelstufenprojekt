package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Shift;
import de.szut.shift_backend.model.GetShiftTradeRequest;
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

    public GetShiftTradeRequest create(GetShiftTradeRequest request){
        return shiftTradeRequestRepository.save(request);
    }

    public GetShiftTradeRequest getShiftTradeRequestByRequestId(Long requestId){
        Optional<GetShiftTradeRequest> request = shiftTradeRequestRepository.findById(requestId);

        if (request.isEmpty())
            throw new ResourceNotFoundException("shift trade request with id '" + requestId + "'could not be found");

        return request.get();
    }

    public void updateShiftTradeRequests(GetShiftTradeRequest updateRequest, boolean accepted){
        if (!accepted){
            shiftTradeRequestRepository.deleteById(updateRequest.getId());
        } else {
            List<GetShiftTradeRequest> conflictingRequesterRequests = shiftTradeRequestRepository.findAllByRequestingEmployee_IdAndNewShift_ShiftDate(updateRequest.getRequestingEmployee().getId(),
                                                                                                                                                   updateRequest.getNewShift().getShiftDate());
            List<GetShiftTradeRequest> conflictingRespondentRequests = shiftTradeRequestRepository.findAllByReplyingEmployee_IdAndOldShift_ShiftDate(updateRequest.getReplyingEmployee().getId(),
                                                                                                                                                  updateRequest.getOldShift().getShiftDate());

            for(GetShiftTradeRequest request : conflictingRequesterRequests){
                shiftTradeRequestRepository.deleteById(request.getId());
            }

            for(GetShiftTradeRequest request : conflictingRespondentRequests){
                shiftTradeRequestRepository.deleteById(request.getId());
            }
        }
    }

    public List<GetShiftTradeRequest> getShiftTradeRequestsForRespondingEmployee(Long employeeId){
        return this.shiftTradeRequestRepository.findAllByReplyingEmployee_Id(employeeId);
    }

    public List<GetShiftTradeRequest> getShiftTradeRequestsForRequestingEmployee(Long employeeId){
        return this.shiftTradeRequestRepository.findAllByRequestingEmployee_Id(employeeId);
    }
}

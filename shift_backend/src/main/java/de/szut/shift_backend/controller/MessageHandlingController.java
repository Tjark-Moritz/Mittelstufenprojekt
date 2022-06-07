package de.szut.shift_backend.controller;

import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Message;
import de.szut.shift_backend.model.dto.AddMessageDto;
import de.szut.shift_backend.model.dto.GetMessageDto;
import de.szut.shift_backend.services.MappingService;
import de.szut.shift_backend.services.MessageService;
import de.szut.shift_backend.socket.WebsocketEventEmitterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@MessageMapping("/message")
public class MessageHandlingController {

    private final MessageController messageController;
    private final WebsocketEventEmitterService websocketEventEmitterService;
    private final MappingService mappingService;
    private final MessageService messageService;

    public MessageHandlingController(MessageController messageController, WebsocketEventEmitterService websocketEventEmitterService, MappingService mappingService, MessageService messageService) {
        this.messageController = messageController;
        this.websocketEventEmitterService = websocketEventEmitterService;
        this.mappingService = mappingService;
        this.messageService = messageService;
    }

    @MessageMapping("/create")
    public void createMessage(AddMessageDto addMessageDto, Principal principal) {
        System.out.println("create");

        Message message = this.mappingService.mapAddMessageDtoToMessage(addMessageDto, principal.getName());
        this.messageService.create(message);

        List<Employee> employeeList = message.getMessageChannel().getEmployees();
        List<Long> requestedEmployeeIdList = new ArrayList<>();

        for (Employee employee: employeeList) {
            requestedEmployeeIdList.add(employee.getId());
        }

        websocketEventEmitterService.sendTo(
                requestedEmployeeIdList,
                "/message",
                addMessageDto
                );
    }
}

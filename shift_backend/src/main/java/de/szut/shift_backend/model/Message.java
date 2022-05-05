package de.szut.shift_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
public class Message {

    public enum MessageStatus {
        send,
        unsend;

        public static Message.MessageStatus of(String status) {
            Message.MessageStatus[] values = Message.MessageStatus.values();

            for (Message.MessageStatus messageStatus : values) {
                if (messageStatus.name().equals(status)) {
                    return send;
                }
            }
            return unsend;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Channel can not be null!")
    private Long channelId;

    @NotNull(message = "Requested-EmployeeId can not be null!")
    private Long requestedEmployeeId;

    @NotNull(message = "Sending employeeId can not be null!")
    private Long sendingEmployeeId;

    private LocalDateTime dateTime;

    private Message.MessageStatus status;
}

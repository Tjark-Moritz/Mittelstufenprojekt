package de.szut.shift_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    private MessageChannel messageChannel;

    @NotNull(message = "Content can not be null!")
    private String content;

    @NotNull(message = "Sending employeeId can not be null!")
    private Long sendingEmployeeId;

    @NotNull(message = "Message-Type can not be null!")
    private Long type;

    private LocalDateTime dateTime;

    private Message.MessageStatus status;
}

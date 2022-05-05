package de.szut.shift_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Data
@Entity
public class MessageChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Channel name can not be null!")
    private String name;

    @NotNull(message = "Channel description can not be null!")
    private String description;

    @OneToMany
    private List<Message> messages;
}

package de.szut.shift_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @OrderBy("dateTime DESC")
    @OneToMany(mappedBy = "messageChannel")
    private List<Message> messages = new ArrayList<>();

    @OneToMany
    private List<Employee> employees;
}

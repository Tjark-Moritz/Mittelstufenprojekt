package de.szut.shift_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@Entity
public class HolidayType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "HolidayTypeName can not be null!")
    private String name;

    @NotNull(message = "HolidayTypeDescription can not be null!")
    private String description;
}

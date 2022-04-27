package de.szut.shift_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ShiftTradeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Employee requestingEmployee;

    @OneToOne
    @NotNull
    private Employee replyingEmployee;

    @OneToOne
    @NotNull
    private Shift oldShift;

    @OneToOne
    @NotNull
    private Shift newShift;
}

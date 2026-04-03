package com.library.reservation.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO {
    private int reservationId;
    private int bookId;
    private int userId;
    private LocalDate reservationDate;
}

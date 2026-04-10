package com.library.reservation.services;

import com.library.reservation.dtos.BookEventDTO;

public interface ReservationService {
    void saveReservation(BookEventDTO bookEventDTO);
    void checkBookInReservations(BookEventDTO bookEventDTO);
}

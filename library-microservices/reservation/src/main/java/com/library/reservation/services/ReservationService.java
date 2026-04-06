package com.library.reservation.services;

import com.library.reservation.dtos.BookOutOfStockEventDTO;

public interface ReservationService {
    void saveReservation(BookOutOfStockEventDTO bookOutOfStockEventDTO);
}

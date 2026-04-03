package com.library.reservation.services.impl;

import com.library.reservation.repositories.ReservationRepository;
import com.library.reservation.services.ReservationService;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

}

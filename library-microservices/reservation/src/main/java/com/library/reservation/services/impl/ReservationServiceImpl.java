package com.library.reservation.services.impl;

import com.library.reservation.dtos.BookOutOfStockEventDTO;
import com.library.reservation.dtos.ReservationDTO;
import com.library.reservation.mappers.ReservationMapper;
import com.library.reservation.repositories.ReservationRepository;
import com.library.reservation.services.ReservationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
    @KafkaListener(topics = "book_out_of_stock", groupId = "library")
    @Override
    public void saveReservation(BookOutOfStockEventDTO bookOutOfStockEventDTO) {
        reservationRepository.save(ReservationMapper.changeToEntity(new ReservationDTO(0, bookOutOfStockEventDTO.getBookId(), bookOutOfStockEventDTO.getUserId(), LocalDate.now())));
    }
}

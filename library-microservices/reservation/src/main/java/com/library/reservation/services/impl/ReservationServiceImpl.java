package com.library.reservation.services.impl;

import com.library.reservation.dtos.BookOutOfStockEventDTO;
import com.library.reservation.dtos.ReservationDTO;
import com.library.reservation.mappers.ReservationMapper;
import com.library.reservation.models.Reservation;
import com.library.reservation.repositories.ReservationRepository;
import com.library.reservation.services.ReservationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final KafkaTemplate<String, BookOutOfStockEventDTO> kafkaTemplate;
    public ReservationServiceImpl(ReservationRepository reservationRepository,KafkaTemplate<String, BookOutOfStockEventDTO> kafkaTemplate){
        this.reservationRepository = reservationRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    @KafkaListener(topics = "book_out_of_stock", groupId = "library")
    @Override
    public void saveReservation(BookOutOfStockEventDTO bookOutOfStockEventDTO) {
        reservationRepository.save(ReservationMapper.changeToEntity(new ReservationDTO(0, bookOutOfStockEventDTO.getBookId(), bookOutOfStockEventDTO.getUserId(), LocalDate.now())));
    }
    @KafkaListener(topics = "book_returned", groupId = "library")
    @Override
    public void checkBookInReservations(BookOutOfStockEventDTO bookOutOfStockEventDTO) {
        Reservation reservation;
        Optional<Reservation> reservationOptional = reservationRepository.findReservationByBookId(bookOutOfStockEventDTO.getBookId());
        if(reservationOptional.isPresent()){
            reservation = reservationOptional.get();
            kafkaTemplate.send("reservation_ready",new BookOutOfStockEventDTO(reservation.getUserId(), reservation.getBookId()));
        }

    }
}

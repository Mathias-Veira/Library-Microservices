package com.library.reservation.services.impl;

import com.library.reservation.dtos.BookEventDTO;
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
    private final KafkaTemplate<String, BookEventDTO> kafkaTemplate;
    public ReservationServiceImpl(ReservationRepository reservationRepository,KafkaTemplate<String, BookEventDTO> kafkaTemplate){
        this.reservationRepository = reservationRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    @KafkaListener(topics = "book_out_of_stock", groupId = "reservation")
    @Override
    public void saveReservation(BookEventDTO bookEventDTO) {
        reservationRepository.save(ReservationMapper.changeToEntity(new ReservationDTO(0, bookEventDTO.getBookId(), bookEventDTO.getUserId(), LocalDate.now())));
    }
    @KafkaListener(topics = "book_returned", groupId = "reservation")
    @Override
    public void checkBookInReservations(BookEventDTO bookEventDTO) {
        Optional<Reservation> reservationOptional = reservationRepository.findReservationByBookId(bookEventDTO.getBookId());
        if(reservationOptional.isPresent()){
            kafkaTemplate.send("reservation_ready",new BookEventDTO(reservationOptional.get().getUserId(), bookEventDTO.getBookId()));
        }
    }
}

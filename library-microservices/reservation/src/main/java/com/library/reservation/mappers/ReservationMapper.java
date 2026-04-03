package com.library.reservation.mappers;

import com.library.reservation.dtos.ReservationDTO;
import com.library.reservation.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationMapper {
    public static ReservationDTO changeToDTO(Reservation reservation){
        return new ReservationDTO(reservation.getReservationId(), reservation.getBookId(), reservation.getUserId(),reservation.getReservationDate());
    }

    public static Reservation changeToEntity(ReservationDTO reservation){
        return new Reservation(reservation.getReservationId(), reservation.getBookId(), reservation.getUserId(),reservation.getReservationDate());
    }

    public static List<ReservationDTO> changeToListDTO(List<Reservation> reservations){
        List<ReservationDTO> reservationDTOList = new ArrayList<>();
        for (Reservation loan: reservations) {
            reservationDTOList.add(changeToDTO(loan));
        }
        return  reservationDTOList;
    }
}

package com.library.reservation.repositories;

import com.library.reservation.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    @Query("SELECT r FROM Reservation r where userId = :userId")
    List<Reservation> findReservationsByUserId(@Param("userId") int userId);
}

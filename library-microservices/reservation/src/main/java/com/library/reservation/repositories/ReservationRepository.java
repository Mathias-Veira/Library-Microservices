package com.library.reservation.repositories;

import com.library.reservation.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    @Query("SELECT r FROM Reservation r where userId = :userId")
    List<Reservation> findReservationsByUserId(@Param("userId") int userId);
    @Query("SELECT r FROM Reservation r where bookId = :bookId")
    Optional<Reservation> findReservationByBookId(@Param("bookId") int bookId);
}

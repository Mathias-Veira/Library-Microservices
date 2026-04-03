package com.library.reservation.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Reservation")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationId;
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "reservation_date")
    private LocalDate reservationDate;
}

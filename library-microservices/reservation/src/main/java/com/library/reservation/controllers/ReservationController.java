package com.library.reservation.controllers;

import com.library.reservation.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }


    @PostMapping("/save_reservation")
    public ResponseEntity<?> saveLoan(@RequestParam int userId, int bookId){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/return_book")
    public ResponseEntity<?> saveLoan(@RequestParam int loanId){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

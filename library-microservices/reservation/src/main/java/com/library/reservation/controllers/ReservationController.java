package com.library.reservation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ReservationController {


    @PostMapping("/save_reservation")
    public ResponseEntity<HttpStatus> saveLoan(@RequestParam int userId, int bookId){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/return_book")
    public ResponseEntity<HttpStatus> saveLoan(@RequestParam int loanId){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

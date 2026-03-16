package com.library.loan.services;

import com.library.loan.dtos.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book")
public interface BookClient {
    @GetMapping("/book_by_id")
    List<BookDTO> getBooksById(@RequestParam List<Integer> id);
}

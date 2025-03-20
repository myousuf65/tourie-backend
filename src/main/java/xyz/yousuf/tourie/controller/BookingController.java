package xyz.yousuf.tourie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.yousuf.tourie.dto.BookTourDto;
import xyz.yousuf.tourie.dto.CancelBookingDto;
import xyz.yousuf.tourie.entity.Booking;
import xyz.yousuf.tourie.service.BookingService;
import xyz.yousuf.tourie.service.TourService;

import java.util.List;


@RestController
public class BookingController {


    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    Booking bookTour(@RequestBody BookTourDto bookTourDto){
        Booking response = bookingService.bookTour(bookTourDto);
        return response;
    }

    @GetMapping("/getbookings")
    ResponseEntity<?> GetAllBookings(){
        List<Booking> allBookings =  bookingService.getAllBookings();
        return ResponseEntity.ok(allBookings);
    }

    @PostMapping("/booking/cancel")
    ResponseEntity<?> CancelBooking(@RequestBody CancelBookingDto cancelBookingDto){
        List<Booking> allBookings =  bookingService.cancelBooking(cancelBookingDto);
        return ResponseEntity.ok(allBookings);
    }

    @GetMapping("/getbookings/{username}")
    ResponseEntity<?> GetAllBookings(@PathVariable String username){
        List<Booking> allBookings =  bookingService.getBookingsByUsername(username);
        return ResponseEntity.ok(allBookings);
    }




}

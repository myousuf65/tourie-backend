package xyz.yousuf.tourie.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.yousuf.tourie.dto.*;
import xyz.yousuf.tourie.entity.Booking;
import xyz.yousuf.tourie.entity.Tour;
import xyz.yousuf.tourie.repository.BookingRespository;
import xyz.yousuf.tourie.repository.UserRepository;
import xyz.yousuf.tourie.service.BookingService;
import xyz.yousuf.tourie.service.TourService;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RequestMapping("/tour")
@RestController
public class TourController {

    @Autowired
    TourService tourService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRespository bookingRespository;
    @Autowired
    private BookingService bookingService;

    @PostMapping("/upload")
    ResponseEntity<?> uploadTour(@RequestBody TourDto tourInfo){
        System.out.println("[[Upload Request]]: " + tourInfo);
        Tour tour = tourService.uploadTour(tourInfo);
        return ResponseEntity.ok(tour);
    }

    @GetMapping("/all")
    List<Tour> getAllTours(){
        return tourService.getAllTours();
    }

    @GetMapping("/getbyusername/{username}")
    List<Tour> getToursByUsername(@PathVariable String username){
        return tourService.getToursByUsername(username);
    }

    @PostMapping("/delete")
    ResponseEntity<?> deleteTour(@RequestBody DeleteTourDto tourInfo){
        System.out.println("[[Delete Request for ]]: " + tourInfo);
        String returnMessage = tourService.deleteTour(tourInfo);
        return ResponseEntity.ok(returnMessage);
    }

    @GetMapping("/get/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Tour> tour = tourService.getById(id);
        return ResponseEntity.ok(tour);
    }

    @PostMapping("/update")
    ResponseEntity<?> updateTour(@RequestBody Tour tourInfo){
        Tour tour = tourService.updateTour(tourInfo);
        return ResponseEntity.ok(tour);
    }

    @PostMapping("/rate")
    List<Booking> rateTour(@RequestBody RateTourDto rateTourDto){
        List<Booking> allBookings = bookingService.rateBooking(rateTourDto);
        return allBookings;
    }


    @GetMapping("/getratings/{tourId}")
    List<Booking> getRatings(@PathVariable Long tourId){
        List<Booking> allBooking = bookingService.getRatings(tourId);
        return allBooking;
    }








}

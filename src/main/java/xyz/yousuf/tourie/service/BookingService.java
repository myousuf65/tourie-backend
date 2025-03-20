package xyz.yousuf.tourie.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yousuf.tourie.dto.BookTourDto;
import xyz.yousuf.tourie.dto.CancelBookingDto;
import xyz.yousuf.tourie.dto.RateTourDto;
import xyz.yousuf.tourie.entity.Booking;
import xyz.yousuf.tourie.entity.Tour;
import xyz.yousuf.tourie.entity.UserModel;
import xyz.yousuf.tourie.repository.BookingRespository;
import xyz.yousuf.tourie.repository.TourRepository;
import xyz.yousuf.tourie.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRespository bookingRespository;



    public Booking bookTour(BookTourDto bookTourDto) {
        Tour tour = tourRepository.findById(Long.valueOf(bookTourDto.getTourId())).orElseThrow();
        UserModel user = userRepository.findByName(bookTourDto.getUsername());


       Optional<Booking> existingBooking = bookingRespository.findByTourAndUser(tour, user);

       if(existingBooking.isPresent()){
           throw new RuntimeException("User has already booked this Tour");
       }

        Booking booking = new Booking();
        booking.setTour(tour);
        booking.setPayment(true);
        booking.setUser(user);

        Booking savedBooking = bookingRespository.save(booking);
        bookingRespository.flush();

        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRespository.findAll();
    }

    public List<Booking> cancelBooking(CancelBookingDto cancelBookingDto) {
        Booking booking = bookingRespository
                .findById(Long.valueOf(cancelBookingDto.getBookingId()))
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        booking.setDeleted(true);
        bookingRespository.save(booking);
        bookingRespository.flush();

        UserModel user = userRepository.findByName(cancelBookingDto.getUsername());
        return bookingRespository.findByUser(user);
    }

    public List<Booking> getBookingsByUsername(String username) {
        UserModel user = userRepository.findByName(username);
        return bookingRespository.findByUser(user);
    }

    public List<Booking> rateBooking(RateTourDto rateTourDto) {
        UserModel user = userRepository.findByName(rateTourDto.getUsername());
        Booking booking = bookingRespository.findById(Long.valueOf(rateTourDto.getBookingId()))
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        booking.setRating(rateTourDto.getRating());
        bookingRespository.save(booking);
        bookingRespository.flush();

        return bookingRespository.findByUser(user);

    }

    public List<Booking> getRatings(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(()-> new RuntimeException("Tour Not Found"));
        return bookingRespository.findByTour(tour);
    }
}

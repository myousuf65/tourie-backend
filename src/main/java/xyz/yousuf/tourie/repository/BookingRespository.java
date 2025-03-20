package xyz.yousuf.tourie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.yousuf.tourie.entity.Booking;
import xyz.yousuf.tourie.entity.Tour;
import xyz.yousuf.tourie.entity.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRespository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByTourAndUser(Tour tour, UserModel user);
    List<Booking> findByUser(UserModel user);

    List<Booking> findByTour(Tour tour);
}

package xyz.yousuf.tourie.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yousuf.tourie.dto.DeleteTourDto;
import xyz.yousuf.tourie.dto.TourDto;
import xyz.yousuf.tourie.entity.Tour;
import xyz.yousuf.tourie.entity.UserModel;
import xyz.yousuf.tourie.repository.TourRepository;
import xyz.yousuf.tourie.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    TourRepository tourRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Tour uploadTour(TourDto tourInfo){
        UserModel user = userRepository.findByName(tourInfo.getUsername());

        Tour tour = new Tour();
        tour.setName(tourInfo.getName());
        tour.setDescription(tourInfo.getDescription());
        tour.setPhotoUrl(tourInfo.getPhotoUrl());
        tour.setPrice(tourInfo.getPrice());
        tour.setUser(user);


        Tour savedTour =  tourRepository.save(tour);
        tourRepository.flush();
        System.out.println("tour saved");

        return savedTour;
    }

    public List<Tour> getAllTours(){
        return tourRepository.findAll();
    }

    public List<Tour> getToursByUsername(String username) {

        UserModel user = userRepository.findByName(username);
        return tourRepository.findByUser(user);
    }

    @Transactional
    public String deleteTour(DeleteTourDto tourInfo) {
        UserModel user = userRepository.findByName(tourInfo.getUsername());

        try{
            tourRepository.deleteById(tourInfo.getTour());
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Error" + e.toString();
        }
        return "delete success";
    }


    public Optional<Tour> getById(Long id) {
        return tourRepository.findById(id);
    }

    public Tour updateTour(Tour tourInfo) {
        Optional<Tour> optionalTour = tourRepository.findById(tourInfo.getTourId());

        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();
            tour.setName(tourInfo.getName());
            tour.setDescription(tourInfo.getDescription());
            tour.setPhotoUrl(tourInfo.getPhotoUrl());
            tour.setPrice(tourInfo.getPrice());

            Tour savedTour = tourRepository.save(tour);
            tourRepository.flush();
            return savedTour;
        } else {
            throw new NoSuchElementException("Tour with ID " + tourInfo.getTourId() + " not found.");
        }
    }
}

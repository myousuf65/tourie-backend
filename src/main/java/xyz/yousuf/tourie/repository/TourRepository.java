package xyz.yousuf.tourie.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.yousuf.tourie.entity.Tour;
import xyz.yousuf.tourie.entity.UserModel;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByUserAndIsDeletedFalse(UserModel user);
    List<Tour> findByIsDeletedFalse();
    List<Tour> findByUser(UserModel user);
    void deleteByNameAndUser(String name, UserModel user);
}

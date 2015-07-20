package ua.kiev.dk.services;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.dk.entities.Advertisement;

import java.util.List;

public interface AdvRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByShortDescLike(String pattern);
}
package ua.kiev.dk.services;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.dk.entities.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
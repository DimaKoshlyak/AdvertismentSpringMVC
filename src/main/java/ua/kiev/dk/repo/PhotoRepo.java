package ua.kiev.dk.repo;

/**
 * Created by d.koshlyak on 23.07.2015.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kiev.dk.entities.Photo;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Long> {
}

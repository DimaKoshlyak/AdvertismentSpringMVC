package ua.kiev.dk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.dk.entities.Advertisement;

import java.util.List;

/**
 * Created by d.koshlyak on 23.07.2015.
 */
@Repository
@Transactional
public interface AdvRepo extends JpaRepository<Advertisement, Long> {
     List<Advertisement> findByShortDescLike(String pattern);
     List<Advertisement> findByToDel(boolean isToDel);
    }


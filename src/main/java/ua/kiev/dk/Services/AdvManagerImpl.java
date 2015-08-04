package ua.kiev.dk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.kiev.dk.entities.Advertisement;
import ua.kiev.dk.entities.Photo;
import ua.kiev.dk.repo.AdvRepo;
import ua.kiev.dk.repo.PhotoRepo;

import java.util.List;

/**
 * Created by d.koshlyak on 04.08.2015.
 */
@Service("advManager")
public class AdvManagerImpl implements AdvManager {

    @Qualifier("advRepo")
    @Autowired
    private AdvRepo advRepo;

    @Qualifier("photoRepo")
    @Autowired
    private PhotoRepo photoRepo;

    public List<Advertisement> list() {
        return advRepo.findByToDel(false);
    }

    public List<Advertisement> list(String pattern) {
        return advRepo.findByShortDescLike(pattern);
    }

    public List<Advertisement> listBin() {
    return advRepo.findByToDel(true);
    }


    public void add(Advertisement adv) {
        advRepo.save(adv);
    }

    public void moveToTrash(long id) {
        Advertisement adv = advRepo.findOne(id);
        adv.setTo_del(true);
        advRepo.save(adv);
    }

    public void restoreFromTrash(long id){
        Advertisement adv = advRepo.findOne(id);
        adv.setTo_del(false);
        advRepo.save(adv);
    }

    public void delete(long id) {
        advRepo.delete(id);
    }


    public byte[] getPhoto(long id) {
        Photo photo = photoRepo.findOne(id);
        return photo.getBody();
    }
}

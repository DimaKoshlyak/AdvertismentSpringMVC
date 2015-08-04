package ua.kiev.dk.Services;

import ua.kiev.dk.entities.Advertisement;

import java.util.List;

/**
 * Created by d.koshlyak on 04.08.2015.
 */
public interface AdvManager {
    List<Advertisement> list();

    List<Advertisement> list(String pattern);

    List<Advertisement> listBin ();

    void add(Advertisement adv);

    void moveToTrash(long id);

    void restoreFromTrash(long id);

    void delete(long id);

    byte[] getPhoto(long id);
}

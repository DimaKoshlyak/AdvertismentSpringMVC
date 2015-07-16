package ua.kiev.dk.dao;

import ua.kiev.dk.entities.Advertisement;

import java.util.List;

public interface AdvDAO {
	List<Advertisement> list();
    List<Advertisement> listInTray();
    List<Advertisement> list(String pattern);
	void add(Advertisement adv);
    Advertisement getAdv(long id);
    void delete(long id);
    byte[] getPhoto(long id);
    void moveToTrash(long id);
    void restoreFromTrash(long id);
}

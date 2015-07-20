package ua.kiev.dk.services;

import ua.kiev.dk.entities.Advertisement;

import java.util.List;

public interface AdvManager {
	List<Advertisement> list();
    List<Advertisement> list(String pattern);
	void add(Advertisement adv);
    void delete(long id);
    byte[] getPhoto(long id);
}

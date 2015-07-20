package ua.kiev.dk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.dk.entities.Advertisement;
import ua.kiev.dk.entities.Photo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Transactional
public class AdvDAOImpl implements AdvDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Advertisement> list() {
        Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.toDel <> 1", Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public List<Advertisement> listInTray() {
        Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.toDel = 1", Advertisement.class);
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public List<Advertisement> list(String pattern) {
        Query query = entityManager.createQuery("SELECT a FROM Advertisement a WHERE a.shortDesc LIKE :pattern", Advertisement.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Advertisement>) query.getResultList();
    }

    @Override
    public void add(Advertisement adv) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public Advertisement getAdv(long id){
        try{
            Advertisement adv = entityManager.find(Advertisement.class,id);
            return adv;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public void delete(long id) {
        try {
            entityManager.getTransaction().begin();
            Advertisement adv = entityManager.find(Advertisement.class, id);
            entityManager.remove(adv);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public byte[] getPhoto(long id) {
        try {
            Photo photo = entityManager.find(Photo.class, id);
            return photo.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void restoreFromTrash(long id){
        try {
            entityManager.getTransaction().begin();
            Advertisement adv = entityManager.find(Advertisement.class, id);
            adv.setTo_del(false);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void moveToTrash(long id) {
        try {
            entityManager.getTransaction().begin();
            Advertisement adv = entityManager.find(Advertisement.class, id);
            adv.setTo_del(true);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
        public void merge(Advertisement adv) {
        entityManager.merge(adv);
    }
}

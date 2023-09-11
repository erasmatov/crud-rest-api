package net.erasmatov.crudapi.repository.hibernate;

import net.erasmatov.crudapi.model.File;
import net.erasmatov.crudapi.repository.FileRepository;
import net.erasmatov.crudapi.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {
    Transaction transaction = null;

    @Override
    public File save(File file) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(file);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public List<File> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM File", File.class).list();
        }
    }

    @Override
    public File getById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            return session.get(File.class, id);
        }
    }

    @Override
    public File update(File file) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(file);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(File.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}

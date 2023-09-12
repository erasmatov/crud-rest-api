package net.erasmatov.crudapi.repository.hibernate;

import net.erasmatov.crudapi.model.Status;
import net.erasmatov.crudapi.model.User;
import net.erasmatov.crudapi.repository.UserRepository;
import net.erasmatov.crudapi.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    Transaction transaction = null;

    @Override
    public User save(User user) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public User getById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            Query<User> query = session.createQuery(
                    "FROM User u LEFT JOIN FETCH u.events WHERE u.id = :id", User.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            user.setStatus(Status.DELETED);
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}

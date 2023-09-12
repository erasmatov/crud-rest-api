package net.erasmatov.crudapi.repository.hibernate;

import net.erasmatov.crudapi.model.Event;
import net.erasmatov.crudapi.repository.EventRepository;
import net.erasmatov.crudapi.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {
    Transaction transaction = null;

    @Override
    public Event save(Event event) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public List<Event> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("FROM Event e LEFT JOIN FETCH e.user", Event.class).list();
        }
    }

    @Override
    public Event getById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            Query<Event> query = session.createQuery(
                    "FROM Event e LEFT JOIN FETCH e.user WHERE e.id = :id", Event.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    @Override
    public Event update(Event event) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(Event.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}

package net.erasmatov.crudapi.service;

import net.erasmatov.crudapi.model.Event;
import net.erasmatov.crudapi.repository.EventRepository;
import net.erasmatov.crudapi.repository.hibernate.HibernateEventRepositoryImpl;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
    }

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event getEventById(Integer id) {
        return eventRepository.getById(id);
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public void deleteEventById(Integer id) {
        eventRepository.deleteById(id);
    }

}

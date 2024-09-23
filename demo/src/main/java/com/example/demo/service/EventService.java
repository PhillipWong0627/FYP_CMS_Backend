package com.example.demo.service;

import com.example.demo.Bean.Event;
import com.example.demo.Bean.Member;
import com.example.demo.repo.EventRepository;
import com.example.demo.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public EventService(EventRepository eventRepository, MemberRepository memberRepository) {
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
    }

    // Create an Event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Get all Events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get an Event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Update an Event
    public Event updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id).map(event -> {
            event.setEventTitle(updatedEvent.getEventTitle());
            event.setEventDescription(updatedEvent.getEventDescription());
            event.setEventDateTime(updatedEvent.getEventDateTime());
            event.setEventLocation(updatedEvent.getEventLocation());
            event.setEventFee(updatedEvent.getEventFee());
            event.setEventImage(updatedEvent.getEventImage());
            event.setIs_active(updatedEvent.getIs_active());
            return eventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // Delete an Event
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // Join an Event
    public Event joinEvent(Long eventId, Long memberId) {
        // Find the event and the member by their IDs
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        Optional<Member> memberOpt = memberRepository.findById(memberId);

        System.out.println("DEBUG");
        System.out.println(eventOpt);
        System.out.println(memberOpt);


        if (eventOpt.isPresent() && memberOpt.isPresent()) {
            System.out.println("GOTCHA");
            Event event = eventOpt.get();
            Member member = memberOpt.get();
            member.setPoints((int) (member.getPoints() + event.getEventFee()));
            System.out.println(event);
            // Add the member to the event's member list
            event.getMembers().add(member);

            // Save the event with the updated member list
            return eventRepository.save(event);
        } else {
            System.out.println("DLLM");
            throw new RuntimeException("Event or Member not found");
        }
    }


}

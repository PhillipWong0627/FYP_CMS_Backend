package com.example.demo.Controller;

import com.example.demo.Bean.Event;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/admin/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    // Create Event
    @PostMapping("/create")
    public Result<Event> createEvent(@RequestBody Event event) {
        Event savedEvent = eventService.createEvent(event);
        return Result.success(savedEvent);
    }

    // Get All Events
    @GetMapping("/all")
    public Result<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return Result.success(events);
    }

    // Get Event by ID
    @GetMapping("/{id}")
    public Result<Optional<Event>> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);

        if (event.isPresent()) {
            return Result.success(event);
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    // Update Event
    @PutMapping("/update/{id}")
    public Result<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        try {
            Event updatedEvent = eventService.updateEvent(id, event);
            return Result.success(updatedEvent);
        } catch (RuntimeException e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    // Delete Event
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result<Boolean>> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(Result.success(true));
    }

    // Member joins an event
    @PostMapping("/{eventId}/join/{memberId}")
    public Result<Event> joinEvent(@PathVariable Long eventId, @PathVariable Long memberId) {
        try {
            Event event = eventService.joinEvent(eventId, memberId);
            System.out.println("SUCCESS");
            return Result.success(event);
        } catch (RuntimeException e) {
            System.out.println("FAIL");
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }



}

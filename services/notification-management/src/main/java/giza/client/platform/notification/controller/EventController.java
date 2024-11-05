package giza.client.platform.notification.controller;


import giza.client.platform.notification.core.repository.EventRepository;
import giza.client.platform.notification.model.dto.EventDTO;
import giza.client.platform.notification.model.entity.Event;
import giza.client.platform.notification.model.vto.EventVTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepo;
    private final ModelMapper mapper;

    public EventController(EventRepository eventRepo, ModelMapper mapper) {
        this.eventRepo = eventRepo;
        this.mapper = mapper;
    }


    @Secured({"admin"})
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody EventDTO eventDTO) {
        Event event = mapper.map(eventDTO,Event.class);
        Event _event = eventRepo.save(event);
        return ResponseEntity.ok("Event Created with Id: "+_event.getId());

    }


    @Secured({"admin"})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEvent(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody EventDTO eventDTO, @PathVariable Integer id) {
        Event event = mapper.map(eventDTO,Event.class);
        event.setId(id);
        eventRepo.save(event);
        return ResponseEntity.ok("Event Updated");

    }

    @Secured({"admin"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvent(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Integer id) {
        eventRepo.deleteById(id);
        return ResponseEntity.ok("Event Deleted");

    }


    @Secured({"admin"})
    @GetMapping("/get/details/{id}")
    public ResponseEntity<?> getEventById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Integer id) {
        Optional<Event> event = eventRepo.findById(id);
        if(event!=null){
            EventVTO eventVTO = mapper.map(event.get(),EventVTO.class);
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.ok("Event Does Not Exist");


    }
}

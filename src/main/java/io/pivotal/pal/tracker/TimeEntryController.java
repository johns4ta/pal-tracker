package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    public TimeEntryRepository timeEntryRepository;

    public TimeEntryController(@Autowired TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository=timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);

        if (timeEntry!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") Long id) {

        System.out.println("Invoked read method in controller...:"+id);
        TimeEntry timeEntry = timeEntryRepository.find(id.longValue());
        System.out.println("Returned timeEntry :"+timeEntry);
        if (timeEntry!=null) {
            return ResponseEntity.ok().body(timeEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        List<TimeEntry> timeEntries = timeEntryRepository.list();
        return ResponseEntity.ok().body(timeEntries);
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(id.longValue(), expected);
        if (timeEntry!=null) {
            return ResponseEntity.ok().body(timeEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") Long timeEntryId) {
        TimeEntry timeEntryFound = timeEntryRepository.find(timeEntryId);
        timeEntryRepository.delete(timeEntryId);
        return ResponseEntity.noContent().build();

    }
}

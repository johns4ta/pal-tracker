package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    public TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(@Autowired TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gauge) {
        this.timeEntryRepository=timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);

        if (timeEntry!=null) {
            counter.increment("TimeEntry.created");
            gauge.submit("timeEntries.count", timeEntryRepository.list().size());
            return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") Long id) {

        System.out.println("Invoked read method in controller...:"+id);
        TimeEntry timeEntry = timeEntryRepository.find(id.longValue());
        System.out.println("Returned timeEntry :"+timeEntry);
        if (timeEntry!=null) {
            counter.increment("TimeEntry.read");
            return ResponseEntity.ok().body(timeEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.created");
        List<TimeEntry> timeEntries = timeEntryRepository.list();
        return ResponseEntity.ok().body(timeEntries);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(id.longValue(), expected);
        if (timeEntry!=null) {
            counter.increment("TimeEntry.created");
            return ResponseEntity.ok().body(timeEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") Long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);

        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return ResponseEntity.noContent().build();

    }
}

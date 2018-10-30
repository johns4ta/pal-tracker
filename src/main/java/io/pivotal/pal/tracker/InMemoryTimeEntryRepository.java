package io.pivotal.pal.tracker;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
    int counter = 0;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(++counter);
        timeEntries.add(timeEntry);
       return timeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        TimeEntry timeEntry = timeEntries.stream().filter(entry -> entry.getId().equals(timeEntryId)).findAny().orElse(null);
        return timeEntry;
    }

    @Override
    public List list() {
        return timeEntries;
    }


    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry timeEntryFound = find(id);
        if (timeEntryFound!=null){
            timeEntryFound.setProjectId(timeEntry.getProjectId());
            timeEntryFound.setDate(timeEntry.getDate());
            timeEntryFound.setHours(timeEntry.getHours());
            timeEntryFound.setUserId(timeEntry.getUserId());
        }
        return timeEntryFound;
    }

    @Override
    public void delete(long id) {
        timeEntries.stream().filter(entry -> entry.getId().equals(id)).findFirst().map(entryToBeRemoved -> timeEntries.remove(entryToBeRemoved)).orElse(null);
    }

}

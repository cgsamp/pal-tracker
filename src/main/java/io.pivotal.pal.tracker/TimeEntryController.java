package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TimeEntry> create(TimeEntry entry) {
        //TimeEntry entry = new TimeEntry();
        return new ResponseEntity<>(timeEntryRepository.create(entry),HttpStatus.CREATED);
    }

    public ResponseEntity<TimeEntry> read(long id){
        return null;
    }

    public ResponseEntity<List<TimeEntry>> list(){
        return null;
    }

    public ResponseEntity<TimeEntry> update(long id, TimeEntry entry){
        return null;
    }

    public ResponseEntity<TimeEntry> delete(long id){
        return null;
    }

}

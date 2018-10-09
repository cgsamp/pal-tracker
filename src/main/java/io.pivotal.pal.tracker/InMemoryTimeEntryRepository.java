package io.pivotal.pal.tracker;

import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    public TimeEntry create(TimeEntry entry) {
        return entry;
    }

    public TimeEntry find(long id) {
        return null;
    }

    public List<TimeEntry> list() {
        return null;
    }

    public TimeEntry update(long id, TimeEntry entry){
        return null;
    }

    public void delete(long id) {}

}

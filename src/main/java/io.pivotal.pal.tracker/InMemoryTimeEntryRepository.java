package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long,TimeEntry> repo = new HashMap<>();
    private long index = 0L;

    public TimeEntry create(TimeEntry entry) {
        index++;
        entry.setId(index);
        repo.put(index, entry);
        return entry;
    }

    public TimeEntry find(long id) {
        TimeEntry entry = repo.get(id);
        return entry;
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(repo.values());
    }

    public TimeEntry update(long id, TimeEntry entry){
        entry.setId(id);
        TimeEntry updated = repo.replace(id, entry);
        if (updated == null) return null;
        return entry;
    }

    public void delete(long id) {
        repo.remove(id);
    }

}

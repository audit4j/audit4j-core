package org.audit4j.core.dto;

import static java.util.Collections.addAll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class represents a batch of records.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.6.0
 */
public class EventBatch<T extends Event> implements Iterable<T>{

    private List<T> events = new ArrayList<>();
    
    public EventBatch() {
        
    }
    
    public EventBatch(T... events) {
        addAll(this.events, events);
    }
    
    public EventBatch(List<T> events) {
        this.events = events;
    }
    
    /**
     * Add a event to the batch.
     *
     * @param record to add
     */
    public void addEvent(final T event) {
        events.add(event);
    }
    
    /**
     * Remove a event from the batch.
     *
     * @param event to remove
     */
    public void removeEvent  (final T record) {
        events.remove(record);
    }

    /**
     * Check if the batch is empty.
     *
     * @return true if the batch is empty, false otherwise
     */
    public boolean isEmpty() {
        return events.isEmpty();
    }
    
    /**
     * Get the size of the batch.
     *
     * @return the size of the batch
     */
    public long size() {
        return events.size();
    }
    
    public Event get(int index) {
        return events.get(index);
    }
    
    @Override
    public Iterator iterator() {
        return events.iterator();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventBatch batch = (EventBatch) o;

        return events.equals(batch.events);

    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Batch: {");
        stringBuilder.append("|");
        for (Event event : events) {
            stringBuilder.append('\t');
            stringBuilder.append(event);
            stringBuilder.append("|");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

}

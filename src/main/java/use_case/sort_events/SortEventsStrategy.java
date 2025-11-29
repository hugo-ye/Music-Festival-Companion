package use_case.sort_events;

import java.util.Comparator;

import entity.Event;

/**
 * Defines the contract for a sorting strategy applied to Event entities.
 * This interface extends Comparator, allowing implementations to define custom
 * comparison logic while remaining compatible with standard Java sorting utilities.
 */
public interface SortEventsStrategy extends Comparator<Event> {
}

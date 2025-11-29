package use_case.sort_events;

import java.util.Comparator;

/**
 * Defines the direction of the sorting operation using the Strategy Pattern.
 * This enum serves as a strategy provider where each constant ASCENDING, DESCENDING
 * implements a specific algorithm for modifying a Comparator.
 */
public enum SortEventsOrder {

    /**
     * Strategy that maintains the original order of the provided comparator.
     */
    ASCENDING {
        @Override
        public <T> Comparator<T> apply(Comparator<T> comparator) {
            return comparator;
        }
    },

    /**
     * Strategy that reverses the order of the provided comparator.
     */
    DESCENDING {
        @Override
        public <T> Comparator<T> apply(Comparator<T> comparator) {
            return comparator.reversed();
        }
    };

    /**
     * Applies the sorting strategy to the provided comparator.
     *
     * @param comparator The base comparator to be used (cannot be null).
     * @param <T>        The type of objects being compared.
     * @return A comparator adjusted according to this strategy (original or reversed).
     */
    public abstract <T> Comparator<T> apply(Comparator<T> comparator);
}

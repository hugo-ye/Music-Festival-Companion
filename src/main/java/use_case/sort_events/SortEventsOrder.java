package use_case.sort_events;

import java.util.Comparator;

/**
 * Defines the direction of the sorting operation using the **Strategy Pattern**.
 * <p>
 * This enum serves as a strategy provider where each constant ({@link #ASCENDING}, {@link #DESCENDING})
 * implements a specific algorithm for modifying a {@link Comparator}.
 */
public enum SortEventsOrder {

    /**
     * Strategy that maintains the natural order of the comparator.
     */
    ASCENDING {
        @Override
        public <T> Comparator<T> apply(Comparator<T> comparator) {
            return comparator;
        }
    },

    /**
     * Strategy that reverses the order of the comparator.
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
     * @param comparator The base comparator (assumed to be in natural/ascending order).
     * @param <T>        The type of objects being compared.
     * @return A comparator adjusted according to this strategy (original or reversed).
     */
    public abstract <T> Comparator<T> apply(Comparator<T> comparator);
}
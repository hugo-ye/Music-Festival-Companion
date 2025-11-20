package use_case.sort_events;

import java.util.Comparator;

public enum SortEventsOrder {
    ASCENDING {
        @Override
        public <T> Comparator<T> apply(Comparator<T> comparator) {
            return comparator;
        }
    },
    DESCENDING {
        @Override
        public <T> Comparator<T> apply(Comparator<T> comparator) {
            return comparator.reversed();
        }
    };

    // Abstract method that each constant must implement
    public abstract <T> Comparator<T> apply(Comparator<T> comparator);
}
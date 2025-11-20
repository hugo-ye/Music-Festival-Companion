package api;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Custom TypeAdapter for Gson to correctly serialize and deserialize LocalDate objects
 * using the standard ISO_LOCAL_DATE format ("yyyy-MM-dd").
 * This is crucial to avoid Java's module system reflection errors (InaccessibleObjectException).
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    // Define the standard format for persistence
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Serializer: Converts LocalDate object to a JSON primitive (String).
     */
    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        // Convert the LocalDate to a String using the defined format
        return new JsonPrimitive(date.format(FORMATTER));
    }

    /**
     * Deserializer: Converts JSON primitive (String) back to a LocalDate object.
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws JsonParseException {
        // We expect the JSON element to be a String
        String dateString = json.getAsString();

        try {
            // Parse the String back into a LocalDate using the defined format
            return LocalDate.parse(dateString, FORMATTER);
        } catch (Exception e) {
            // If parsing fails (e.g., malformed date string in the file), throw a JsonParseException
            throw new JsonParseException("Failed to parse LocalDate: " + dateString, e);
        }
    }
}
package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.EventList;

import java.io.FileWriter;
import java.io.IOException;

// hasnt been connected yet. just creating general functions
public class FileListDataAccessObject {
    private final String filePath = "userList.json";
    public void createEventList(EventList eventList, String userId) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath, true)) {
            gson.toJson(eventList, writer);
            System.out.println("data succesfully written");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

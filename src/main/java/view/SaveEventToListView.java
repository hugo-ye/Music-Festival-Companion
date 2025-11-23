package view;

import entity.Event;
import entity.EventList;
import interface_adapter.save_event_to_list.SaveEventToListController;
import interface_adapter.save_event_to_list.SaveEventToListViewModel;
import use_case.save_event_to_list.SaveEventToListInputBoundary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// stuck, current issue:
// in createUI, for loop of creating checkBox has problem, since I have not coded viewModel to get name of list

public class SaveEventToListView extends JPanel{
    private final SaveEventToListController controller;
    private final SaveEventToListViewModel viewModel;

//    public SaveEventToListView(){}

    // UI generate (based on number of eventList)
    public void createUI(){
        EventList[] eventLists = viewModel.getEventList();
        Event event = viewModel.getEvent();

        List<Integer> selectedNumber = new ArrayList<>();

        // n is number of existing eventList
        JFrame frame = new JFrame("Save Event to List View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        // basic parameter of frame layout
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;

        JLabel viewQuestion = new JLabel("Which list(s) you want to save your event in?");

        frame.add(viewQuestion, gbc);

        JCheckBox[] eventListCheckBoxes;

        if(eventLists.length == 0){
            // to be added, situation that
            JOptionPane.showMessageDialog(frame, "no EventList is created");
            return;
        }else{
            eventListCheckBoxes = new JCheckBox[eventLists.length];
            for(int i = 0; i < eventLists.length; i++){
                gbc.gridx = 0; gbc.gridy = 1 + i;
                eventListCheckBoxes[i] = new JCheckBox(eventLists[i].getName());
                frame.add(eventListCheckBoxes[i], gbc);
            }
        }

        gbc.gridx = 0; gbc.gridy = 1 + eventLists.length;
        gbc.anchor = GridBagConstraints.EAST;

        JButton confirmButton = new JButton("Confirm Saving");
        confirmButton.addActionListener(e -> {
            for(int i = 0; i < eventLists.length; i++){
                if(eventListCheckBoxes[i].isSelected()){
                    // if checkBox it selected, then the eventList is supposed to add an event inside
                    selectedNumber.add(i);
                }
            }
            EventList[] selectedEvents = new EventList[selectedNumber.size()];
            int i = 0;
            for(int num: selectedNumber){
                selectedEvents[i] = eventLists[num];
                i++;
            }

            controller.SaveEventToList(event, selectedEvents);
            JOptionPane.showMessageDialog(frame, viewModel.getMessage());
        });

        frame.add(confirmButton, gbc);

        frame.setVisible(true);
    }


    public SaveEventToListView(SaveEventToListController controller, SaveEventToListViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }


    public static void main(String[] args) {
    }
}

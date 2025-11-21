package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AllEventListsView extends JPanel implements PropertyChangeListener {

    private final String viewName = "event lists";
    // Implement View Models and Controllers here

    // Swing components
    private final JButton createListButton;
    private final JPanel eventsPanel;
    private final JButton masterViewButton;
    private final JButton backButton;

    public AllEventListsView() {
        // Instantiate the View Models and Controllers here
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        createListButton = new JButton("Create List");
        createListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("button pressed");
            }
        });

        topPanel.add(createListButton);
        add(topPanel, BorderLayout.NORTH);


        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JPanel masterListPanel = new JPanel();
        masterListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel masterLabel = new JLabel("Master List");
        masterViewButton = new JButton("View");

        masterListPanel.add(masterLabel);
        masterListPanel.add(masterViewButton);
        masterListPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        eventsPanel.add(masterListPanel);

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        backButton = new JButton("Back");
        bottomPanel.add(backButton);
        add(backButton, BorderLayout.SOUTH);

    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return this.viewName;
    }
}
package view;

import javax.swing.*;
        import java.awt.*;

public class CreateListDialog extends JDialog {

    private final JTextField nameField = new JTextField(20);
    private final JButton createButton = new JButton("Create");

    public CreateListDialog(Frame parent) {
        super(parent, "Create List", true);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Create List");
        panel.add(titleLabel);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        panel.add(namePanel);

        // Create button
        panel.add(createButton);
        setContentPane(panel); // Places the UI inside the window
        pack(); // Automatically resize
    }
    public String getEnteredName() {
        return nameField.getText();
    }
}
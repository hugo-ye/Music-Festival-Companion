package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The Dialog for the Display Notifications Use Case.
 */
public class NotificationDialog extends JDialog {
    private final String viewName = "notification";

    public NotificationDialog(Window owner, String message) {
        super(owner, "Notification", ModalityType.APPLICATION_MODAL);

        setUndecorated(true);

        final JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(ViewStyle.CARD_BACKGROUND);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ViewStyle.ACCENT_OUTLINE, 2),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // Header
        final JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ViewStyle.CARD_BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        final JLabel titleLabel = new JLabel("New Notification");
        ViewStyle.applyHeaderStyle(titleLabel);
        titleLabel.setForeground(ViewStyle.ACCENT_PRIMARY);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Message area
        final JTextArea messageArea = ViewStyle.createReadOnlyTextArea();
        messageArea.setText(message);
        messageArea.setCaretPosition(0);
        messageArea.setColumns(30);
        messageArea.setRows(5);
        messageArea.setFocusable(false);

        final JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(ViewStyle.CARD_BACKGROUND);

        // Bottom panel
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ViewStyle.CARD_BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        final JButton okButton = new JButton("Got it");
        ViewStyle.applyPrimaryButtonStyle(okButton);
        okButton.addActionListener(e -> dispose());
        buttonPanel.add(okButton);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.setContentPane(contentPanel);

        this.getRootPane().setDefaultButton(okButton);

        this.pack();

        if (this.getWidth() < 350) {
            this.setSize(350, this.getHeight());
        }

        this.setLocationRelativeTo(owner);
    }

    public String getViewName() {
        return this.viewName;
    }
}
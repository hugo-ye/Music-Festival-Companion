package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;
    private final ViewManagerModel viewManagerModel;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.cardLayout = cardLayout;
        this.views = views;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("property changed with evt: " + evt.getPropertyName() + "\n property changed new value is: " + evt.getNewValue());
        if (evt.getPropertyName().equals("state")) {
            String viewModelName = (String) evt.getNewValue();
            System.out.println(evt.getNewValue());
            cardLayout.show(views, viewModelName);
        }
    }
}

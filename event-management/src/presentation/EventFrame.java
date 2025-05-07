package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import dao.EventDAO;
import model.Event;

public class EventFrame extends JFrame {
    private JTextField nameField, locationField, dateField, timeField;
    private JButton addButton;
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private EventDAO eventDAO = new EventDAO();

    public EventFrame() {
        setTitle("📅 Event Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel haut pour les champs
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Nom de l'événement:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Lieu:"));
        locationField = new JTextField();
        inputPanel.add(locationField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Heure (HH:MM):"));
        timeField = new JTextField();
        inputPanel.add(timeField);

        addButton = new JButton("Ajouter Événement");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Tableau pour afficher les événements
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Lieu", "Date", "Heure"}, 0);
        eventTable = new JTable(tableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        // Action du bouton
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String location = locationField.getText();
            String date = dateField.getText();
            String time = timeField.getText();

            if (!name.isEmpty() && !location.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                Event event = new Event(name, location, date, time);
                eventDAO.addEvent(event);
                refreshTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Tous les champs sont requis.");
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        List<Event> events = eventDAO.getAllEvents();
        tableModel.setRowCount(0);
        for (Event ev : events) {
            tableModel.addRow(new Object[]{
                ev.getId(), ev.getName(), ev.getLocation(), ev.getDate(), ev.getTime()
            });
        }
    }

    private void clearFields() {
        nameField.setText("");
        locationField.setText("");
        dateField.setText("");
        timeField.setText("");
    }
}

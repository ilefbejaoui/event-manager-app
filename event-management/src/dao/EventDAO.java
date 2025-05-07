package dao;

import java.sql.*;
import java.util.*;
import model.Event;
import utils.ConnectionDB;

public class EventDAO {

    public void addEvent(Event event) {
        try {
            Connection conn = ConnectionDB.getConnection();
            String sql = "INSERT INTO events(name, location, date, time) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event.getName());
            ps.setString(2, event.getLocation());
            ps.setString(3, event.getDate());
            ps.setString(4, event.getTime());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        try {
            Connection conn = ConnectionDB.getConnection();
            String sql = "SELECT * FROM events";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getString("date"),
                    rs.getString("time")
                );
                list.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

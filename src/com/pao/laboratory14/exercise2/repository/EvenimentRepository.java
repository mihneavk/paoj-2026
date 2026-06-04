package com.pao.laboratory14.exercise2.repository;

import com.pao.laboratory14.exercise1.TipBilet;
import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EvenimentRepository implements Repository<Eveniment, Integer> {

    private final Connection connection;

    public EvenimentRepository() throws Exception {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void initSchema() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS evenimente");
            stmt.execute("CREATE TABLE evenimente (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nume TEXT NOT NULL, " +
                    "data TEXT NOT NULL, " +
                    "capacitate INTEGER, " +
                    "tip TEXT)");
        }
    }

    @Override
    public void save(Eveniment entity) throws SQLException {
        String sql = "INSERT INTO evenimente(nume, data, capacitate, tip) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getNume());
            ps.setString(2, entity.getData());
            ps.setInt(3, entity.getCapacitate());
            ps.setString(4, entity.getTip().name());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Optional<Eveniment> findById(Integer id) throws SQLException {
        return Optional.empty(); 
    }

    @Override
    public List<Eveniment> findAll() throws SQLException {
        List<Eveniment> events = new ArrayList<>();
        String sql = "SELECT * FROM evenimente ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                events.add(new Eveniment(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("data"),
                        rs.getInt("capacitate"),
                        TipBilet.valueOf(rs.getString("tip"))
                ));
            }
        }
        return events;
    }

    @Override
    public void update(Eveniment entity) throws SQLException {
    }

    @Override
    public void delete(Integer id) throws SQLException {
        deleteImpl(id);
    }

    public int deleteImpl(int id) throws SQLException {
        String sql = "DELETE FROM evenimente WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM evenimente";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
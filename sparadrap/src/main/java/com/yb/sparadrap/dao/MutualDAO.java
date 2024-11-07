package com.yb.sparadrap.dao;

import com.yb.sparadrap.model.Address;
import com.yb.sparadrap.model.Mutual;
import com.yb.sparadrap.model.enums.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MutualDAO implements DAO<Mutual, Integer> {

    @Override
    public void add(Mutual mutual) {
        String sql = "INSERT INTO mutuals (name, street, zip_code, city, department_code, phone_number, email, reimbursement_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, mutual.getName());
            pstmt.setString(2, mutual.getAddress().getStreet());
            pstmt.setString(3, mutual.getAddress().getZipCode());
            pstmt.setString(4, mutual.getAddress().getCity());
            pstmt.setString(5, mutual.getDepartment().toString()); // Assurez-vous que l'enum peut être converti en String
            pstmt.setString(6, mutual.getPhoneNumber());
            pstmt.setString(7, mutual.getEmail());
            pstmt.setDouble(8, mutual.getReimbursementRate());
            pstmt.executeUpdate();

            // Récupérer la clé générée (ID)
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mutual.setId(generatedKeys.getInt(1)); // Ajoutez un setter pour l'ID dans la classe Mutual
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mutual getById(Integer id) {
        Mutual mutual = null;
        String sql = "SELECT * FROM mutuals WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Address address = new Address(rs.getString("street"), rs.getString("zip_code"), rs.getString("city"));
                    Department department = Department.valueOf(rs.getString("department_code")); // Assurez-vous que l'enum peut être converti depuis une String
                    String name = rs.getString("name");
                    String phoneNumber = rs.getString("phone_number");
                    String email = rs.getString("email");
                    double reimbursementRate = rs.getDouble("reimbursement_rate");

                    mutual = new Mutual(name, address, department, phoneNumber, email, reimbursementRate);
                    mutual.setId(id); // Ajoutez un setter pour l'ID dans la classe Mutual
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mutual;
    }

    @Override
    public List<Mutual> getAll() {
        List<Mutual> mutuals = new ArrayList<>();
        String sql = "SELECT * FROM mutuals";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Address address = new Address(rs.getString("street"), rs.getString("zip_code"), rs.getString("city"));
                Department department = Department.valueOf(rs.getString("department_code"));
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                double reimbursementRate = rs.getDouble("reimbursement_rate");

                Mutual mutual = new Mutual(name, address, department, phoneNumber, email, reimbursementRate);
                mutual.setId(rs.getInt("id")); // Ajoutez un setter pour l'ID dans la classe Mutual
                mutuals.add(mutual);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mutuals;
    }

    @Override
    public void update(Mutual mutual) {
        String sql = "UPDATE mutuals SET name = ?, street = ?, zip_code = ?, city = ?, department_code = ?, phone_number = ?, email = ?, reimbursement_rate = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mutual.getName());
            pstmt.setString(2, mutual.getAddress().getStreet());
            pstmt.setString(3, mutual.getAddress().getZipCode());
            pstmt.setString(4, mutual.getAddress().getCity());
            pstmt.setString(5, mutual.getDepartment().toString());
            pstmt.setString(6, mutual.getPhoneNumber());
            pstmt.setString(7, mutual.getEmail());
            pstmt.setDouble(8, mutual.getReimbursementRate());
            pstmt.setInt(9, mutual.getId()); // Assurez-vous que la classe Mutual a un setter pour l'ID
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM mutuals WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

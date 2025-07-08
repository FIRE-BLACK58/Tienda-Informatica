package com.tienda.informatica.infrastructure.adapters.out.jdbc;

import com.tienda.informatica.domain.model.Fabricante;
import com.tienda.informatica.domain.ports.out.FabricanteRepositoryPort;
import com.tienda.informatica.infrastructure.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FabricanteRepositoryJdbcAdapter implements FabricanteRepositoryPort {
    @Override
    public void save(Fabricante fabricante) {
        String sql = "INSERT INTO Fabricantes (nombre, direccion, numero_empleados) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, fabricante.getNombre());
            pstmt.setString(2, fabricante.getDireccion());
            pstmt.setInt(3, fabricante.getNumeroEmpleados());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fabricante.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Fabricante> findById(Integer id) {
        String sql = "SELECT * FROM Fabricantes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Fabricante fabricante = new Fabricante();
                fabricante.setId(rs.getInt("id"));
                fabricante.setNombre(rs.getString("nombre"));
                fabricante.setDireccion(rs.getString("direccion"));
                fabricante.setNumeroEmpleados(rs.getInt("numero_empleados"));
                return Optional.of(fabricante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Fabricante> findAll() {
        List<Fabricante> fabricantes = new ArrayList<>();
        String sql = "SELECT * FROM Fabricantes";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Fabricante fabricante = new Fabricante();
                fabricante.setId(rs.getInt("id"));
                fabricante.setNombre(rs.getString("nombre"));
                fabricante.setDireccion(rs.getString("direccion"));
                fabricante.setNumeroEmpleados(rs.getInt("numero_empleados"));
                fabricantes.add(fabricante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fabricantes;
    }

    @Override
    public void update(Fabricante fabricante) {
        String sql = "UPDATE Fabricantes SET nombre = ?, direccion = ?, numero_empleados = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, fabricante.getNombre());
            pstmt.setString(2, fabricante.getDireccion());
            pstmt.setInt(3, fabricante.getNumeroEmpleados());
            pstmt.setInt(4, fabricante.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Fabricantes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
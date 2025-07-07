package com.tienda.informatica.infrastructure.adapters.out.jdbc;

import com.tienda.informatica.domain.model.Monitor;
import com.tienda.informatica.domain.ports.out.MonitorRepositoryPort;
import com.tienda.informatica.infrastructure.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MonitorRepositoryJdbcAdapter implements MonitorRepositoryPort {

    @Override
    public void save(Monitor monitor) {
        String sql = "INSERT INTO Monitores (codigo_producto, tamaño, resolucion) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, monitor.getCodigoProducto());
            pstmt.setString(2, monitor.getTamaño());
            pstmt.setString(3, monitor.getResolucion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar monitor", e);
        }
    }

    @Override
    public Optional<Monitor> findByCodigo(String codigo) {
        String sql = "SELECT * FROM Monitores WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Monitor monitor = new Monitor();
                monitor.setCodigoProducto(rs.getString("codigo_producto"));
                monitor.setTamaño(rs.getString("tamaño"));
                monitor.setResolucion(rs.getString("resolucion"));
                return Optional.of(monitor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar monitor por código", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Monitor> findAll() {
        List<Monitor> monitores = new ArrayList<>();
        String sql = "SELECT * FROM Monitores";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Monitor monitor = new Monitor();
                monitor.setCodigoProducto(rs.getString("codigo_producto"));
                monitor.setTamaño(rs.getString("tamaño"));
                monitor.setResolucion(rs.getString("resolucion"));
                monitores.add(monitor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los monitores", e);
        }
        return monitores;
    }

    @Override
    public void update(Monitor monitor) {
        String sql = "UPDATE Monitores SET tamaño = ?, resolucion = ? WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, monitor.getTamaño());
            pstmt.setString(2, monitor.getResolucion());
            pstmt.setString(3, monitor.getCodigoProducto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar monitor", e);
        }
    }

    @Override
    public void deleteByCodigo(String codigo) {
        String sql = "DELETE FROM Monitores WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar monitor", e);
        }
    }
}
package com.tienda.informatica.infrastructure.adapters.out.jdbc;

import com.tienda.informatica.domain.model.SoporteTecnico;
import com.tienda.informatica.infrastructure.config.DatabaseConfig;
import com.tienda.informatica.domain.ports.out.SoporteTecnicoRepositoryPort;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoporteTecnicoRepositoryJdbcAdapter implements SoporteTecnicoRepositoryPort {
    @Override
    public void save(SoporteTecnico soporte) {
        String sql = "INSERT INTO Soporte_Tecnico (cliente_id, producto_id, fecha_soporte, descripcion_problema, solucion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, soporte.getClienteId());
            pstmt.setInt(2, soporte.getProductoId());
            pstmt.setDate(3, Date.valueOf(soporte.getFechaSoporte()));
            pstmt.setString(4, soporte.getDescripcionProblema());
            pstmt.setString(5, soporte.getSolucion());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    soporte.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<SoporteTecnico> findById(Integer id) {
        String sql = "SELECT * FROM Soporte_Tecnico WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                SoporteTecnico soporte = new SoporteTecnico();
                soporte.setId(rs.getInt("id"));
                soporte.setClienteId(rs.getInt("cliente_id"));
                soporte.setProductoId(rs.getInt("producto_id"));
                soporte.setFechaSoporte(rs.getDate("fecha_soporte").toLocalDate());
                soporte.setDescripcionProblema(rs.getString("descripcion_problema"));
                soporte.setSolucion(rs.getString("solucion"));
                return Optional.of(soporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<SoporteTecnico> findAll() {
        List<SoporteTecnico> soportes = new ArrayList<>();
        String sql = "SELECT * FROM Soporte_Tecnico";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                SoporteTecnico soporte = new SoporteTecnico();
                soporte.setId(rs.getInt("id"));
                soporte.setClienteId(rs.getInt("cliente_id"));
                soporte.setProductoId(rs.getInt("producto_id"));
                soporte.setFechaSoporte(rs.getDate("fecha_soporte").toLocalDate());
                soporte.setDescripcionProblema(rs.getString("descripcion_problema"));
                soporte.setSolucion(rs.getString("solucion"));
                soportes.add(soporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return soportes;
    }

    @Override
    public void update(SoporteTecnico soporte) {
        String sql = "UPDATE Soporte_Tecnico SET cliente_id = ?, producto_id = ?, fecha_soporte = ?, descripcion_problema = ?, solucion = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, soporte.getClienteId());
            pstmt.setInt(2, soporte.getProductoId());
            pstmt.setDate(3, Date.valueOf(soporte.getFechaSoporte()));
            pstmt.setString(4, soporte.getDescripcionProblema());
            pstmt.setString(5, soporte.getSolucion());
            pstmt.setInt(6, soporte.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Soporte_Tecnico WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
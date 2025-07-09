package com.tienda.informatica.infrastructure.adapters.out.jdbc;

import com.tienda.informatica.domain.model.Impresora;
import com.tienda.informatica.domain.ports.out.ImpresoraRepositoryPort;
import com.tienda.informatica.infrastructure.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImpresoraRepositoryJdbcAdapter implements ImpresoraRepositoryPort {
    @Override
    public void save(Impresora impresora) {
        String sql = "INSERT INTO Impresoras (codigo_producto, tipo_tinta, es_multifuncional) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, impresora.getCodigoProducto());
            pstmt.setString(2, impresora.getTipoTinta());
            pstmt.setBoolean(3, impresora.getEsMultifuncional());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Impresora> findByCodigo(String codigo) {
        String sql = "SELECT * FROM Impresoras WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Impresora impresora = new Impresora();
                impresora.setCodigoProducto(rs.getString("codigo_producto"));
                impresora.setTipoTinta(rs.getString("tipo_tinta"));
                impresora.setEsMultifuncional(rs.getBoolean("es_multifuncional"));
                return Optional.of(impresora);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Impresora> findAll() {
        List<Impresora> impresoras = new ArrayList<>();
        String sql = "SELECT * FROM Impresoras";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Impresora impresora = new Impresora();
                impresora.setCodigoProducto(rs.getString("codigo_producto"));
                impresora.setTipoTinta(rs.getString("tipo_tinta"));
                impresora.setEsMultifuncional(rs.getBoolean("es_multifuncional"));
                impresoras.add(impresora);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return impresoras;
    }

    @Override
    public void update(Impresora impresora) {
        String sql = "UPDATE Impresoras SET tipo_tinta = ?, es_multifuncional = ? WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, impresora.getTipoTinta());
            pstmt.setBoolean(2, impresora.getEsMultifuncional());
            pstmt.setString(3, impresora.getCodigoProducto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByCodigo(String codigo) {
        String sql = "DELETE FROM Impresoras WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
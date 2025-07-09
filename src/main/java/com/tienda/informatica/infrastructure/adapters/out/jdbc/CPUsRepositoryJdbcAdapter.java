package com.tienda.informatica.infrastructure.adapters.out.jdbc;

import com.tienda.informatica.domain.model.CPU;
import com.tienda.informatica.domain.ports.out.CPURepositoryPort;
import com.tienda.informatica.infrastructure.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CPURepositoryJdbcAdapter implements CPURepositoryPort {
    @Override
    public void save(CPU cpu) {
        String sql = "INSERT INTO CPUs (codigo_producto, memoria_principal, velocidad) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cpu.getCodigoProducto());
            pstmt.setString(2, cpu.getMemoriaPrincipal());
            pstmt.setString(3, cpu.getVelocidad());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<CPU> findByCodigo(String codigo) {
        String sql = "SELECT * FROM CPUs WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                CPU cpu = new CPU();
                cpu.setCodigoProducto(rs.getString("codigo_producto"));
                cpu.setMemoriaPrincipal(rs.getString("memoria_principal"));
                cpu.setVelocidad(rs.getString("velocidad"));
                return Optional.of(cpu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<CPU> findAll() {
        List<CPU> cpus = new ArrayList<>();
        String sql = "SELECT * FROM CPUs";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                CPU cpu = new CPU();
                cpu.setCodigoProducto(rs.getString("codigo_producto"));
                cpu.setMemoriaPrincipal(rs.getString("memoria_principal"));
                cpu.setVelocidad(rs.getString("velocidad"));
                cpus.add(cpu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cpus;
    }

    @Override
    public void update(CPU cpu) {
        String sql = "UPDATE CPUs SET memoria_principal = ?, velocidad = ? WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cpu.getMemoriaPrincipal());
            pstmt.setString(2, cpu.getVelocidad());
            pstmt.setString(3, cpu.getCodigoProducto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByCodigo(String codigo) {
        String sql = "DELETE FROM CPUs WHERE codigo_producto = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.tienda.informatica.infrastructure.adapters.out.jdbc;

import com.tienda.informatica.domain.model.Producto;
import com.tienda.informatica.domain.ports.out.ProductoRepositoryPort;
import com.tienda.informatica.infrastructure.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoRepositoryJdbcAdapter implements ProductoRepositoryPort {
    @Override
    public void save(Producto producto) {
        String sql = "INSERT INTO Productos (codigo, modelo, tipo_producto) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getModelo());
            pstmt.setString(3, producto.getTipoProducto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Producto> findByCodigo(String codigo) {
        String sql = "SELECT * FROM Productos WHERE codigo = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getString("codigo"));
                producto.setModelo(rs.getString("modelo"));
                producto.setTipoProducto(rs.getString("tipo_producto"));
                return Optional.of(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Productos";
        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getString("codigo"));
                producto.setModelo(rs.getString("modelo"));
                producto.setTipoProducto(rs.getString("tipo_producto"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public void update(Producto producto) {
        String sql = "UPDATE Productos SET modelo = ?, tipo_producto = ? WHERE codigo = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getModelo());
            pstmt.setString(2, producto.getTipoProducto());
            pstmt.setString(3, producto.getCodigo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByCodigo(String codigo) {
        String sql = "DELETE FROM Productos WHERE codigo = ?";
        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
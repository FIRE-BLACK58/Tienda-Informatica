package com.tienda.informatica.infrastructure.adapters.out.jdbc;

import com.tienda.informatica.domain.model.ProductoAlquilado;
import com.tienda.informatica.domain.ports.out.ProductoAlquiladoRepositoryPort;
import com.tienda.informatica.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoAlquiladoRepositoryJdbcAdapter extends ProductoAlquiladoRepositoryPort {

    public void save(ProductoAlquilado producto) {
        String sql = "INSERT INTO ProductosAlquilados (codigo, precio_por_hora, tipo_producto, resolucion_maxima, capacidad_almacenamiento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, (String) producto.getCodigo());
            pstmt.setDouble(2, (double) producto.getPrecioPorHora());
            pstmt.setString(3, (String) producto.getTipoProducto());
            pstmt.setString(4, (String) producto.getResolucionMaxima()); // puede ser null
            pstmt.setInt(5, producto.getCapacidadAlmacenamiento()); // puede ser 0

            pstmt.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public Optional<ProductoAlquilado> findByCodigo(String codigo) {
        String sql = "SELECT * FROM ProductosAlquilados WHERE codigo = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ProductoAlquilado producto = mapResultSetToProducto(rs);
                return Optional.of(producto);
            }

        } catch (SQLException e) {
        }

        return Optional.empty();
    }

    public List<ProductoAlquilado> findAll() {
        List<ProductoAlquilado> productos = new ArrayList<>();
        String sql = "SELECT * FROM ProductosAlquilados";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                productos.add(mapResultSetToProducto(rs));
            }

        } catch (SQLException e) {
        }

        return productos;
    }

    public void update(ProductoAlquilado producto) {
        String sql = "UPDATE ProductosAlquilados SET precio_por_hora = ?, tipo_producto = ?, resolucion_maxima = ?, capacidad_almacenamiento = ? WHERE codigo = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, (double) producto.getPrecioPorHora());
            pstmt.setString(2, (String) producto.getTipoProducto());
            pstmt.setString(3, (String) producto.getResolucionMaxima());
            pstmt.setInt(4, producto.getCapacidadAlmacenamiento());
            pstmt.setString(5, (String) producto.getCodigo());

            pstmt.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public void deleteByCodigo(String codigo) {
        String sql = "DELETE FROM ProductosAlquilados WHERE codigo = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            pstmt.executeUpdate();

        } catch (SQLException e) {
        }
    }

    // Mapea un ResultSet a un objeto ProductoAlquilado
    private ProductoAlquilado mapResultSetToProducto(ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo");
        double precio = rs.getDouble("precio_por_hora");
        String tipo = rs.getString("tipo_producto");
        String resolucion = rs.getString("resolucion_maxima");
        int capacidad = rs.getInt("capacidad_almacenamiento");

        if ("MONITOR".equalsIgnoreCase(tipo)) {
            return new ProductoAlquilado(codigo, precio, resolucion);
        } else {
            return new ProductoAlquilado(codigo, precio, capacidad);
        }
    }
}

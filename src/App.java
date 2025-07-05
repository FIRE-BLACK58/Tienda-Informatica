import dao.ProductoDAO;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class App extends JFrame {
    public App() {
        setTitle("Tienda Informática - Productos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnas = {"Código", "Tipo", "Modelo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.listarProductos();
        for (Producto p : productos) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getTipo(), p.getModelo()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
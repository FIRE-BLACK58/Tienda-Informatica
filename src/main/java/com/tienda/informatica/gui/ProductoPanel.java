package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.ProductoService;
import com.tienda.informatica.domain.model.Producto;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.ProductoRepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductoPanel extends BaseCrudPanel<Producto> {
    private final ProductoService productoService;

    public ProductoPanel() {
        productoService = new ProductoService(new ProductoRepositoryJdbcAdapter());
        configurarBotones();
        configurarModeloTabla();
        actualizarTabla();
    }

    private void configurarBotones() {
        btnNuevo.addActionListener(e -> mostrarFormularioCrear());
        btnEditar.addActionListener(e -> editarRegistro());
        btnEliminar.addActionListener(e -> eliminarRegistro());
        btnActualizar.addActionListener(e -> actualizarTabla());
    }

    @Override
    protected void configurarModeloTabla() {
        tableModel.addColumn("Código");
        tableModel.addColumn("Modelo");
        tableModel.addColumn("Tipo de Producto");
    }

    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = createDialog("Nuevo Producto", 400, 200);
        JPanel panel = createFormPanel();
        
        JTextField txtCodigo = new JTextField();
        JTextField txtModelo = new JTextField();
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"CPU", "Impresora", "Monitor", "Otro"});
        
        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Modelo:"));
        panel.add(txtModelo);
        panel.add(new JLabel("Tipo de Producto:"));
        panel.add(cmbTipo);
        
        addFormButtons(dialog, panel, () -> {
            Producto producto = new Producto();
            producto.setCodigo(txtCodigo.getText());
            producto.setModelo(txtModelo.getText());
            producto.setTipoProducto(cmbTipo.getSelectedItem().toString());
            
            productoService.crearProducto(producto);
            mostrarMensajeExito("Producto creado exitosamente");
            actualizarTabla();
        });
        
        dialog.setVisible(true);
    }

    @Override
    protected void editarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un producto para editar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        Producto producto = productoService.getProductoByCodigo(codigo).orElse(null);
        
        if (producto == null) {
            mostrarMensajeError("Producto no encontrado");
            return;
        }
        
        JDialog dialog = createDialog("Editar Producto", 400, 200);
        JPanel panel = createFormPanel();
        
        JTextField txtCodigo = new JTextField(producto.getCodigo());
        txtCodigo.setEditable(false);
        JTextField txtModelo = new JTextField(producto.getModelo());
        JComboBox<String> cmbTipo = new JComboBox<>(
            new String[]{"CPU", "Impresora", "Monitor", "Otro"});
        cmbTipo.setSelectedItem(producto.getTipoProducto());
        
        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Modelo:"));
        panel.add(txtModelo);
        panel.add(new JLabel("Tipo de Producto:"));
        panel.add(cmbTipo);
        
        addFormButtons(dialog, panel, () -> {
            producto.setModelo(txtModelo.getText());
            producto.setTipoProducto(cmbTipo.getSelectedItem().toString());
            
            productoService.actualizarProducto(producto);
            mostrarMensajeExito("Producto actualizado exitosamente");
            actualizarTabla();
        });
        
        dialog.setVisible(true);
    }

    @Override
    protected void eliminarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un producto para eliminar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        String modelo = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el producto " + modelo + "?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            productoService.eliminarProducto(codigo);
            mostrarMensajeExito("Producto eliminado exitosamente");
            actualizarTabla();
        }
    }

    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Producto> productos = obtenerTodosLosRegistros();
        for (Producto p : productos) {
            tableModel.addRow(new Object[]{
                p.getCodigo(),
                p.getModelo(),
                p.getTipoProducto()
            });
        }
    }

    @Override
    protected List<Producto> obtenerTodosLosRegistros() {
        return productoService.getAllProductos();
    }

    // Métodos auxiliares reutilizables
    private JDialog createDialog(String title, int width, int height) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);
        return dialog;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private void addFormButtons(JDialog dialog, JPanel formPanel, Runnable saveAction) {
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnGuardar);
        
        btnGuardar.addActionListener(e -> {
            saveAction.run();
            dialog.dispose();
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        dialog.setLayout(new BorderLayout());
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    }
}
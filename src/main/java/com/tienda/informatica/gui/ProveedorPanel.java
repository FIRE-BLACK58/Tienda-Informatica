package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.ProveedorService;
import com.tienda.informatica.domain.model.Proveedor;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.ProveedorRepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProveedorPanel extends BaseCrudPanel<Proveedor> {
    private final ProveedorService proveedorService;

    public ProveedorPanel() {
        proveedorService = new ProveedorService(new ProveedorRepositoryJdbcAdapter());
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
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Dirección");
        tableModel.addColumn("Teléfono");
    }

    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nuevo Proveedor");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(5, 2, 5, 5));
        
        JTextField txtNombre = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtTelefono = new JTextField();
        
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Dirección:"));
        dialog.add(txtDireccion);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(txtTelefono);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(txtNombre.getText());
            proveedor.setDireccion(txtDireccion.getText());
            proveedor.setTelefono(txtTelefono.getText());
            
            proveedorService.crearProveedor(proveedor);
            mostrarMensajeExito("Proveedor creado exitosamente");
            actualizarTabla();
            dialog.dispose();
        });
        
        dialog.add(new JLabel());
        dialog.add(btnGuardar);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    protected void editarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un proveedor para editar");
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        Proveedor proveedor = proveedorService.getProveedorById(id).orElse(null);
        
        if (proveedor == null) {
            mostrarMensajeError("Proveedor no encontrado");
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Proveedor");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(5, 2, 5, 5));
        
        JTextField txtNombre = new JTextField(proveedor.getNombre());
        JTextField txtDireccion = new JTextField(proveedor.getDireccion());
        JTextField txtTelefono = new JTextField(proveedor.getTelefono());
        
        dialog.add(new JLabel("ID:"));
        dialog.add(new JLabel(proveedor.getId().toString()));
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Dirección:"));
        dialog.add(txtDireccion);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(txtTelefono);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            proveedor.setNombre(txtNombre.getText());
            proveedor.setDireccion(txtDireccion.getText());
            proveedor.setTelefono(txtTelefono.getText());
            
            proveedorService.actualizarProveedor(proveedor);
            mostrarMensajeExito("Proveedor actualizado exitosamente");
            actualizarTabla();
            dialog.dispose();
        });
        
        dialog.add(new JLabel());
        dialog.add(btnGuardar);
        
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    protected void eliminarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un proveedor para eliminar");
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar al proveedor: " + nombre + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                proveedorService.eliminarProveedor(id);
                mostrarMensajeExito("Proveedor eliminado exitosamente");
                actualizarTabla();
            } catch (Exception e) {
                mostrarMensajeError("Error al eliminar proveedor: " + e.getMessage());
            }
        }
    }

    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Proveedor> proveedores = obtenerTodosLosRegistros();
        for (Proveedor p : proveedores) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getDireccion(),
                p.getTelefono()
            });
        }
    }

    @Override
    protected List<Proveedor> obtenerTodosLosRegistros() {
        return proveedorService.getAllProveedores();
    }
}
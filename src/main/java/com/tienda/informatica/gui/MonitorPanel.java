package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.MonitorService;
import com.tienda.informatica.domain.model.Monitor;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.MonitorRepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MonitorPanel extends BaseCrudPanel<Monitor> {
    private final MonitorService monitorService;

    public MonitorPanel() {
        monitorService = new MonitorService(new MonitorRepositoryJdbcAdapter());
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
        tableModel.addColumn("Código Producto");
        tableModel.addColumn("Tamaño");
        tableModel.addColumn("Resolución");
    }

    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nuevo Monitor");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        
        JTextField txtCodigo = new JTextField();
        JTextField txtTamaño = new JTextField();
        JTextField txtResolucion = new JTextField();
        
        dialog.add(new JLabel("Código Producto:"));
        dialog.add(txtCodigo);
        dialog.add(new JLabel("Tamaño:"));
        dialog.add(txtTamaño);
        dialog.add(new JLabel("Resolución:"));
        dialog.add(txtResolucion);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            Monitor monitor = new Monitor();
            monitor.setCodigoProducto(txtCodigo.getText());
            monitor.setTamaño(txtTamaño.getText());
            monitor.setResolucion(txtResolucion.getText());
            
            monitorService.crearMonitor(monitor);
            mostrarMensajeExito("Monitor creado exitosamente");
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
            mostrarMensajeError("Seleccione un monitor para editar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        Monitor monitor = monitorService.getMonitorByCodigo(codigo).orElse(null);
        
        if (monitor == null) {
            mostrarMensajeError("Monitor no encontrado");
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Monitor");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        
        JTextField txtCodigo = new JTextField(monitor.getCodigoProducto());
        txtCodigo.setEditable(false);
        JTextField txtTamaño = new JTextField(monitor.getTamaño());
        JTextField txtResolucion = new JTextField(monitor.getResolucion());
        
        dialog.add(new JLabel("Código Producto:"));
        dialog.add(txtCodigo);
        dialog.add(new JLabel("Tamaño:"));
        dialog.add(txtTamaño);
        dialog.add(new JLabel("Resolución:"));
        dialog.add(txtResolucion);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            monitor.setTamaño(txtTamaño.getText());
            monitor.setResolucion(txtResolucion.getText());
            
            monitorService.actualizarMonitor(monitor);
            mostrarMensajeExito("Monitor actualizado exitosamente");
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
            mostrarMensajeError("Seleccione un monitor para eliminar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        String tamaño = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar el monitor de " + tamaño + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            monitorService.eliminarMonitor(codigo);
            mostrarMensajeExito("Monitor eliminado exitosamente");
            actualizarTabla();
        }
    }

    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Monitor> monitores = obtenerTodosLosRegistros();
        for (Monitor monitor : monitores) {
            tableModel.addRow(new Object[]{
                monitor.getCodigoProducto(),
                monitor.getTamaño(),
                monitor.getResolucion()
            });
        }
    }

    @Override
    protected List<Monitor> obtenerTodosLosRegistros() {
        return monitorService.getAllMonitores();
    }
}
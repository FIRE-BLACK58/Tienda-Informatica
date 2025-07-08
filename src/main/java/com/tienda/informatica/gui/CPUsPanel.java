package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.CPUService;
import com.tienda.informatica.domain.model.CPU;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.CPURepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CPUPanel extends BaseCrudPanel<CPU> {
    private final CPUService cpuService;

    public CPUPanel() {
        cpuService = new CPUService(new CPURepositoryJdbcAdapter());
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
        tableModel.addColumn("Memoria Principal");
        tableModel.addColumn("Velocidad");
    }

    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nueva CPU");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        
        JTextField txtCodigo = new JTextField();
        JTextField txtMemoria = new JTextField();
        JTextField txtVelocidad = new JTextField();
        
        dialog.add(new JLabel("Código Producto:"));
        dialog.add(txtCodigo);
        dialog.add(new JLabel("Memoria Principal:"));
        dialog.add(txtMemoria);
        dialog.add(new JLabel("Velocidad:"));
        dialog.add(txtVelocidad);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            CPU cpu = new CPU();
            cpu.setCodigoProducto(txtCodigo.getText());
            cpu.setMemoriaPrincipal(txtMemoria.getText());
            cpu.setVelocidad(txtVelocidad.getText());
            
            cpuService.crearCPU(cpu);
            mostrarMensajeExito("CPU creada exitosamente");
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
            mostrarMensajeError("Seleccione una CPU para editar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        CPU cpu = cpuService.getCPUByCodigo(codigo).orElse(null);
        
        if (cpu == null) {
            mostrarMensajeError("CPU no encontrada");
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar CPU");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        
        JTextField txtCodigo = new JTextField(cpu.getCodigoProducto());
        txtCodigo.setEditable(false);
        JTextField txtMemoria = new JTextField(cpu.getMemoriaPrincipal());
        JTextField txtVelocidad = new JTextField(cpu.getVelocidad());
        
        dialog.add(new JLabel("Código Producto:"));
        dialog.add(txtCodigo);
        dialog.add(new JLabel("Memoria Principal:"));
        dialog.add(txtMemoria);
        dialog.add(new JLabel("Velocidad:"));
        dialog.add(txtVelocidad);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            cpu.setMemoriaPrincipal(txtMemoria.getText());
            cpu.setVelocidad(txtVelocidad.getText());
            
            cpuService.actualizarCPU(cpu);
            mostrarMensajeExito("CPU actualizada exitosamente");
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
            mostrarMensajeError("Seleccione una CPU para eliminar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        String memoria = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar la CPU con memoria " + memoria + "?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cpuService.eliminarCPU(codigo);
            mostrarMensajeExito("CPU eliminada exitosamente");
            actualizarTabla();
        }
    }

    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<CPU> cpus = obtenerTodosLosRegistros();
        for (CPU cpu : cpus) {
            tableModel.addRow(new Object[]{
                cpu.getCodigoProducto(),
                cpu.getMemoriaPrincipal(),
                cpu.getVelocidad()
            });
        }
    }

    @Override
    protected List<CPU> obtenerTodosLosRegistros() {
        return cpuService.getAllCPUs();
    }
}
package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.SoporteTecnicoService;
import com.tienda.informatica.domain.model.SoporteTecnico;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.SoporteTecnicoRepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class SoporteTecnicoPanel extends BaseCrudPanel<SoporteTecnico> {
    private final SoporteTecnicoService soporteService;

    public SoporteTecnicoPanel() {
        soporteService = new SoporteTecnicoService(new SoporteTecnicoRepositoryJdbcAdapter());
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
        tableModel.addColumn("Cliente ID");
        tableModel.addColumn("Producto ID");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Problema");
        tableModel.addColumn("Solución");
    }

    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nuevo Soporte Técnico");
        dialog.setModal(true);
        dialog.setSize(600, 400);
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JSpinner spnClienteId = new JSpinner(new SpinnerNumberModel(
            Integer.valueOf(1),    // valor inicial
            Integer.valueOf(1),    // mínimo
            Integer.valueOf(10000), // máximo
            Integer.valueOf(1)     // incremento
        ));
        JSpinner spnProductoId = new JSpinner(new SpinnerNumberModel(
            Integer.valueOf(1),    // valor inicial
            Integer.valueOf(1),    // mínimo
            Integer.valueOf(10000), // máximo
            Integer.valueOf(1)     // incremento
        ));
        JTextField txtFecha = new JTextField(LocalDate.now().toString());
        JTextArea txtProblema = new JTextArea(3, 20);
        JTextArea txtSolucion = new JTextArea(3, 20);
        
        panel.add(new JLabel("Cliente ID:"));
        panel.add(spnClienteId);
        panel.add(new JLabel("Producto ID:"));
        panel.add(spnProductoId);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Problema:"));
        panel.add(new JScrollPane(txtProblema));
        panel.add(new JLabel("Solución:"));
        panel.add(new JScrollPane(txtSolucion));
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            SoporteTecnico soporte = new SoporteTecnico();
            soporte.setClienteId((Integer) spnClienteId.getValue());
            soporte.setProductoId((Integer) spnProductoId.getValue());
            soporte.setFechaSoporte(LocalDate.parse(txtFecha.getText()));
            soporte.setDescripcionProblema(txtProblema.getText());
            soporte.setSolucion(txtSolucion.getText());
            
            soporteService.crearSoporteTecnico(soporte);
            mostrarMensajeExito("Soporte técnico creado exitosamente");
            actualizarTabla();
            dialog.dispose();
        });
        
        panel.add(new JLabel());
        panel.add(btnGuardar);
        
        dialog.add(panel);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    protected void editarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un soporte técnico para editar");
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        SoporteTecnico soporte = soporteService.getSoporteTecnicoById(id).orElse(null);
        
        if (soporte == null) {
            mostrarMensajeError("Soporte técnico no encontrado");
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Soporte Técnico");
        dialog.setModal(true);
        dialog.setSize(600, 400);
        
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JSpinner spnClienteId = new JSpinner(new SpinnerNumberModel(
            Integer.valueOf(1),    // valor inicial
            Integer.valueOf(1),    // mínimo
            Integer.valueOf(10000), // máximo
            Integer.valueOf(1)     // incremento
        ));
        JSpinner spnProductoId = new JSpinner(new SpinnerNumberModel(
            Integer.valueOf(1),    // valor inicial
            Integer.valueOf(1),    // mínimo
            Integer.valueOf(10000), // máximo
            Integer.valueOf(1)     // incremento
        ));
        JTextField txtFecha = new JTextField(soporte.getFechaSoporte().toString());
        JTextArea txtProblema = new JTextArea(soporte.getDescripcionProblema(), 3, 20);
        JTextArea txtSolucion = new JTextArea(soporte.getSolucion(), 3, 20);
        
        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(soporte.getId().toString()));
        panel.add(new JLabel("Cliente ID:"));
        panel.add(spnClienteId);
        panel.add(new JLabel("Producto ID:"));
        panel.add(spnProductoId);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Problema:"));
        panel.add(new JScrollPane(txtProblema));
        panel.add(new JLabel("Solución:"));
        panel.add(new JScrollPane(txtSolucion));
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            soporte.setClienteId((Integer) spnClienteId.getValue());
            soporte.setProductoId((Integer) spnProductoId.getValue());
            soporte.setFechaSoporte(LocalDate.parse(txtFecha.getText()));
            soporte.setDescripcionProblema(txtProblema.getText());
            soporte.setSolucion(txtSolucion.getText());
            
            soporteService.actualizarSoporteTecnico(soporte);
            mostrarMensajeExito("Soporte técnico actualizado exitosamente");
            actualizarTabla();
            dialog.dispose();
        });
        
        panel.add(new JLabel());
        panel.add(btnGuardar);
        
        dialog.add(panel);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    protected void eliminarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un registro de soporte técnico para eliminar");
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        LocalDate fecha = LocalDate.parse(tableModel.getValueAt(selectedRow, 3).toString());
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar el soporte técnico del " + fecha + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                soporteService.eliminarSoporteTecnico(id);
                mostrarMensajeExito("Soporte técnico eliminado exitosamente");
                actualizarTabla();
            } catch (Exception e) {
                mostrarMensajeError("Error al eliminar soporte técnico: " + e.getMessage());
            }
        }
    }

    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<SoporteTecnico> soportes = obtenerTodosLosRegistros();
        for (SoporteTecnico s : soportes) {
            tableModel.addRow(new Object[]{
                s.getId(),
                s.getClienteId(),
                s.getProductoId(),
                s.getFechaSoporte(),
                s.getDescripcionProblema(),
                s.getSolucion()
            });
        }
    }

    @Override
    protected List<SoporteTecnico> obtenerTodosLosRegistros() {
        return soporteService.getAllSoportesTecnicos();
    }
}
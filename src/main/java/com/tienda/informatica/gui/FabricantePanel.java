package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.FabricanteService;
import com.tienda.informatica.domain.model.Fabricante;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.FabricanteRepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FabricantePanel extends BaseCrudPanel<Fabricante> {
    private final FabricanteService fabricanteService;

    public FabricantePanel() {
        fabricanteService = new FabricanteService(new FabricanteRepositoryJdbcAdapter());
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
        tableModel.addColumn("Núm. Empleados");
    }

    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = createDialog("Nuevo Fabricante", 400, 250);
        JPanel panel = createFormPanel();
        
        JTextField txtNombre = new JTextField();
        JTextField txtDireccion = new JTextField();
        JSpinner spnEmpleados = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
        
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Núm. Empleados:"));
        panel.add(spnEmpleados);
        
        addFormButtons(dialog, panel, () -> {
            Fabricante fabricante = new Fabricante();
            fabricante.setNombre(txtNombre.getText());
            fabricante.setDireccion(txtDireccion.getText());
            fabricante.setNumeroEmpleados((Integer) spnEmpleados.getValue());
            
            fabricanteService.crearFabricante(fabricante);
            mostrarMensajeExito("Fabricante creado exitosamente");
            actualizarTabla();
        });
        
        dialog.setVisible(true);
    }

    @Override
    protected void editarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un fabricante para editar");
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        Fabricante fabricante = fabricanteService.getFabricanteById(id).orElse(null);
        
        if (fabricante == null) {
            mostrarMensajeError("Fabricante no encontrado");
            return;
        }
        
        JDialog dialog = createDialog("Editar Fabricante", 400, 250);
        JPanel panel = createFormPanel();
        
        JTextField txtNombre = new JTextField(fabricante.getNombre());
        JTextField txtDireccion = new JTextField(fabricante.getDireccion());
        //JSpinner spnEmpleados = new JSpinner(new SpinnerNumberModel(
           // fabricante.getNumeroEmpleados(), 0, 100000, 1));
        JSpinner spnEmpleados = new JSpinner(new SpinnerNumberModel(
            Integer.valueOf(0),  // value
            Integer.valueOf(0),  // min
            Integer.valueOf(100000),  // max
            Integer.valueOf(1)   // step
        ));
        
        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(fabricante.getId().toString()));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtDireccion);
        panel.add(new JLabel("Núm. Empleados:"));
        panel.add(spnEmpleados);
        
        addFormButtons(dialog, panel, () -> {
            fabricante.setNombre(txtNombre.getText());
            fabricante.setDireccion(txtDireccion.getText());
            fabricante.setNumeroEmpleados((Integer) spnEmpleados.getValue());
            
            fabricanteService.actualizarFabricante(fabricante);
            mostrarMensajeExito("Fabricante actualizado exitosamente");
            actualizarTabla();
        });
        
        dialog.setVisible(true);
    }

    @Override
    protected void eliminarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            mostrarMensajeError("Seleccione un fabricante para eliminar");
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el fabricante " + nombre + "?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            fabricanteService.eliminarFabricante(id);
            mostrarMensajeExito("Fabricante eliminado exitosamente");
            actualizarTabla();
        }
    }

    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Fabricante> fabricantes = obtenerTodosLosRegistros();
        for (Fabricante f : fabricantes) {
            tableModel.addRow(new Object[]{
                f.getId(),
                f.getNombre(),
                f.getDireccion(),
                f.getNumeroEmpleados()
            });
        }
    }

    @Override
    protected List<Fabricante> obtenerTodosLosRegistros() {
        return fabricanteService.getAllFabricantes();
    }

    // Métodos auxiliares para creación de formularios
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
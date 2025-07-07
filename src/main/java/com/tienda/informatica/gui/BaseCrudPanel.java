package com.tienda.informatica.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public abstract class BaseCrudPanel<T> extends JPanel {
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected JButton btnNuevo, btnEditar, btnEliminar, btnActualizar;
    protected JTextField txtBuscar;
    protected JPanel panelBusqueda;

    public BaseCrudPanel() {
        setLayout(new BorderLayout());
        initToolbar();
        initTable();
        initBusqueda();
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void initToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        btnNuevo = createToolButton("Nuevo", "icons/add.png");
        btnEditar = createToolButton("Editar", "icons/edit.png");
        btnEliminar = createToolButton("Eliminar", "icons/delete.png");
        btnActualizar = createToolButton("Actualizar", "icons/refresh.png");
        
        toolBar.add(btnNuevo);
        toolBar.add(btnEditar);
        toolBar.add(btnEliminar);
        toolBar.addSeparator();
        toolBar.add(btnActualizar);
        
        add(toolBar, BorderLayout.NORTH);
    }

    private JButton createToolButton(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            button.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        } catch (Exception e) {
            // Si no hay icono, continuar sin él
        }
        button.setFocusPainted(false);
        return button;
    }

    private void initTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initBusqueda() {
        panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        
        btnBuscar.addActionListener(this::buscarRegistros);
        txtBuscar.addActionListener(this::buscarRegistros);
        
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        add(panelBusqueda, BorderLayout.SOUTH);
    }

    protected void buscarRegistros(ActionEvent e) {
        // Implementación específica en cada panel
        actualizarTabla();
    }

    protected void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    protected void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // Métodos abstractos que deben implementar las clases hijas
    protected abstract void configurarModeloTabla();
    protected abstract void mostrarFormularioCrear();
    protected abstract void editarRegistro();
    protected abstract void eliminarRegistro();
    protected abstract void actualizarTabla();
    protected abstract List<T> obtenerTodosLosRegistros();
}
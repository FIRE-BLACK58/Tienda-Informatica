package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.ClienteService;
import com.tienda.informatica.domain.model.Cliente;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.ClienteRepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientePanel extends BaseCrudPanel<Cliente> {
    private ClienteService clienteService;
    
    public ClientePanel() {
        clienteService = new ClienteService(new ClienteRepositoryJdbcAdapter());
    }
    
    @Override
    protected void configurarModeloTabla() {
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Dirección");
        tableModel.addColumn("Teléfono");
        tableModel.addColumn("Email");
    }
    
    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nuevo Cliente");
        dialog.setModal(true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2));
        
        JTextField txtNombre = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtEmail = new JTextField();
        
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Dirección:"));
        dialog.add(txtDireccion);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(txtTelefono);
        dialog.add(new JLabel("Email:"));
        dialog.add(txtEmail);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            Cliente cliente = new Cliente();
            cliente.setNombre(txtNombre.getText());
            cliente.setDireccion(txtDireccion.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setEmail(txtEmail.getText());
            
            clienteService.crearCliente(cliente);
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
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        Cliente cliente = clienteService.getClienteById(id).orElse(null);
        
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Cliente");
        dialog.setModal(true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2));
        
        JTextField txtNombre = new JTextField(cliente.getNombre());
        JTextField txtDireccion = new JTextField(cliente.getDireccion());
        JTextField txtTelefono = new JTextField(cliente.getTelefono());
        JTextField txtEmail = new JTextField(cliente.getEmail());
        
        dialog.add(new JLabel("ID:"));
        dialog.add(new JLabel(cliente.getId().toString()));
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Dirección:"));
        dialog.add(txtDireccion);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(txtTelefono);
        dialog.add(new JLabel("Email:"));
        dialog.add(txtEmail);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            cliente.setNombre(txtNombre.getText());
            cliente.setDireccion(txtDireccion.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setEmail(txtEmail.getText());
            
            clienteService.actualizarCliente(cliente);
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
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el cliente con ID " + id + "?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            clienteService.eliminarCliente(id);
            actualizarTabla();
        }
    }
    
    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = obtenerTodosLosRegistros();
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getDireccion(),
                c.getTelefono(),
                c.getEmail()
            });
        }
    }
    
    @Override
    protected List<Cliente> obtenerTodosLosRegistros() {
        return clienteService.getAllClientes();
    }
}
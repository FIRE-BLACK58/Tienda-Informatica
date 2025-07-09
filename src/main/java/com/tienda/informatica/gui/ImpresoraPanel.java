package com.tienda.informatica.gui;

import com.tienda.informatica.application.service.ImpresoraService;
import com.tienda.informatica.domain.model.Impresora;
import com.tienda.informatica.infrastructure.adapters.out.jdbc.ImpresoraRepositoryJdbcAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ImpresoraPanel extends BaseCrudPanel<Impresora> {
    private final ImpresoraService impresoraService;

    public ImpresoraPanel() {
        impresoraService = new ImpresoraService(new ImpresoraRepositoryJdbcAdapter());
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
        tableModel.addColumn("Tipo Tinta");
        tableModel.addColumn("Multifuncional");
    }

    @Override
    protected void mostrarFormularioCrear() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nueva Impresora");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        
        JTextField txtCodigo = new JTextField();
        JTextField txtTinta = new JTextField();
        JCheckBox chkMultifuncional = new JCheckBox();
        
        dialog.add(new JLabel("Código Producto:"));
        dialog.add(txtCodigo);
        dialog.add(new JLabel("Tipo Tinta:"));
        dialog.add(txtTinta);
        dialog.add(new JLabel("Multifuncional:"));
        dialog.add(chkMultifuncional);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            Impresora impresora = new Impresora();
            impresora.setCodigoProducto(txtCodigo.getText());
            impresora.setTipoTinta(txtTinta.getText());
            impresora.setEsMultifuncional(chkMultifuncional.isSelected());
            
            impresoraService.crearImpresora(impresora);
            mostrarMensajeExito("Impresora creada exitosamente");
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
            mostrarMensajeError("Seleccione una impresora para editar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        Impresora impresora = impresoraService.getImpresoraByCodigo(codigo).orElse(null);
        
        if (impresora == null) {
            mostrarMensajeError("Impresora no encontrada");
            return;
        }
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Impresora");
        dialog.setModal(true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        
        JTextField txtCodigo = new JTextField(impresora.getCodigoProducto());
        txtCodigo.setEditable(false);
        JTextField txtTinta = new JTextField(impresora.getTipoTinta());
        JCheckBox chkMultifuncional = new JCheckBox();
        chkMultifuncional.setSelected(impresora.getEsMultifuncional());
        
        dialog.add(new JLabel("Código Producto:"));
        dialog.add(txtCodigo);
        dialog.add(new JLabel("Tipo Tinta:"));
        dialog.add(txtTinta);
        dialog.add(new JLabel("Multifuncional:"));
        dialog.add(chkMultifuncional);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            impresora.setTipoTinta(txtTinta.getText());
            impresora.setEsMultifuncional(chkMultifuncional.isSelected());
            
            impresoraService.actualizarImpresora(impresora);
            mostrarMensajeExito("Impresora actualizada exitosamente");
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
            mostrarMensajeError("Seleccione una impresora para eliminar");
            return;
        }
        
        String codigo = (String) tableModel.getValueAt(selectedRow, 0);
        String tipoTinta = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar la impresora con tinta " + tipoTinta + "?", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            impresoraService.eliminarImpresora(codigo);
            mostrarMensajeExito("Impresora eliminada exitosamente");
            actualizarTabla();
        }
    }

    @Override
    protected void actualizarTabla() {
        tableModel.setRowCount(0);
        List<Impresora> impresoras = obtenerTodosLosRegistros();
        for (Impresora impresora : impresoras) {
            tableModel.addRow(new Object[]{
                impresora.getCodigoProducto(),
                impresora.getTipoTinta(),
                impresora.getEsMultifuncional() ? "Sí" : "No"
            });
        }
    }

    @Override
    protected List<Impresora> obtenerTodosLosRegistros() {
        return impresoraService.getAllImpresoras();
    }
}
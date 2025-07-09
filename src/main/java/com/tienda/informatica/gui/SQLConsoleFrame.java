package com.tienda.informatica.gui;

import com.tienda.informatica.infrastructure.config.DatabaseConfig;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.*;

public class SQLConsoleFrame extends JFrame {
    private JTextArea txtQuery;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;
    
    public SQLConsoleFrame() {
        configurarVentana();
        initComponents();
    }

    private void configurarVentana() {
        setTitle("Consola SQL");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("icons/sql.png")).getImage());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel superior con el área de consulta
        JPanel topPanel = new JPanel(new BorderLayout());
        txtQuery = new JTextArea();
        txtQuery.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnExecute = new JButton("Ejecutar");
        btnExecute.setMnemonic('E');
        btnExecute.addActionListener(this::executeQuery);
        
        JButton btnClear = new JButton("Limpiar");
        btnClear.setMnemonic('L');
        btnClear.addActionListener(e -> {
            txtQuery.setText("");
            resultTable.setModel(new DefaultTableModel());
        });
        
        buttonPanel.add(btnExecute);
        buttonPanel.add(btnClear);
        
        topPanel.add(new JScrollPane(txtQuery), BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Panel central con resultados
        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Barra de estado
        lblStatus = new JLabel("Listo");
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(lblStatus, BorderLayout.SOUTH);
        
        // Configurar atajos de teclado
        configurarAtajosTeclado();
    }

    private void configurarAtajosTeclado() {
        // F5 para ejecutar consulta
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "executeQuery");
        getRootPane().getActionMap().put("executeQuery", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeQuery(null);
            }
        });
        
        // Ctrl+Enter para ejecutar consulta
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK), "executeQueryCtrlEnter");
        getRootPane().getActionMap().put("executeQueryCtrlEnter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeQuery(null);
            }
        });
    }

    private void executeQuery(ActionEvent e) {
        String query = txtQuery.getText().trim();
        if (query.isEmpty()) {
            lblStatus.setText("Error: Ingrese una consulta SQL");
            return;
        }
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            
            if (query.toUpperCase().startsWith("SELECT")) {
                // Consulta que devuelve resultados
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData metaData = rs.getMetaData();
                
                // Configurar modelo de tabla
                int columnCount = metaData.getColumnCount();
                tableModel = new DefaultTableModel();
                
                // Añadir columnas
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }
                
                // Añadir filas
                int rowCount = 0;
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i-1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                    rowCount++;
                }
                
                resultTable.setModel(tableModel);
                lblStatus.setText("Consulta ejecutada. " + rowCount + " filas devueltas.");
                
                // Ajustar tamaño de columnas
                for (int i = 0; i < resultTable.getColumnCount(); i++) {
                    resultTable.getColumnModel().getColumn(i).setPreferredWidth(150);
                }
            } else {
                // Consulta de actualización
                int affectedRows = stmt.executeUpdate(query);
                lblStatus.setText("Consulta ejecutada. " + affectedRows + " filas afectadas.");
                tableModel = new DefaultTableModel();
                resultTable.setModel(tableModel);
            }
        } catch (SQLException ex) {
            lblStatus.setText("Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error al ejecutar consulta:\n" + ex.getMessage(),
                "Error SQL", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
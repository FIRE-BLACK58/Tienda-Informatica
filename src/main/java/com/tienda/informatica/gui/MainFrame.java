package com.tienda.informatica.gui;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        // Configuración básica de la ventana
        setTitle("Sistema de Gestión - Tienda de Informática");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 700);
        
        // Crear panel de pestañas
        tabbedPane = new JTabbedPane();
        
        // Agregar pestañas (aquí solo ejemplo, ajusta según tus necesidades)
        tabbedPane.addTab("Productos", new ProductoPanel());
        tabbedPane.addTab("Fabricantes", new FabricantePanel());
        tabbedPane.addTab("Monitores", new MonitorPanel());
        tabbedPane.addTab("Clientes", new ClientePanel());
        tabbedPane.addTab("CPU", new CPUPanel());
        tabbedPane.addTab("Impresoras", new ImpresoraPanel());
        tabbedPane.addTab("Proveedores", new ProveedorPanel());
        tabbedPane.addTab("Productos", new ProductoPanel());
        tabbedPane.addTab("Soporte Tecnico", new SoporteTecnicoPanel());
        //tabbedPane.addTab("SQL", new SQLConsoleFrame());
        
        add(tabbedPane);
        
        // Centrar ventana
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Configurar el look and feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Crear y mostrar la GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
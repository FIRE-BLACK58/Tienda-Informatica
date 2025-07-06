package com.tienda;

import com.formdev.flatlaf.FlatDarkLaf;
import com.tienda.informatica.gui.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel con manejo de errores
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("Error configurando FlatLaf:");
            e.printStackTrace();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("Error configurando Look and Feel del sistema:");
                ex.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación:");
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error crítico al iniciar la aplicación:\n" + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
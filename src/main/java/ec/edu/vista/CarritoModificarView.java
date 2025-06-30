package ec.edu.vista;

import ec.edu.controlador.CarritoController;
import ec.edu.modelo.ItemCarrito;
import ec.edu.modelo.Carrito;
import java.util.List;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarritoModificarView extends JInternalFrame {
    private JPanel PanelPrincipal;
    private JLabel LblTitulo;
    private JTable TblProductos;
    private JComboBox<Integer> CBoxCantidad;
    private JButton BtnEliminar;
    private JButton BtnAgregar;
    private JLabel LblSubtotal;
    private JTextField TxtSubtotal;
    private JLabel LblIVA;
    private JTextField TxtIVA;
    private JLabel LblTotal;
    private JTextField TxtTotal;
    private JButton BtnActualizarCantidad;
    private JTextField TxtCodigo;
    private JComboBox<Carrito> CBoxCodigo;
    private JLabel LblCantidad;
    private JLabel LblAgregar;
    private JLabel LblCodigo;
    private JComboBox<Carrito> CBoxCarritos;
    private JLabel LblCarritos;

    private DefaultTableModel modelo;
    private CarritoController carritoController;

    public CarritoModificarView() {
        setTitle("Modificar Carrito");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setContentPane(PanelPrincipal);

        // Configurar tabla
        modelo = new DefaultTableModel();
        Object[] columnas = {"Código", "Nombre", "Precio", "Cantidad", "Subtotal"};
        modelo.setColumnIdentifiers(columnas);
        TblProductos.setModel(modelo);
        TblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        for (int i = 1; i <= 20; i++) {
            CBoxCantidad.addItem(i);
        }


        TblProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int filaSeleccionada = TblProductos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    try {
                        int cantidad = Integer.parseInt(TblProductos.getValueAt(filaSeleccionada, 3).toString());
                        CBoxCantidad.setSelectedItem(cantidad);
                    } catch (Exception ex) {
                        CBoxCantidad.setSelectedItem(1);
                    }
                }
            }
        });


        CBoxCarritos.addActionListener(e -> {
            Carrito carritoSeleccionado = (Carrito) CBoxCarritos.getSelectedItem();
            if (carritoSeleccionado != null) {
                int codigoCarrito = carritoSeleccionado.getCodigo();
                carritoController.establecerCarritoActual(codigoCarrito);
                carritoController.cargarProductosCarritoEnModificar();
            }

        });


        BtnActualizarCantidad.addActionListener(e -> {
            int filaSeleccionada = TblProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para modificar.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codigoProducto = TblProductos.getValueAt(filaSeleccionada, 0).toString();
            Integer nuevaCantidad = (Integer) CBoxCantidad.getSelectedItem();

            if (nuevaCantidad == null || nuevaCantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (carritoController != null) {
                boolean exito = carritoController.modificarCantidadProductoEnCarrito(codigoProducto, nuevaCantidad);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cantidad modificada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarProductosEnTabla();
                    actualizarTotales();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Controlador no asignado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        BtnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = TblProductos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(CarritoModificarView.this, "Selecciona un producto para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(CarritoModificarView.this, "¿Estás seguro que deseas eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirmacion != JOptionPane.YES_OPTION) return;

                String codigoProducto = TblProductos.getValueAt(filaSeleccionada, 0).toString();

                if (carritoController != null) {
                    boolean exito = carritoController.eliminarProductoDelCarrito(codigoProducto);

                    if (exito) {
                        JOptionPane.showMessageDialog(CarritoModificarView.this, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarProductosEnTabla();
                        actualizarTotales();
                    } else {
                        JOptionPane.showMessageDialog(CarritoModificarView.this, "No se pudo eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(CarritoModificarView.this, "Controlador no asignado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        CBoxCarritos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        Carrito carritoSeleccionado = (Carrito) CBoxCarritos.getSelectedItem();
                        if (carritoSeleccionado != null && carritoController != null) {
                            carritoController.setCarrito(carritoSeleccionado);
                            cargarProductosEnTabla();
                            actualizarTotales();
                        }
                    }
                });

            }



    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public JTable getTblProductos() {
        return TblProductos;
    }

    public JComboBox<Integer> getCBoxCantidad() {
        return CBoxCantidad;
    }

    public JButton getBtnEliminar() {
        return BtnEliminar;
    }

    public JButton getBtnAgregar() {
        return BtnAgregar;
    }

    public JButton getBtnActualizarCantidad() {
        return BtnActualizarCantidad;
    }

    public JTextField getTxtSubtotal() {
        return TxtSubtotal;
    }

    public JTextField getTxtIVA() {
        return TxtIVA;
    }

    public JTextField getTxtTotal() {
        return TxtTotal;
    }

    public JComboBox<Carrito> getCBoxCodigo() {
        return CBoxCodigo;
    }

    public JTextField getTxtCodigo() {
        return TxtCodigo;
    }

    public JComboBox<Carrito> getCBoxCarritos() {
        return CBoxCarritos;
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public void cargarProductosEnTabla() {
        modelo.setRowCount(0);
        for (ItemCarrito item : carritoController.getCarrito().getItems()) {
            Object[] fila = {
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getCantidad() * item.getProducto().getPrecio()
            };
            modelo.addRow(fila);
        }
    }

    public void actualizarTotales() {
        double subtotal = 0.0;

        for (ItemCarrito item : carritoController.getCarrito().getItems()) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }

        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        TxtSubtotal.setText(String.format("%.2f", subtotal));
        TxtIVA.setText(String.format("%.2f", iva));
        TxtTotal.setText(String.format("%.2f", total));
    }
    public void cargarCarritosEnCombo(List<Carrito> carritos) {
        CBoxCarritos.removeAllItems();
        for (Carrito carrito : carritos) {
            CBoxCarritos.addItem(carrito);
        }
    }



}

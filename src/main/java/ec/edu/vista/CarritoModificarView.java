package ec.edu.vista;

import ec.edu.controlador.CarritoController;
import ec.edu.modelo.ItemCarrito;
import ec.edu.modelo.Carrito;
import ec.edu.util.ActualizablePorIdioma;
import ec.edu.util.MensajeInternacionalizacionHandler;
import ec.edu.util.FormateadorUtils;
import java.util.Locale;

import java.awt.*;
import java.util.List;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class CarritoModificarView extends JInternalFrame implements ActualizablePorIdioma {
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
    private JButton BtnCancelar;
    private MensajeInternacionalizacionHandler mensajeHandler;


    private DefaultTableModel modelo;
    private CarritoController carritoController;

    public CarritoModificarView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = Main.mensajeHandler;
        actualizarTextos(Main.mensajeHandler.getBundle());
        setTitle("Modificar Carrito");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setContentPane(PanelPrincipal);


        modelo = new DefaultTableModel();
        Object[] columnas = {"Código", "Nombre", "Precio", "Cantidad", "Subtotal"};
        modelo.setColumnIdentifiers(columnas);
        TblProductos.setModel(modelo);
        CBoxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            CBoxCantidad.addItem(i);
        }
        TblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TblProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = TblProductos.getSelectedRow();
                if (fila >= 0) {
                    CBoxCantidad.setSelectedItem(
                            Integer.parseInt(TblProductos.getValueAt(fila, 3).toString())
                    );
                }
            }
        });


        ImageIcon iconActualizar = new ImageIcon(getClass().getResource("/imagenes/check.png"));
        BtnActualizarCantidad.setIcon(new ImageIcon(iconActualizar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));


        ImageIcon iconEliminar = new ImageIcon(getClass().getResource("/imagenes/cross (1).png"));
        BtnEliminar.setIcon(new ImageIcon(iconEliminar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));


        ImageIcon iconAgregar = new ImageIcon(getClass().getResource("/imagenes/plus.png"));
        BtnAgregar.setIcon(new ImageIcon(iconAgregar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        ImageIcon iconCancelar = new ImageIcon(getClass().getResource("/imagenes/cross (1).png"));

        BtnCancelar.setIcon(new ImageIcon(iconCancelar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));



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

        BtnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        BtnAgregar.addActionListener(e -> {
            String codigo = TxtCodigo.getText().trim();
            int cantidad = Integer.parseInt(CBoxCodigo.getSelectedItem().toString());

            boolean agregado = carritoController.agregarProductoACarrito(codigo, cantidad);
            if (agregado) {
                JOptionPane.showMessageDialog(this, "Producto agregado al carrito.");
                carritoController.cargarProductosCarritoEnModificar();

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
    public JButton getBtnCancelar() {
        return BtnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        BtnCancelar = btnCancelar;
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public void cargarProductosEnTabla() {
        modelo.setRowCount(0);
        Locale locale = mensajeHandler.getLocale();

        for (ItemCarrito item : carritoController.getCarrito().getItems()) {
            Object[] fila = {
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getCantidad() * item.getProducto().getPrecio(), locale)
            };
            modelo.addRow(fila);
        }
    }


    public void actualizarTotales() {
        Carrito carrito = carritoController.getCarrito();
        if (carrito == null) {
            System.out.println("[ERROR] No hay carrito seleccionado.");
            return;
        }

        double subtotal = 0.0;

        for (ItemCarrito item : carritoController.getCarrito().getItems()) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }

        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        Locale locale = mensajeHandler.getLocale();
        TxtSubtotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        TxtIVA.setText(FormateadorUtils.formatearMoneda(iva, locale));
        TxtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
    }


    public void actualizarTextos(ResourceBundle Bundle) {
        LblTitulo.setText(mensajeHandler.get("titulo"));
        LblCarritos.setText(mensajeHandler.get("carritos"));
        LblAgregar.setText(mensajeHandler.get("agregar"));
        LblIVA.setText(mensajeHandler.get("iva"));
        LblTotal.setText(mensajeHandler.get("total"));
        LblSubtotal.setText(mensajeHandler.get("subtotal"));
        LblCodigo.setText(mensajeHandler.get("codigo"));
        LblCantidad.setText(mensajeHandler.get("cantidad"));
        BtnCancelar.setText(mensajeHandler.get("cancelar"));
        BtnEliminar.setText(mensajeHandler.get("eliminar"));
        BtnAgregar.setText(mensajeHandler.get("agregar"));
        BtnActualizarCantidad.setText(mensajeHandler.get("actualizar"));

    }




}
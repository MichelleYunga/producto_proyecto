/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import model.Producto;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USUARIO
 */
public class ModeloTablaProducto extends AbstractTableModel {

    private String[] columnas = {"Nombre", "Precio", "Cantidad"};
    public static List<Producto> filas;
    private Producto productoSelecionado;
    private int indice;

    public ModeloTablaProducto() {
        filas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return filas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        productoSelecionado = filas.get(rowIndex);
        this.indice = rowIndex;
        switch (columnIndex) {
            case 0:
                return productoSelecionado.getNombre();
            case 1:
                return productoSelecionado.getPrecio();
            case 2:
                return productoSelecionado.getCantidad();
            
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            
            default:
                return Object.class;
        }
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    public List<Producto> getFilas() {
        return filas;
    }

    public void setFilas(List<Producto> filas) {
        this.filas = filas;
    }

    public Producto getProductoSelecionado() {
        return productoSelecionado;
    }

    public void setProductoSelecionado(Producto productoSelecionado) {
        this.productoSelecionado = productoSelecionado;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void actualizar(Producto p) {
        setProductoSelecionado(null);
        if (p != null) {
            filas.add(indice, p);
            fireTableDataChanged();
        }
    }

    public void agregar(Producto p) {
        if (p != null) {
            filas.add(p);
            fireTableDataChanged();
        }
    }

    public void eliminar(Producto p) {
        if (p != null) {
            filas.remove(p);
            fireTableDataChanged();
        }

    }
}

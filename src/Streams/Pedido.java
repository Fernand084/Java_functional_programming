package Streams;

import java.util.List;

public class Pedido {
    private final String id;
    private final String cliente;
    private final Estado estado;
    private final List<Producto> productos;

    public Pedido(String id, String cliente, Estado estado, List<Producto> productos) {
        this.id = id;
        this.cliente = cliente;
        this.estado = estado;
        this.productos = productos;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public Estado getEstado() {
        return estado;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double getTotal() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }
}

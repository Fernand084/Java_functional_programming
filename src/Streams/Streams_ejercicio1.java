package Streams;

import java.util.*;

enum Estado {
    PENDIENTE, PROCESANDO, ENVIADO, CANCELADO
}

public class Streams_ejercicio1 {
    public static void main(String[] args) {
        // Datos de prueba — pégalos en main()
        List<Pedido> pedidos = List.of(
                new Pedido("P001", "Ana", Estado.ENVIADO, List.of(
                        new Producto("Laptop", "electronica", 1200.00),
                        new Producto("Mouse", "electronica", 45.00))),
                new Pedido("P002", "Carlos", Estado.PENDIENTE, List.of(
                        new Producto("Camisa", "ropa", 89.00),
                        new Producto("Pantalon", "ropa", 120.00))),
                new Pedido("P003", "Beatriz", Estado.ENVIADO, List.of(
                        new Producto("Monitor", "electronica", 850.00),
                        new Producto("Lampara", "hogar", 75.00),
                        new Producto("Teclado", "electronica", 120.00))),
                new Pedido("P004", "David", Estado.CANCELADO, List.of(
                        new Producto("Sofa", "hogar", 2200.00))),
                new Pedido("P005", "Elena", Estado.ENVIADO, List.of(
                        new Producto("Webcam", "electronica", 199.00),
                        new Producto("Cojin", "hogar", 35.00))),
                new Pedido("P006", "Ana", Estado.PENDIENTE, List.of(
                        new Producto("Auriculares", "electronica", 320.00),
                        new Producto("Funda", "electronica", 29.00))));

        ReporteService service = new ReporteService();
        service.imprimirReporte(pedidos);
    }

}

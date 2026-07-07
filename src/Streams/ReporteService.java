package Streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReporteService {
    // Reto 1 — Reporte de ingresos (filter + mapToDouble)
    // TODO: devuelve el total de ingresos solo de pedidos enviados
    public double calcularIngresos(List<Pedido> pedidos) {
        // una sola cadena de stream
        return pedidos.stream().filter(p -> p.getEstado() == Estado.ENVIADO).mapToDouble(Pedido::getTotal).sum();
    }

    // Reto 2 — Reporte por categoría (flatMap + groupingBy + summingDouble)
    // TODO: devuelve cuánto se vendió en total por categoría
    // solo pedidos ENVIADO
    // Map → {"electronica": 2414.0, "hogar": 110.0}
    public Map<String, Double> ventasPorCategoria(List<Pedido> pedidos) {
        // pista 1: necesitas flatMap para llegar a los Productos
        // pista 2: pero necesitas filtrar por estado ANTES de aplanar
        // pista 3: groupingBy + summingDouble al final
        return pedidos.stream()
                .filter(p -> p.getEstado() == Estado.ENVIADO)
                .flatMap(p -> p.getProductos().stream())
                .collect(Collectors.groupingBy(Producto::getCategoria, Collectors.summingDouble(Producto::getPrecio)));
    }

    // Reto 3 — Top clientes (groupingBy + summingDouble + sorted + limit)
    // TODO: devuelve los N clientes con mayor gasto total
    // considera TODOS los pedidos (cualquier estado)
    // devuelve lista de strings: "Ana: $1594.0"
    public List<String> topClientes(List<Pedido> pedidos, int n) {
        // pista: agrupa por cliente, suma totales,
        // luego necesitas salir del Map para ordenar
        // Map.entrySet().stream() te da un stream de entradas
        return pedidos.stream()
                .collect(Collectors.groupingBy(
                        Pedido::getCliente,
                        Collectors.summingDouble(Pedido::getTotal)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(n)
                .map(e -> e.getKey() + ": $" + e.getValue())
                .collect(Collectors.toList());
        // entrySet().stream() es el patrón clave para ordenar un Map —
        // los Maps no tienen orden inherente, hay que convertirlos a Stream para poder
        // ordenarlos.
    }

    // Reto 4 — Generador de IDs (Stream.iterate + limit)
    // Sin lista, sin datos — solo un stream infinito.
    // TODO: genera N ids con formato "ORD-001", "ORD-002", ...
    // usando Stream.iterate — sin bucle for
    public List<String> generarIds(int cantidad) {
        // pista: itera desde 1, formatea con String.format
        return Stream.iterate(1, n -> n + 1).limit(cantidad).map(n -> String.format("ORD-%03d", n))
                .collect(Collectors.toList());
    }

    // Reto 5 — Reporte completo (todo junto)
    // Combina los cuatro métodos anteriores en un reporte final.

    public void imprimirReporte(List<Pedido> pedidos) {
        System.out.println("=== REPORTE DE VENTAS ===");

        System.out.printf("Ingresos totales (enviados): $%.2f%n",
                calcularIngresos(pedidos));

        System.out.println("\nVentas por categoría:");
        ventasPorCategoria(pedidos)
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("  %-15s $%.2f%n",
                        e.getKey(), e.getValue()));

        System.out.println("\nTop 3 clientes:");
        topClientes(pedidos, 3)
                .forEach(s -> System.out.println("  " + s));

        System.out.println("\nPróximos IDs disponibles:");
        System.out.println("  " + generarIds(3));
    }
}

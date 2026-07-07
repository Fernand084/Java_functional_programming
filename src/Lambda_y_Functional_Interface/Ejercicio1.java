package Lambda_y_Functional_Interface;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

enum Estado {
    PENDIENTE, PROCESANDO, ENVIADO, CANCELADO
}

class Pedido {
    private final String id;
    private final String cliente;
    private double total;
    private Estado estado;

    // constructor, getters, setters
    public Pedido(String id, String cliente, double total, Estado estado) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public double getTotal() {
        return total;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

class Notificacion {
    private final String destinatario;
    private final String mensaje;

    // constructor + getters
    public Notificacion(String destinatario, String msg) {
        this.destinatario = destinatario;
        this.mensaje = msg;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return "[" + destinatario + "] " + mensaje;
    }
}

class ProcesadorPedidos {

    // TODO: reemplaza ??? con el tipo correcto
    public List<Pedido> filtrar(List<Pedido> pedidos, Predicate<Pedido> criterio) {
        // TODO: implementa usando stream — sin for, sin if suelto
        return pedidos = pedidos.stream()
                .filter(criterio)
                .collect(Collectors.toList());
    }

    // TODO: define estas tres reglas como variables del tipo correcto
    Predicate<Pedido> soloMayores1000 = p -> p.getTotal() > 1000/* pedidos cuyo total > 1000 */;
    Predicate<Pedido> soloPendientes = p -> p.getEstado() == Estado.PENDIENTE/* pedidos con estado == PENDIENTE */;
    Predicate<Pedido> urgentes = soloMayores1000.and(soloPendientes)/*
                                                                     * mayores de 1000 Y pendientes — combina las dos
                                                                     * anteriores
                                                                     */;

    // TODO: reemplaza ??? con el tipo correcto
    // Método A — transforma Pedido en Notificacion
    public List<Notificacion> generarNotificaciones(List<Pedido> pedidos, Function<Pedido, Notificacion> estrategia) {
        // TODO: implementa usando stream
        return pedidos.stream()
                .map(estrategia)
                .collect(Collectors.toList());

    }

    // Método B — recibe un total y devuelve el total ajustado
    // PISTA: entrada y salida son del mismo tipo (Double → Double)
    public double aplicarDescuento(double total, UnaryOperator<Double> descuento) {
        // TODO: implementa
        return descuento.apply(total);
    }

    // TODO: define estas dos estrategias con el tipo correcto
    Function<Pedido, Notificacion> notifEmail = p -> new Notificacion(p.getCliente(),
            "Estimado " + p.getCliente() + " su pedido " + p.getId() + " está " + p.getEstado())/*
                                                                                                 * "Estimado {cliente}: su pedido {id} está {estado}"
                                                                                                 */;
    Function<Pedido, Notificacion> notifSMS = p -> new Notificacion(p.getCliente(), "Pedido " + p.getId() + ": "
            + p.getEstado() + " - $" + p.getTotal())/* "Pedido {id}: {estado} - ${total}" */;
}

class Ejercicio1 {

    public static void main(String[] args) {
        List<Pedido> pedidos = List.of(
                new Pedido("P001", "Ana", 1200.00, Estado.PENDIENTE),
                new Pedido("P002", "Carlos", 450.50, Estado.PROCESANDO),
                new Pedido("P003", "Beatriz", 3100.00, Estado.PENDIENTE),
                new Pedido("P004", "David", 890.00, Estado.CANCELADO),
                new Pedido("P005", "Elena", 2200.00, Estado.PENDIENTE));

        ProcesadorPedidos proc = new ProcesadorPedidos();
        List<Pedido> resultado = proc.filtrar(pedidos, proc.urgentes);
        // Debe imprimir: P001-Ana, P003-Beatriz, P005-Elena
        resultado.forEach(p -> System.out.println(p.getId() + " - " + p.getCliente()));

        List<Notificacion> emails = proc.generarNotificaciones(pedidos, proc.notifEmail);

        emails.forEach(e -> System.out.println(e.getMensaje()));
        // → Estimado Ana: su pedido P001 está PENDIENTE

        double totalFinal = proc.aplicarDescuento(1200.0, total -> total * 0.9);
        System.out.println(totalFinal);
        // → 1080.0
    }

}

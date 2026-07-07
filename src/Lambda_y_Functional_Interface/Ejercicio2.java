package Lambda_y_Functional_Interface;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
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

    public List<Notificacion> procesar(
            List<Pedido> pedidos,
            Predicate<Pedido> filtro,
            UnaryOperator<Double> ajusteTotal,
            Consumer<Pedido> auditor,
            Function<Pedido, Notificacion> notificador) {
        return pedidos.stream()
                .filter(filtro)
                .peek(p -> p.setTotal(ajusteTotal.apply(p.getTotal())))
                .peek(auditor::accept)
                .map(notificador)
                .collect(Collectors.toList());

    }

    Predicate<Pedido> soloMayores1000 = p -> p.getTotal() > 1000;
    Predicate<Pedido> soloPendientes = p -> p.getEstado() == Estado.PENDIENTE;
    Predicate<Pedido> urgentes = soloMayores1000.and(soloPendientes);
    Function<Pedido, Notificacion> notifEmail = p -> new Notificacion(p.getCliente(),
            "Estimado " + p.getCliente() + " su pedido " + p.getId() + " está " + p.getEstado());
    Function<Pedido, Notificacion> notifSMS = p -> new Notificacion(p.getCliente(), "Pedido " + p.getId() + ": "
            + p.getEstado() + " - $" + p.getTotal());
}

class Ejercicio2 {

    public static void main(String[] args) {
        List<Pedido> pedidos = List.of(
                new Pedido("P001", "Ana", 1200.00, Estado.PENDIENTE),
                new Pedido("P002", "Carlos", 450.50, Estado.PROCESANDO),
                new Pedido("P003", "Beatriz", 3100.00, Estado.PENDIENTE),
                new Pedido("P004", "David", 890.00, Estado.CANCELADO),
                new Pedido("P005", "Elena", 2200.00, Estado.PENDIENTE));

        ProcesadorPedidos proc = new ProcesadorPedidos();
        Consumer<Pedido> logger = p -> System.out.printf("[AUDIT] %s → %s $%.2f%n",
                p.getId(), p.getCliente(), p.getTotal());

        List<Notificacion> resultado = proc.procesar(
                pedidos,
                proc.urgentes,
                total -> total * 0.85,
                logger,
                proc.notifEmail);
        resultado.forEach(n -> System.out.println(n.getMensaje()));
    }

}

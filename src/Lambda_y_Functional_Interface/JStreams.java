package Lambda_y_Functional_Interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;;

public class JStreams {
        public static void main(String[] args) {
                String[] names = new String[] { "Fer", "Regina", "Nico", "Rosa", "David", "Vale", "Jesus", "Erick",
                                "Leo",
                                "Alex", "Ana" };

                // refactorizar este código para usar streams
                /*
                 * List<String> result = new ArrayList<>();
                 * for (String name : names) {
                 * if (name.length() > 3) {
                 * result.add(name.toUpperCase());
                 * }
                 * }
                 */

                List<String> result = Arrays.asList(names).stream()
                                .filter(n -> n.length() > 3)
                                .map(String::toUpperCase)
                                .toList();

                // input List<Order> orders;
                // Cada Oder tiene: double amount, boolean active, Customer customer,
                // Customer.getCountry()
                // Objetivo: Obtener el total (double) de ordenes activas de clientes que viven
                // en Mexico.
                // restricciones: usar stream, filets mapToDouble, sin variables externas

                double total = orders.stream()
                                .filter(o -> o.active)
                                .filter(o -> o.Customer.getCountry() == "Mexico")
                                .mapToDouble(o -> o.amount)
                                .sum();

                // correcto
                double total = orders.stream()
                                .filter(Order::isActive)
                                .filter(o -> o.getCustomer() != null)
                                .filter(o -> "Mexico".equals(o.getCustomer().getCountry()))
                                .mapToDouble(Order::getAmount)
                                .sum();

                // Objetivo: obtener un map<String, Double> done key: pais, value: total de
                // ordenes activas por pais
                // restricciones: usar groupingBy, summingDouble, solo órdenes activas
                Map<String, Double> result = ordes.stream()
                                .filter(Order::isActive)
                                .summingDouble(Order::getAmount)
                                .collect(groupingBy(o -> o.getCustomer().getCountry()));

                // objetivo: obtener el top 3 de paises con mayor total de ordenes activas
                // output esperado: List<String> / ["USA", "Mexico", "Canada"]
                // requisitos reutiliza Map<String, Double>; ordena por valor descendente; toma
                // los primeros 3; usa stream() sobre el entrySet()
                List<String> top = result.entrySet().stream()
                                .collect(entry -> entry.get()).limit(3).sorted();

                // correcto:
                List<String> top = result.entrySet().stream()
                        .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                        .limit(3)
                        .map(Map.Entry::getKey)
                        .toList();
                // alternativa más limpia
                List<String> top = result.entrySet().stream()
                        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                        .limit(3)
                        .map(Map.Entry::getKey)
                        .toList();
                
                //////////////////////////////////////////////////////
                
                // imput List<Person> people;
                // objetivo: obtener el primer email de una persona mayor de 18, si existe
                // output: Optional<String>
                // Requisitos: usar stream, findFirst, map, if
                Optional<String> email = people.stream()
                        .filter(p->p.getAge()>18)
                        .findFirst(Person::getEmail())
                        .map();
                        if(email.isPresent()){
                                System.out.println("true");
                        }

                

        }
}

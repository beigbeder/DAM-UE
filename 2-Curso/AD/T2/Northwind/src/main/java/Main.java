
import com.google.gson.Gson;
import controller.Almacen;
import model.Producto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Almacen almacen = new Almacen();

        try (Scanner sc = new Scanner(System.in)) {

            int opcion = -1;

            do {
                System.out.println("\n========== NORTHWIND ==========");
                System.out.println("1. Mostrar todos los productos");
                System.out.println("2. Mostrar productos inferiores a 600 €");
                System.out.println("3. Mostrar productos favoritos");
                System.out.println("4. Insertar productos favoritos");
                System.out.println("5. Insertar empleados");
                System.out.println("6. Insertar pedidos");
                System.out.println("7. Importar productos desde DummyJSON");
                System.out.println("0. Salir");
                System.out.println("===============================");
                System.out.print("Selecciona una opción: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Introduce una opción numérica válida.");
                    sc.nextLine();
                    continue;
                }

                opcion = sc.nextInt();

                switch (opcion) {

                    case 1:
                        almacen.mostrarProductos();
                        break;

                    case 2:
                        almacen.mostrarProductosBaratos();
                        break;

                    case 3:
                        almacen.mostrarProductosFavoritos();
                        break;

                    case 4:
                        almacen.insertarProductosFavoritos();
                        break;

                    case 5:
                        almacen.insertarEmpleados();
                        break;

                    case 6:
                        almacen.insertarPedidos();
                        break;

                    case 7:
                        System.out.print("La importación puede duplicar productos o generar errores de clave primaria. ¿Continuar? (s/n): ");
                        sc.nextLine();
                        String respuesta = sc.nextLine();

                        if (respuesta.equalsIgnoreCase("s")) {

                            HttpClient client = HttpClient.newHttpClient();

                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create("https://dummyjson.com/products?limit=0"))
                                    .GET()
                                    .build();

                            try {
                                Gson gson = new Gson();

                                HttpResponse<String> response = client.send(
                                        request, HttpResponse.BodyHandlers.ofString());

                                String responseJSON = response.body();

                                JSONObject jsonObject = new JSONObject(responseJSON);
                                JSONArray jsonArray = jsonObject.getJSONArray("products");

                                System.out.println("Productos recibidos: " + jsonArray.length());

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectProduct = jsonArray.getJSONObject(i);

                                    Producto producto = gson.fromJson(
                                            jsonObjectProduct.toString(), Producto.class);

                                    almacen.insertarProducto(producto);
                                }

                                System.out.println("Proceso de importación finalizado.");

                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }

                        } else {
                            System.out.println("Importación cancelada.");
                        }
                        break;

                    case 0:
                        System.out.println("Saliendo de Northwind...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

            } while (opcion != 0);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

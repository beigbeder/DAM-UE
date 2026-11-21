package controller;

import database.DBConnection;
import model.Producto;

import java.sql.*;

public class Almacen {

    private Connection connection;

    public void insertarProducto(Producto producto) {
        connection = DBConnection.getConnection();

        String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)",
                "productos", "id", "nombre", "descripcion", "cantidad", "precio");

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, producto.getId());
            preparedStatement.setString(2, producto.getNombre());
            preparedStatement.setString(3, producto.getDescripcion());
            preparedStatement.setInt(4, producto.getCantidad());
            preparedStatement.setDouble(5, producto.getPrecio());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error de SQL");
            System.out.println(e.getMessage());
        }
    }

    public void mostrarProductos() {

        connection = DBConnection.getConnection();
        String query = "SELECT * FROM productos";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Descripción: " + descripcion);
                System.out.println("Cantidad: " + cantidad);
                System.out.println("Precio: " + precio + " €");
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL");
            System.out.println(e.getMessage());
        }
    }

    public void mostrarProductosBaratos() {

        connection = DBConnection.getConnection();

        String query = "SELECT * FROM productos WHERE precio < ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, 600);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    int cantidad = resultSet.getInt("cantidad");
                    double precio = resultSet.getDouble("precio");

                    System.out.println("ID: " + id);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Descripción: " + descripcion);
                    System.out.println("Cantidad: " + cantidad);
                    System.out.println("Precio: " + precio + " €");
                    System.out.println("-------------------------");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL");
            System.out.println(e.getMessage());
        }
    }

    public void insertarProductosFavoritos() {

        connection = DBConnection.getConnection();

        String querySelect = "SELECT id FROM productos WHERE precio > ?";
        String queryInsert = "INSERT INTO productos_fav (id_producto) VALUES (?)";

        try (PreparedStatement psSelect = connection.prepareStatement(querySelect);
             PreparedStatement psInsert = connection.prepareStatement(queryInsert)) {

            psSelect.setDouble(1, 1000);

            try (ResultSet resultSet = psSelect.executeQuery()) {

                while (resultSet.next()) {

                    int idProducto = resultSet.getInt("id");

                    psInsert.setInt(1, idProducto);
                    psInsert.executeUpdate();

                    System.out.println("Producto añadido a favoritos: " + idProducto);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    public void mostrarProductosFavoritos() {

        connection = DBConnection.getConnection();

        String query = "SELECT p.* FROM productos_fav pf " +
                "INNER JOIN productos p ON pf.id_producto = p.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Descripción: " + descripcion);
                System.out.println("Cantidad: " + cantidad);
                System.out.println("Precio: " + precio + " €");
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    public void insertarEmpleados() {

        connection = DBConnection.getConnection();

        String query = "INSERT INTO empleados (nombre, correo) VALUES " +
                "('Borja', 'borja@gmail.com'), " +
                "('Santi', 'santi@gmail.com'), " +
                "('Carmen', 'carmen@gmail.com')";

        try (Statement statement = connection.createStatement()) {

            int filas = statement.executeUpdate(query);
            System.out.println("Empleados insertados: " + filas);

        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }

    public void insertarPedidos() {

        connection = DBConnection.getConnection();

        String query = "INSERT INTO pedidos (id_producto, id_empleado, cantidad) VALUES " +
                "(1, 1, 2), " +
                "(2, 2, 1), " +
                "(3, 3, 5)";

        try (Statement statement = connection.createStatement()) {

            int filas = statement.executeUpdate(query);
            System.out.println("Pedidos insertados: " + filas);

        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
    }
}

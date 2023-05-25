/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package databaserestaurante;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class DatabaseRestaurante {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurante_pueba";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Osc@r801223";

    private Connection connection;

    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Conectado a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Desconectado de la base de datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al desconectar de la base de datos");
        }
    }

    public void insertarCliente(String nombre, String apellido, String direccion, String telefono, String email) {
        try {
            String query = "INSERT INTO clientes (Nombre, Apellido, Direccion, Telefono, Email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nombre);
                statement.setString(2, apellido);
                statement.setString(3, direccion);
                statement.setString(4, telefono);
                statement.setString(5, email);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cliente insertado correctamente");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente");
        }
    }

    public void consultarClientes() {
        try {
            String query = "SELECT * FROM clientes";
            try (PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("idCliente");
                    String nombre = resultSet.getString("Nombre");
                    String apellido = resultSet.getString("Apellido");
                    String direccion = resultSet.getString("Direccion");
                    String telefono = resultSet.getString("Telefono");
                    String email = resultSet.getString("Email");

                    System.out.println("ID: " + id);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Apellido: " + apellido);
                    System.out.println("Dirección: " + direccion);
                    System.out.println("Teléfono: " + telefono);
                    System.out.println("Email: " + email);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar clientes");
        }
    }

    public void actualizarCliente(int idCliente, String nuevoTelefono) {
        try {
            String query = "UPDATE clientes SET Telefono = ? WHERE idCliente = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nuevoTelefono);
                statement.setInt(2, idCliente);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cliente actualizado correctamente");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente");
        }
    }

    public void eliminarCliente(int idCliente) {
        try {
            String query = "DELETE FROM clientes WHERE idCliente = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idCliente);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cliente eliminado correctamente");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente");
        }
    }

    public static void main(String[] args) {
        DatabaseRestaurante databaseConnection = new DatabaseRestaurante();
        databaseConnection.connect();

        // uso
        databaseConnection.insertarCliente("Oscar", "Galindo", "Calle 20 N102-30", "3212505606", "funcionmain@gmail.com");
        databaseConnection.consultarClientes();
        databaseConnection.actualizarCliente(3, "3212505606");
        databaseConnection.eliminarCliente(27);

        databaseConnection.disconnect();
    }
}
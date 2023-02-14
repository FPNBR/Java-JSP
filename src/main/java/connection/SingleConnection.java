package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
    private static final String banco = "jdbc:postgresql://localhost:5432/jsp?autoReconnect=true";
    private static final String usuario = "postgres";
    private static final String senha = "postgres";
    private static Connection connection = null;

    public static Connection getConnection() {
        return connection;
    }

    static {
        conectar();
    }

    public SingleConnection() { // Quando tiver uma instância vai conectar
        conectar();
    }

    private static void  conectar() {

        try {
            if (connection == null) {
                Class.forName("org.postgresql.Driver"); // Carrega o driver de conexão do banco
                connection = DriverManager.getConnection(banco, usuario, senha);
                connection.setAutoCommit(false); // Para não efetuar alterações no banco sem nosso comando
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar qualquer erro no momento de conectar
        }
    }
}

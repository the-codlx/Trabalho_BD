package Conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String url = "jdbc:mysql://localhost:3306/bdvendas";
    private static final String user = "root";
    private static final String password = "root123@#";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}

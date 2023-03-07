package dao;

import connection.SingleConnection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOVersionadorBanco implements Serializable {
    private Connection connection;

    public DAOVersionadorBanco() {
        connection = SingleConnection.getConnection();
    }

    public void salvarArquivoSqlExecutado(String nomeArquivo) throws SQLException {
        try {
            String sql = "INSERT INTO versionador_banco(arquivo_sql) VALUES (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomeArquivo);
            preparedStatement.execute();

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Falha ao executar o versionador: " + e.getMessage());
        }
    }

    public boolean arquivoSqlExecutado(String nomeArquivo) throws SQLException {
        try {
            String sql = "SELECT count(1) > 0 as executado FROM versionador_banco WHERE arquivo_sql = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomeArquivo);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getBoolean("executado");

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Falha ao executar o versionador: " + e.getMessage());
        }
    }
}

package dao;

import connection.SingleConnection;
import model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOLoginRepository {
    private Connection connection;

    public DAOLoginRepository() {
        connection = SingleConnection.getConnection();
    }

    public boolean validarAutenticacao(ModelLogin modelLogin) throws SQLException {
        String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, modelLogin.getLogin());
        preparedStatement.setString(2, modelLogin.getSenha());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return true; // Usuário autenticado
        }
        return false; // Usuário mão autenticado
    }
}

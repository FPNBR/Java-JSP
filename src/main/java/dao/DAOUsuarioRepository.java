package dao;

import connection.SingleConnection;
import model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUsuarioRepository {
    private Connection connection;

    public DAOUsuarioRepository() {
        connection = SingleConnection.getConnection();
    }

    public ModelLogin salvarUsuario(ModelLogin usuario) throws SQLException {

        try {
            String sql = "insert into model_login (login, nome, email, senha) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getLogin());
            preparedStatement.setString(2, usuario.getNome());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getSenha());
            preparedStatement.executeUpdate();
            connection.commit();

            return this.consultarUsuario(usuario.getLogin());

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public ModelLogin consultarUsuario(String login) throws SQLException {
        try {
            ModelLogin modelLogin = new ModelLogin();
            String sql = "select * from model_login where upper(login) = upper(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setSenha(resultSet.getString("senha"));
            }
            return modelLogin;
        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }
}

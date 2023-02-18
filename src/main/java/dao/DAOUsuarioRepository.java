package dao;

import connection.SingleConnection;
import model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOUsuarioRepository {
    private Connection connection;

    public DAOUsuarioRepository() {
        connection = SingleConnection.getConnection();
    }

    public void salvarUsuario(ModelLogin usuario) throws SQLException {

        try {
            String sql = "insert into model_login (login, nome, email, senha) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getLogin());
            preparedStatement.setString(2, usuario.getNome());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getSenha());
            preparedStatement.executeUpdate();
            connection.commit();

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}

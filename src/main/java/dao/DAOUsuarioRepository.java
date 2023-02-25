package dao;

import connection.SingleConnection;
import model.ModelLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarioRepository {
    private Connection connection;

    public DAOUsuarioRepository() {
        connection = SingleConnection.getConnection();
    }

    public ModelLogin salvarUsuario(ModelLogin usuario, Long usuarioLogado) throws SQLException {

        try {
            if (usuario.idExiste()) { // Grava um novo usuário se o boolean for verdadeiro
                String sql = "INSERT INTO model_login (login, nome, email, senha, usuario_id, perfil) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, usuario.getLogin());
                preparedStatement.setString(2, usuario.getNome());
                preparedStatement.setString(3, usuario.getEmail());
                preparedStatement.setString(4, usuario.getSenha());
                preparedStatement.setLong(5, usuarioLogado);
                preparedStatement.setString(6, usuario.getPerfil());
                preparedStatement.execute();
                connection.commit();

            } else { // Atualiza o usuário se o boolean for false
                String sql = "UPDATE model_login SET login=?, nome=?, email=?, senha=?, perfil=? WHERE id = " + usuario.getId() + ";";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, usuario.getLogin());
                preparedStatement.setString(2, usuario.getNome());
                preparedStatement.setString(3, usuario.getEmail());
                preparedStatement.setString(4, usuario.getSenha());
                preparedStatement.setString(5, usuario.getPerfil());
                preparedStatement.executeUpdate();
                connection.commit();
            }
            return this.consultarUsuario(usuario.getLogin(), usuarioLogado);

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao cadastrar/atualizar usuario: " + e.getMessage());
        }
    }

    public List<ModelLogin> consultarUsuarioView(Long usuarioLogado) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE usuario_admin IS FALSE and usuario_id =" + usuarioLogado;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { // Percorrer as linhas de resultado do SQL
                ModelLogin modelLogin = new ModelLogin();
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setSenha(resultSet.getString("senha"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLoginList.add(modelLogin);
            }
            return modelLoginList;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public List<ModelLogin> consultarUsuarioList(String nome, Long usuarioLogado) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE UPPER(nome) LIKE UPPER(?) and usuario_admin IS FALSE and usuario_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + nome + "%");
            preparedStatement.setLong(2, usuarioLogado);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { // Percorrer as linhas de resultado do SQL
                ModelLogin modelLogin = new ModelLogin();
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLoginList.add(modelLogin);
            }
            return modelLoginList;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public ModelLogin consultarUsuarioLogado(String login) throws SQLException {
        try {
            ModelLogin modelLogin = new ModelLogin();
            String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setUsuario_admin(resultSet.getBoolean("usuario_admin"));
                modelLogin.setPerfil((resultSet.getString("perfil")));
            }
            return modelLogin;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public ModelLogin consultarUsuario(String login) throws SQLException {
        try {
            ModelLogin modelLogin = new ModelLogin();
            String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"') and usuario_admin IS FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
            }
            return modelLogin;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public ModelLogin consultarUsuario(String login, Long usuarioLogado) throws SQLException {
        try {
            ModelLogin modelLogin = new ModelLogin();
            String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"') and usuario_admin IS FALSE and usuario_id =" + usuarioLogado;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
            }
            return modelLogin;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public ModelLogin consultarUsuarioPorId(String id, Long usuarioLogado) throws SQLException {
        try {
            ModelLogin modelLogin = new ModelLogin();
            String sql = "SELECT * FROM model_login WHERE id = ? and usuario_admin IS FALSE and usuario_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, Long.parseLong(id));
            preparedStatement.setLong(2, usuarioLogado);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setSenha(resultSet.getString("senha"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
            }
            return modelLogin;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public boolean validarLogin(String login) throws SQLException {
        try {
            String sql = "SELECT count(1) > 0 AS existe FROM model_login WHERE UPPER(login) = UPPER('" + login + "');";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getBoolean("existe");

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar login do usuário: " + e.getMessage());
        }
    }

    public void deletarUsuario(String idUsuario) throws SQLException {
        try {
            String sql = "DELETE FROM model_login WHERE id = ? and usuario_admin IS FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, Long.parseLong(idUsuario));
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}

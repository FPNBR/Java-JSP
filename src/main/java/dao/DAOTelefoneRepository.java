package dao;

import connection.SingleConnection;
import model.ModelTelefone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOTelefoneRepository {
    private Connection connection;
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public DAOTelefoneRepository() {
        connection = SingleConnection.getConnection();
    }

    public boolean telefoneExiste(String telefone, Long idUsuario) throws SQLException {
        try {
            String sql = "SELECT COUNT(1) > 0 AS existe FROM telefone WHERE usuario_pai_id =? AND numero =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idUsuario);
            preparedStatement.setString(2, telefone);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("existe");

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao carregar telefones: " + e.getMessage());
        }
    }

    public void salvarTelefone(ModelTelefone modelTelefone) throws SQLException {
        try {
            String sql = "INSERT INTO telefone(numero, usuario_pai_id, usuario_cadastro_id) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, modelTelefone.getNumero());
            preparedStatement.setLong(2, modelTelefone.getUsuarioPaiId().getId());
            preparedStatement.setLong(3, modelTelefone.getUsuarioCadastroId().getId());
            preparedStatement.execute();
            connection.commit();

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao cadastrar telefone: " + e.getMessage());
        }
    }

    public void deletarTelefone(Long id) throws SQLException {
        try {
            String sql = "DELETE FROM telefone WHERE id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao deletar telefone: " + e.getMessage());
        }
    }

    public List<ModelTelefone> gerarTabelaTelefone(Long idUsuarioPai) throws SQLException {
        try {
            List<ModelTelefone> modelTelefones = new ArrayList<>();
            String sql = "SELECT * FROM telefone WHERE usuario_pai_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idUsuarioPai);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ModelTelefone modelTelefone = new ModelTelefone();
                modelTelefone.setId(resultSet.getLong("id"));
                modelTelefone.setNumero(resultSet.getString("numero"));
                modelTelefone.setUsuarioCadastroId(daoUsuarioRepository.consultarUsuarioPorId(resultSet.getLong("usuario_cadastro_id")));
                modelTelefone.setUsuarioPaiId(daoUsuarioRepository.consultarUsuarioPorId(resultSet.getLong("usuario_pai_id")));

                modelTelefones.add(modelTelefone);
            }
            return modelTelefones;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao carregar lista de telefones: " + e.getMessage());
        }
    }
}

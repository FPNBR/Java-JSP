package dao;

import DTO.GraficoSalarioDTO;
import connection.SingleConnection;
import model.ModelLogin;
import model.ModelTelefone;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarioRepository {
    private Connection connection;

    public DAOUsuarioRepository() {
        connection = SingleConnection.getConnection();
    }

    public GraficoSalarioDTO gerarGraficoMediaSalario(Long usuarioLogado) throws SQLException {
        try {
            String sql = "SELECT AVG(renda_mensal) AS media_salarial, perfil FROM model_login WHERE usuario_id = ? AND perfil != 'admin' GROUP BY perfil";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, usuarioLogado);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> perfis = new ArrayList<>();
            List<Double> salarios = new ArrayList<>();

            GraficoSalarioDTO graficoSalarioDTO = new GraficoSalarioDTO();

            while (resultSet.next()) {
                Double media_salarial = resultSet.getDouble("media_salarial");
                String perfil = resultSet.getString("perfil");
                perfis.add(perfil);
                salarios.add(media_salarial);
            }
            graficoSalarioDTO.setPerfis(perfis);
            graficoSalarioDTO.setSalarios(salarios);

            return graficoSalarioDTO;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao gerar média salarial dos usuarios: " + e.getMessage());
        }
    }

    public ModelLogin salvarUsuario(ModelLogin usuario, Long usuarioLogado) throws SQLException {

        try {
            if (usuario.idExiste()) { // Grava um novo usuário se o boolean for verdadeiro
                String sql = "INSERT INTO model_login (login, nome, email, senha, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero_casa, data_nascimento, renda_mensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, usuario.getLogin());
                preparedStatement.setString(2, usuario.getNome());
                preparedStatement.setString(3, usuario.getEmail());
                preparedStatement.setString(4, usuario.getSenha());
                preparedStatement.setLong(5, usuarioLogado);
                preparedStatement.setString(6, usuario.getPerfil());
                preparedStatement.setString(7, usuario.getSexo());
                preparedStatement.setString(8, usuario.getCep());
                preparedStatement.setString(9, usuario.getLogradouro());
                preparedStatement.setString(10, usuario.getBairro());
                preparedStatement.setString(11, usuario.getLocalidade());
                preparedStatement.setString(12, usuario.getUf());
                preparedStatement.setString(13, usuario.getNumeroCasa());
                preparedStatement.setDate(14, usuario.getDataNascimento());
                preparedStatement.setDouble(15, usuario.getRendaMensal());
                preparedStatement.execute();
                connection.commit();

                if (usuario.getFotoUsuario() != null && !usuario.getFotoUsuario().isEmpty()) {
                    sql = "UPDATE model_login SET foto_usuario =?, extensao_foto_usuario=? WHERE login =?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, usuario.getFotoUsuario());
                    preparedStatement.setString(2, usuario.getExtensaoFotoUsuario());
                    preparedStatement.setString(3, usuario.getLogin());
                    preparedStatement.execute();
                    connection.commit();
                }

            } else { // Atualiza o usuário se o boolean for false
                String sql = "UPDATE model_login SET login=?, nome=?, email=?, senha=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero_casa=?, data_nascimento=?, renda_mensal=? WHERE id = " + usuario.getId() + ";";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, usuario.getLogin());
                preparedStatement.setString(2, usuario.getNome());
                preparedStatement.setString(3, usuario.getEmail());
                preparedStatement.setString(4, usuario.getSenha());
                preparedStatement.setString(5, usuario.getPerfil());
                preparedStatement.setString(6, usuario.getSexo());
                preparedStatement.setString(7, usuario.getCep());
                preparedStatement.setString(8, usuario.getLogradouro());
                preparedStatement.setString(9, usuario.getBairro());
                preparedStatement.setString(10, usuario.getLocalidade());
                preparedStatement.setString(11, usuario.getUf());
                preparedStatement.setString(12, usuario.getNumeroCasa());
                preparedStatement.setDate(13, usuario.getDataNascimento());
                preparedStatement.setDouble(14, usuario.getRendaMensal());
                preparedStatement.executeUpdate();
                connection.commit();

                if (usuario.getFotoUsuario() != null && !usuario.getFotoUsuario().isEmpty()) {
                    sql = "UPDATE model_login SET foto_usuario =?, extensao_foto_usuario=? WHERE id =?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, usuario.getFotoUsuario());
                    preparedStatement.setString(2, usuario.getExtensaoFotoUsuario());
                    preparedStatement.setLong(3, usuario.getId());
                    preparedStatement.execute();
                    connection.commit();
                }
            }
            return this.consultarUsuario(usuario.getLogin(), usuarioLogado);

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao cadastrar/atualizar usuario: " + e.getMessage());
        }
    }

    public int totalPaginas(Long usuarioLogado) throws SQLException {
        try {
            String sql = "SELECT count(1) AS TOTAL FROM model_login WHERE usuario_admin IS FALSE and usuario_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, usuarioLogado);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Double totalUsuarios = resultSet.getDouble("total");
            Double limitePaginas = 5.0;
            Double totalPaginas = Math.ceil(totalUsuarios / limitePaginas);
            return totalPaginas.intValue();

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro na paginação para consultar usuários: " + e.getMessage());
        }
    }

    public List<ModelLogin> gerarTabelaUsuario(Long usuarioLogado) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE usuario_admin IS FALSE and usuario_id = " + usuarioLogado + " LIMIT 5";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { // Percorrer as linhas de resultado do SQL
                ModelLogin modelLogin = new ModelLogin();
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLoginList.add(modelLogin);
            }
            return modelLoginList;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public List<ModelLogin> gerarTabelaUsuario(Long usuarioLogado, Integer offset) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE usuario_admin IS FALSE and usuario_id = ? ORDER BY nome LIMIT 5 OFFSET ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, usuarioLogado);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) { // Percorrer as linhas de resultado do SQL
                ModelLogin modelLogin = new ModelLogin();
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLoginList.add(modelLogin);
            }
            return modelLoginList;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public List<ModelLogin> gerarRelatorioUsuario(Long usuarioLogado) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE usuario_admin IS FALSE and usuario_id = " + usuarioLogado;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { // Percorrer as linhas de resultado do SQL
                ModelLogin modelLogin = new ModelLogin();
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLogin.setModelTelefones(this.gerarTabelaTelefone(modelLogin.getId()));
                modelLoginList.add(modelLogin);
            }
            return modelLoginList;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public List<ModelLogin> gerarRelatorioUsuario(Long usuarioLogado, String dataInicial, String dataFinal) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE usuario_admin IS FALSE and usuario_id = " + usuarioLogado + " AND data_nascimento >= ? AND data_nascimento <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
            preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { // Percorrer as linhas de resultado do SQL
                ModelLogin modelLogin = new ModelLogin();
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLogin.setModelTelefones(this.gerarTabelaTelefone(modelLogin.getId()));
                modelLoginList.add(modelLogin);
            }
            return modelLoginList;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public int buscarUsuarioNomeAjaxPaginada(String nome, Long usuarioLogado) throws SQLException {
        try {
            String sql = "SELECT COUNT(1) AS TOTAL FROM model_login WHERE UPPER(nome) LIKE UPPER(?) AND usuario_admin IS FALSE AND usuario_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + nome + "%");
            preparedStatement.setLong(2, usuarioLogado);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Double totalUsuarios = resultSet.getDouble("total");
            Double limitePaginas = 5.0;
            Double totalPaginas = Math.ceil(totalUsuarios / limitePaginas);
            return totalPaginas.intValue();


        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public List<ModelLogin> buscarUsuarioNomeAjax(String nome, Long usuarioLogado) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE UPPER(nome) LIKE UPPER(?) AND usuario_admin IS FALSE AND usuario_id = ? LIMIT 5";
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
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLoginList.add(modelLogin);
            }
            return modelLoginList;

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public List<ModelLogin> buscarUsuarioNomeAjax(String nome, Long usuarioLogado, Integer offset) throws SQLException {
        try {
            List<ModelLogin> modelLoginList = new ArrayList<>();
            String sql = "SELECT * FROM model_login WHERE UPPER(nome) LIKE UPPER(?) AND usuario_admin IS FALSE AND usuario_id = ? OFFSET "+offset+" LIMIT 5";
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
                modelLogin.setSexo(resultSet.getString("sexo"));
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
                modelLogin.setSenha(resultSet.getString("senha"));
                modelLogin.setUsuarioAdmin(resultSet.getBoolean("usuario_admin"));
                modelLogin.setPerfil((resultSet.getString("perfil")));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setFotoUsuario(resultSet.getString("foto_usuario"));
                modelLogin.setCep(resultSet.getString("cep"));
                modelLogin.setLogradouro(resultSet.getString("logradouro"));
                modelLogin.setBairro(resultSet.getString("bairro"));
                modelLogin.setLocalidade(resultSet.getString("localidade"));
                modelLogin.setUf(resultSet.getString("uf"));
                modelLogin.setNumeroCasa(resultSet.getString("numero_casa"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLogin.setRendaMensal(resultSet.getDouble("renda_mensal"));
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
            String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"') AND usuario_admin IS FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setSenha(resultSet.getString("senha"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setFotoUsuario(resultSet.getString("foto_usuario"));
                modelLogin.setCep(resultSet.getString("cep"));
                modelLogin.setLogradouro(resultSet.getString("logradouro"));
                modelLogin.setBairro(resultSet.getString("bairro"));
                modelLogin.setLocalidade(resultSet.getString("localidade"));
                modelLogin.setUf(resultSet.getString("uf"));
                modelLogin.setNumeroCasa(resultSet.getString("numero_casa"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLogin.setRendaMensal(resultSet.getDouble("renda_mensal"));
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
            String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER('"+login+"') AND usuario_admin IS FALSE AND usuario_id =" + usuarioLogado;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setSenha(resultSet.getString("senha"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setFotoUsuario(resultSet.getString("foto_usuario"));
                modelLogin.setCep(resultSet.getString("cep"));
                modelLogin.setLogradouro(resultSet.getString("logradouro"));
                modelLogin.setBairro(resultSet.getString("bairro"));
                modelLogin.setLocalidade(resultSet.getString("localidade"));
                modelLogin.setUf(resultSet.getString("uf"));
                modelLogin.setNumeroCasa(resultSet.getString("numero_casa"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLogin.setRendaMensal(resultSet.getDouble("renda_mensal"));
            }
            return modelLogin;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            throw new SQLException("Erro ao consultar usuário: " + e.getMessage());
        }
    }

    public ModelLogin consultarUsuarioPorId(Long id) throws SQLException {
        try {
            ModelLogin modelLogin = new ModelLogin();
            String sql = "SELECT * FROM model_login WHERE id = ? AND usuario_admin IS FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                modelLogin.setId(resultSet.getLong("id"));
                modelLogin.setLogin(resultSet.getString("login"));
                modelLogin.setNome(resultSet.getString("nome"));
                modelLogin.setEmail(resultSet.getString("email"));
                modelLogin.setSenha(resultSet.getString("senha"));
                modelLogin.setPerfil(resultSet.getString("perfil"));
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setFotoUsuario(resultSet.getString("foto_usuario"));
                modelLogin.setExtensaoFotoUsuario(resultSet.getString("extensao_foto_usuario"));
                modelLogin.setCep(resultSet.getString("cep"));
                modelLogin.setLogradouro(resultSet.getString("logradouro"));
                modelLogin.setBairro(resultSet.getString("bairro"));
                modelLogin.setLocalidade(resultSet.getString("localidade"));
                modelLogin.setUf(resultSet.getString("uf"));
                modelLogin.setNumeroCasa(resultSet.getString("numero_casa"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLogin.setRendaMensal(resultSet.getDouble("renda_mensal"));
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
            String sql = "SELECT * FROM model_login WHERE id = ? AND usuario_admin IS FALSE AND usuario_id = ?";
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
                modelLogin.setSexo(resultSet.getString("sexo"));
                modelLogin.setFotoUsuario(resultSet.getString("foto_usuario"));
                modelLogin.setExtensaoFotoUsuario(resultSet.getString("extensao_foto_usuario"));
                modelLogin.setCep(resultSet.getString("cep"));
                modelLogin.setLogradouro(resultSet.getString("logradouro"));
                modelLogin.setBairro(resultSet.getString("bairro"));
                modelLogin.setLocalidade(resultSet.getString("localidade"));
                modelLogin.setUf(resultSet.getString("uf"));
                modelLogin.setNumeroCasa(resultSet.getString("numero_casa"));
                modelLogin.setDataNascimento(resultSet.getDate("data_nascimento"));
                modelLogin.setRendaMensal(resultSet.getDouble("renda_mensal"));
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
            String sql = "DELETE FROM model_login WHERE id = ? AND usuario_admin IS FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, Long.parseLong(idUsuario));
            preparedStatement.executeUpdate();
            connection.commit();

        }catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
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
                modelTelefone.setUsuarioCadastroId(this.consultarUsuarioPorId(resultSet.getLong("usuario_cadastro_id")));
                modelTelefone.setUsuarioPaiId(this.consultarUsuarioPorId(resultSet.getLong("usuario_pai_id")));

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

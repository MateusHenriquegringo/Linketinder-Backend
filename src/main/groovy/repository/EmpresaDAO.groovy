package repository

import DB.ConnectionFactory
import DB.DBTypes
import model.Empresa
import model.builder.EmpresaBuilder
import model.builder.IEmpresaBuilder
import model.builder.director.EmpresaDirector

import java.sql.*

class EmpresaDAO implements ModelsCRUD<Empresa, Long> {

    private Connection connection = ConnectionFactory.getConnection(DBTypes.POSTGRES)

    private EmpresaDirector director = new EmpresaDirector()
    private IEmpresaBuilder builder = new EmpresaBuilder()

    EmpresaDAO(Connection connection) {
        this.connection = connection
    }

    EmpresaDAO() {
    }

    @Override
    long create(Empresa empresa) {
        String command = "INSERT INTO empresa (empresa_name, description, email, cnpj, cep, country, password)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"

        try (PreparedStatement pstm = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, empresa.getEmpresa_name())
            pstm.setString(2, empresa.getDescription())
            pstm.setString(3, empresa.getEmail())
            pstm.setString(4, empresa.getCNPJ())
            pstm.setString(5, empresa.getCEP())
            pstm.setString(6, empresa.getCountry())
            pstm.setString(7, empresa.getPassword())

            pstm.executeUpdate()

            ResultSet keys = pstm.getGeneratedKeys()
            return keys.next() ? keys.getLong(1) : -1
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel criar empresa " + e.getMessage())
        }

    }

    @Override
    void update(Empresa empresaUpdate, Long id) {
        String command = "UPDATE empresa SET empresa_name=?, description=?, email=?, cnpj=?, cep=?, country=?, password=? WHERE id=?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, empresaUpdate.getEmpresa_name())
            pstmt.setString(2, empresaUpdate.getDescription())
            pstmt.setString(3, empresaUpdate.getEmail())
            pstmt.setString(4, empresaUpdate.getCNPJ())
            pstmt.setString(5, empresaUpdate.getCEP())
            pstmt.setString(6, empresaUpdate.getCountry())
            pstmt.setString(7, empresaUpdate.getPassword())
            pstmt.setLong(8, id)

            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar " + e.getMessage())
        }
    }

    @Override
    void delete(Long id) {
        String command = "DELETE FROM empresa WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " + e.getMessage())
        }
    }

    @Override
    List<Empresa> listAll() {
        String command = "SELECT * FROM empresa;"

        try (Statement stmt = connection.createStatement()
             ResultSet resultSet = stmt.executeQuery(command)
        ) {
            List<Empresa> responseList = new ArrayList<>()
            while (resultSet.next()) {

                responseList.add(
                        director.constructFromResultSet(resultSet, builder)
                )

            }
            return responseList

        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as empresas " + e.getMessage())
        }
    }

    @Override
    Empresa findById(Long id) {
        String command = "SELECT * FROM empresa WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {

                return director.constructFromResultSet(resultSet, builder)

            } else throw new NoSuchElementException("Empresa nao encontrada")
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }
}

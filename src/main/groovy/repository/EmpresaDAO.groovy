package repository

import model.Empresa

import java.sql.*

class EmpresaDAO implements CRUD<Empresa, Long> {

    private Connection connection;

    EmpresaDAO(Connection connection) {
        this.connection = connection
    }

    @Override
    long create(Empresa empresa) {
        String command = "INSERT INTO \"Empresa\" (name, description, email, cnpj, cep, country, password)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"

        try (PreparedStatement pstm = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, empresa.getName())
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
        String command = "UPDATE \"Empresa\" SET name=?, description=?, email=?, cnpj=?, cep=?, country=?, password=? WHERE id=?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, empresaUpdate.getName())
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
        String command = "DELETE FROM \"Empresa\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " + e.getMessage())
        }
    }

    @Override
    List<Empresa> listAll() {
        String command = "SELECT * FROM \"Empresa\";"

        try (Statement stmt = connection.createStatement()
             ResultSet resultSet = stmt.executeQuery(command)
        ) {
            List<Empresa> responseList = new ArrayList<>()
            while (resultSet.next()) {
                responseList.add(new Empresa(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("email"),
                        resultSet.getString("cnpj"),
                        resultSet.getString("cep"),
                        resultSet.getString("country"))
                )
            }

            return responseList
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as empresas " + e.getMessage())
        }
    }

    @Override
    Empresa findById(Long id) {
        String command = "SELECT * FROM \"Empresa\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {
                return new Empresa(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("email"),
                        resultSet.getString("cnpj"),
                        resultSet.getString("cep"),
                        resultSet.getString("country")
                )
            } else throw new NoSuchElementException("Empresa nao encontrada")
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }
}

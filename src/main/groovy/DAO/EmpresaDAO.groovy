package DAO

import DB.DatabaseConnection
import model.Empresa

import java.sql.*

class EmpresaDAO implements CRUD<Empresa, Long> {

    private Connection connection = DatabaseConnection.getConnection()

    @Override
    void create(Empresa empresa) {
        String command = "INSERT INTO \"Empresa\" (name, description, email, cnpj, cep, country, password)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"

        try (PreparedStatement pstm = connection.prepareStatement(command)) {
            pstm.setString(1, empresa.getName())
            pstm.setString(2, empresa.getDescription())
            pstm.setString(3, empresa.getEmail())
            pstm.setString(4, empresa.getCNPJ())
            pstm.setString(5, empresa.getCEP())
            pstm.setString(6, empresa.getCountry())
            pstm.setString(7, empresa.getPassword())

            pstm.executeUpdate()
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
             ResultSet setEmpresas = stmt.executeQuery(command)
        ) {
            List<Empresa> empresasResponse = new ArrayList<>()
            while (setEmpresas.next()) {
                empresasResponse.add(new Empresa(
                        setEmpresas.getLong("id"),
                        setEmpresas.getString("name"),
                        setEmpresas.getString("description"),
                        setEmpresas.getString("email"),
                        setEmpresas.getString("cnpj"),
                        setEmpresas.getString("cep"),
                        setEmpresas.getString("country"))
                )
            }

            return empresasResponse
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as empresas " + e.getMessage())
        }
    }

    @Override
    Empresa findById(Long id) {
        String command = "SELECT * FROM \"Empresa\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            ResultSet result = pstmt.executeQuery()

            if (result.next()) {
                return new Empresa(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("email"),
                        result.getString("cnpj"),
                        result.getString("cep"),
                        result.getString("country")
                )
            } else throw new NoSuchElementException("Empresa nao encontrada")
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }
}

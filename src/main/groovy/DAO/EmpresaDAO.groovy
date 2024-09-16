package DAO

import DB.DatabaseConnection
import DTO.Response.EmpresaResponseDTO
import DTO.VagaDTO
import model.Empresa
import model.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class EmpresaDAO {

    private Connection connection = DatabaseConnection.getConnection();

    void createEmpresa(Empresa empresa) {

        String command = "INSERT INTO \"Empresa\" (name, description, email, cnpj, cep, country, password)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

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

    List<EmpresaResponseDTO> listAllEmpresas() {
        String command = "SELECT * FROM \"Empresa\";"

        try (Statement stmt = connection.createStatement();
             ResultSet setEmpresas = stmt.executeQuery(command)
        ) {
            List<EmpresaResponseDTO> empresasResponse = new ArrayList<>();
            while (setEmpresas.next()) {
                empresasResponse.add(new EmpresaResponseDTO(
                        setEmpresas.getLong("id"),
                        setEmpresas.getString("name"),
                        setEmpresas.getString("description"),
                        setEmpresas.getString("email"),
                        setEmpresas.getString("cnpj"),
                        setEmpresas.getString("cep"),
                        setEmpresas.getString("country"))
                )
            }

            return empresasResponse;
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as empresas " + e.getMessage())
        }
    }

    EmpresaResponseDTO findEmpresaById(long id) {
        String command = "SELECT * FROM \"Empresa\" WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            ResultSet result = pstmt.executeQuery()

            if (result.next()) {
                return new EmpresaResponseDTO(
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

    void updateEmpresa(Empresa empresaUpdate, long id){
        String command = "UPDATE \"Empresa\" SET name=?, description=?, email=?, cnpj=?, cep=?, country=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, empresaUpdate.getName())
            pstmt.setString(2, empresaUpdate.getDescription())
            pstmt.setString(3, empresaUpdate.getEmail())
            pstmt.setString(4, empresaUpdate.getCNPJ())
            pstmt.setString(5, empresaUpdate.getCEP())
            pstmt.setString(6, empresaUpdate.getCountry())
            pstmt.setLong(6, id)

            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar")
        }
    }
    void updateEmpresa(Empresa empresaUpdate){
        String command = "UPDATE \"Empresa\" SET name=?, description=?, email=?, cnpj=?, cep=?, country=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, empresaUpdate.getName())
            pstmt.setString(2, empresaUpdate.getDescription())
            pstmt.setString(3, empresaUpdate.getEmail())
            pstmt.setString(4, empresaUpdate.getCNPJ())
            pstmt.setString(5, empresaUpdate.getCEP())
            pstmt.setString(6, empresaUpdate.getCountry())
            pstmt.setLong(6, empresaUpdate.getId())

            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar")
        }
    }

    void deleteEmpresaById(long id) {
        String command = "DELETE FROM \"Empresa\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " +e.getMessage())
        }
    }

}

package DAO

import DB.DatabaseConnection
import DTO.Response.CandidatoCompetenciaDTO
import DTO.Response.CandidatoResponseDTO
import model.Candidato

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CandidatoDAO {

    private CandidatoCompetenciaDAO candidatoCompetenciaDAO;
    private connection = DatabaseConnection.getConnection()

    void createCandidato(Candidato candidato) {
        String command = "INSERT INTO \"Candidato\" (first_name, last_name, email, cpf, city, cep, description, password)" +
                "VALUES (?, ?, ?, ? , ?, ?, ?, ?);"

        try (PreparedStatement pstmt = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, candidato.getFirst_name())
            pstmt.setString(2, candidato.getLast_name())
            pstmt.setString(3, candidato.getEmail())
            pstmt.setString(4, candidato.getCPF())
            pstmt.setString(5, candidato.getCity())
            pstmt.setString(6, candidato.getCEP())
            pstmt.setString(7, candidato.getDescription())
            pstmt.setString(8, candidato.getPassword())

            pstmt.executeUpdate()

            ResultSet generateKey = pstmt.getGeneratedKeys()

            if (candidato.getCompetences() != null) {
                if (candidato.getCompetences().size() > 0)
                    candidatoCompetenciaDAO = new CandidatoCompetenciaDAO(connection)
                while (generateKey.next()) {
                    candidato.setId(generateKey.getLong(1))
                    print(generateKey)
                }

                candidatoCompetenciaDAO.insertCompetenciaToCandidato(candidato.getId(), candidato.getCompetences())
            }

        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao salvar " + e.getMessage())
        }
    }

    List<CandidatoResponseDTO> listAllCandidatos() {

        String command = "SELECT * FROM \"Candidato\";"

        try (Statement stmt = connection.createStatement();
             ResultSet setCandidatos = stmt.executeQuery(command)
        ) {
            List<CandidatoResponseDTO> candidatosResponse = new ArrayList<>();
            while (setCandidatos.next()) {

                candidatosResponse.add(
                        new CandidatoResponseDTO.Builder()
                                .CEP(setCandidatos.getString("cep"))
                                .city(setCandidatos.getString("city"))
                                .CPF(setCandidatos.getString("cpf"))
                                .description(setCandidatos.getString("description"))
                                .email(setCandidatos.getString("email"))
                                .firstName(setCandidatos.getString("first_name"))
                                .lastName(setCandidatos.getString("last_name"))
                                .id(setCandidatos.getLong("id"))
                                .build())
            }

            return candidatosResponse;
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar candidatos " + e.getMessage())
        }

    }

    List<CandidatoCompetenciaDTO> listAllCandidatosAndCompetencias() {

        List<CandidatoCompetenciaDTO> candidadosCompetencias = new ArrayList<>()

        String command = "SELECT candidato.id, candidato.first_name, candidato.last_name, candidato.cpf, " +
                "candidato.description, candidato.email, candidato.cep, candidato.city, " +
                "comp.id AS competencia_id, comp.description AS competencia_name " +
                "FROM \"Candidato\" AS candidato " +
                "LEFT JOIN \"Candidato_Competencia\" AS cand_comp ON cand_comp.candidato_id = candidato.id " +
                "LEFT JOIN competencia_by_enum AS comp ON cand_comp.competencia_id = comp.id;";

        try (Statement stmt = connection.prepareStatement(command)
             ResultSet resultSet = stmt.executeQuery()) {

            Map<Long, CandidatoCompetenciaDTO> candidatoMap = new HashMap<>()

            while (resultSet.next()){
                long candidatoId = resultSet.getLong("id")
                CandidatoCompetenciaDTO candidato = candidatoMap.get(candidatoId)

                if(candidato==null) {
                    candidato = new CandidatoCompetenciaDTO(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("cpf"),
                            resultSet.getString("description"),
                            resultSet.getString("email"),
                            resultSet.getString("cep"),
                            resultSet.getString("city"),
                            new ArrayList<String>()
                    )
                    candidatoMap.put(candidatoId, candidato)
                }
                String competenciaName = resultSet.getString("competencia_name")
                if(competenciaName!= null && !competenciaName.isBlank()) {
                    candidato.competences().add(competenciaName)
                }
            }
            candidadosCompetencias.addAll(candidatoMap.values())
        } catch (SQLException e ) {
            e.printStackTrace()
        }
        return candidadosCompetencias
    }

    CandidatoResponseDTO findCandidatoById(long id) {

        String command = "SELECT * FROM \"Candidato\" WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            ResultSet result = pstmt.executeQuery()

            if (result.next()) {
                return new CandidatoResponseDTO.Builder()
                        .CEP(result.getString("cep"))
                        .city(result.getString("city"))
                        .CPF(result.getString("cpf"))
                        .description(result.getString("description"))
                        .email(result.getString("email"))
                        .firstName(result.getString("first_name"))
                        .lastName(result.getString("last_name"))
                        .id(result.getLong("id"))
                        .build()

            } else throw new NoSuchElementException("Candidato nao encontrado")
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }

    void updateCandidato(Candidato candidato, long id) {
        String command = "UPDATE \"Candidato\" SET first_name=?, last_name=?, email=?, cep=?, cpf=?, city=?, description=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, candidato.getFirst_name())
            pstmt.setString(2, candidato.getLast_name())
            pstmt.setString(3, candidato.getEmail())
            pstmt.setString(4, candidato.getCEP())
            pstmt.setString(5, candidato.getCPF())
            pstmt.setString(6, candidato.getCity())
            pstmt.setString(7, candidato.getDescription())
            pstmt.setLong(8, id)

            pstmt.executeUpdate()

            if(candidato.getCompetences()!= null && !candidato.getCompetences().isEmpty()){
                CandidatoCompetenciaDAO candidatoCompetenciaDAO = new CandidatoCompetenciaDAO(connection)
                candidatoCompetenciaDAO.updateCompetencias(id, candidato.getCompetences())
            }
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar "+ e.getMessage())
        }
    }


    void deleteCandidatoById(long id) {
        String command = "DELETE FROM \"Candidato\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " +e.getMessage())
        }
    }
}

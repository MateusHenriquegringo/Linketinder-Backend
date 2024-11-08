package dao.likes

import service.CandidatoService

import java.sql.Connection
import java.sql.PreparedStatement

class CandidatoLikesDAO {

    private Connection connection

    CandidatoLikesDAO(Connection connection) {
        this.connection = connection
    }

    void addLike(Like like){
        String command = "INSERT INTO candidato_likes (candidato_id, vaga_id) VALUES (?, ?)"

        try(PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1, like.getFrom())
            preparedStatement.setLong(2, like.getTo())
            preparedStatement.executeUpdate()
        } catch (Exception e){
            throw new RuntimeException("erro ao adicionar like "+ e.getMessage())
        }
    }

    void deleteLike (Like like) {
        String command = "DELETE FROM candidato_likes WHERE candidato_id = ? AND vaga_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1, like.getFrom())
            preparedStatement.setLong(2, like.getTo())
            preparedStatement.executeUpdate()
        } catch (Exception e){
            throw new RuntimeException("erro ao deletar like "+ e.getMessage())
        }
    }

    boolean likeToAnyVagaFromEmpresaExists(Like like){
        String command = """SELECT 1 FROM candidato_likes cl
                            JOIN vaga v 
                            ON cl.vaga_id = v.id
                            WHERE cl.candidato_id = ? 
                              AND v.empresa_id = ? """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1, like.getTo())
            preparedStatement.setLong(2, like.getFrom())

            return preparedStatement.executeQuery().next()

        } catch (Exception e){
            throw new RuntimeException("erro ao verificar like "+ e.getMessage())
        }

    }

}

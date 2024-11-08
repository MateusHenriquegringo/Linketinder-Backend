package dao.likes

import java.sql.Connection
import java.sql.PreparedStatement

class EmpresaLikesDAO {

    private Connection connection

    EmpresaLikesDAO(Connection connection) {
        this.connection = connection
    }

    void addLike(Like like){
        String command = "INSERT INTO empresa_likes (empresa_id, candidato_id) VALUES (?, ?)"

        try(PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1, like.getFrom())
            preparedStatement.setLong(2, like.getTo())
            preparedStatement.executeUpdate()
        } catch (Exception e){
            throw new RuntimeException("erro ao adicionar like "+ e.getMessage())
        }
    }

    void deleteLike(Like like){
        String command = "DELETE FROM empresa_likes WHERE empresa_id = ? AND candidato_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1, like.getFrom())
            preparedStatement.setLong(2, like.getTo())
            preparedStatement.executeUpdate()
        } catch (Exception e){
            throw new RuntimeException("erro ao deletar like "+ e.getMessage())
        }
    }

    boolean likeExists(Like like){
        String command = "SELECT 1 FROM empresa_likes WHERE empresa_id = ? AND candidato_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1, like.getFrom())
            preparedStatement.setLong(2, like.getTo())

            return preparedStatement.executeQuery().next()
        } catch (Exception e){
            throw new RuntimeException("erro ao verificar like "+ e.getMessage())
        }

    }

}

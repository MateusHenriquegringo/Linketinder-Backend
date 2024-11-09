package app.service

import org.springframework.stereotype.Service

@Service
class NotificationService {

    public notifyMatch(long candidatoId, long empresaId) {
        println "Notifying match between candidato $candidatoId and empresa $empresaId"
    }

}

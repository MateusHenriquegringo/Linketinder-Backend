package repository.auxiliary

enum SQLQuerys {
    RETURN_CANDIDATO_WITH_COMPETENCES(
            """
SELECT c.id, c.first_name, c.last_name, c.cpf, c.email, c.cep, c.city, c.description,
       STRING_AGG(comp.description::TEXT, ', ') AS competences
FROM candidato c
JOIN candidato_competencia cc ON c.id = cc.candidato_id
WHERE c.id = ? 
GROUP BY c.id, c.first_name, c.last_name, c.cpf, c.email, c.cep, c.city, c.description;
"""
    ),

    RETURN_VAGA_WITH_COMPETENCES(
            """
            SELECT v.id, v.vaga_name, v.empresa_id, v.state, v.city, v.description, 
                STRING_AGG(comp.description::text, ', ') AS competences
            FROM vaga v
            JOIN vaga_competencia vc ON v.id = vc.vaga_id
            JOIN competencia_by_enum comp ON vc.competencia_id = comp.id
            WHERE v.id = ?
            GROUP BY v.id, v.vaga_name, v.empresa_id, v.state, v.city, v.description;
"""
    ),

    LIST_ALL_CANDIDATOS_JOIN_COMPETENCIAS (
            """
SELECT 
    c.*,
    STRING_AGG(cc.competences::TEXT, ', ') AS competences
FROM 
    candidato c
LEFT JOIN 
    candidato_competencia cc ON c.id = cc.candidato_id
GROUP BY 
    c.id, c.first_name, c.last_name, c.cep, c.cpf, c.description;
"""
    ),
    LIST_ALL_VAGAS_JOIN_COMPETENCIAS(
            """
SELECT 
    v.*,
    STRING_AGG(vc.competences::TEXT, ', ') AS competences
FROM 
    flyway.vaga v
LEFT JOIN 
    flyway.vaga_competencia vc ON v.id = vc.vaga_id
GROUP BY 
    v.id, v.vaga_name, v.description, v.empresa_id;
"""
    )

    String getQuery() {
        return query
    }
    private String query

    SQLQuerys(String query) {
        this.query = query
    }
}

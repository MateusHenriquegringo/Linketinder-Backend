SELECT 1
FROM candidato_likes cl
JOIN vaga v ON cl.empresa_id = v.id
WHERE cl.candidato_id = :candidato_id
  AND v.empresa_id = :empresa_id
LIMIT 1;
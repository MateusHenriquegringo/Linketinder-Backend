SELECT 1
FROM empresas el
JOIN vaga v ON v.empresa_id = el.empresa_id
WHERE el.candidato_id = :candidato_id
  AND v.id = :vaga_id
LIMIT 1;

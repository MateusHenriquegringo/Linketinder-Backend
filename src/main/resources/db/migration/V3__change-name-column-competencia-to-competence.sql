
BEGIN;

ALTER TABLE candidato_competencia RENAME COLUMN competencia TO competences;
ALTER TABLE vaga_competencia RENAME COLUMN competencia TO competences;

COMMIT;

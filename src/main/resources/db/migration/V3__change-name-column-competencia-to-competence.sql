
BEGIN;

ALTER TABLE candidato_competencia RENAME COLUMN competencia TO competences;
ALTER TABLE vaga_competencia RENAME COLUMN competencia TO competences;

ALTER TABLE candidato ADD CONSTRAINT unique_c_email UNIQUE (email);
ALTER TABLE empresa ADD CONSTRAINT unique_e_email UNIQUE (email);

COMMIT;

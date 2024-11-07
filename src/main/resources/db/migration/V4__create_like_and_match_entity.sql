CREATE TABLE IF NOT EXISTS candidato_likes (
    id SERIAL PRIMARY KEY,
    candidato_id INT NOT NULL,
    vaga_id INT NOT NULL,
    date_like TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_candidato FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE,
    CONSTRAINT fk_vaga FOREIGN KEY (vaga_id) REFERENCES vaga(id) ON DELETE CASCADE,

    CONSTRAINT unique_candidato_like UNIQUE (candidato_id, vaga_id)
);

CREATE TABLE IF NOT EXISTS empresa_likes (
    id SERIAL PRIMARY KEY,
    empresa_id INT NOT NULL,
    candidato_id INT NOT NULL,
    date_like TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_candidato FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE,
    CONSTRAINT fk_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE,

    CONSTRAINT unique_empresa_like UNIQUE (candidato_id, empresa_id)
);

CREATE TABLE IF NOT EXISTS matches (
    id SERIAL PRIMARY KEY,
    candidato_id INT NOT NULL,
    empresa_id INT NOT NULL,
    data_match TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_candidato_match FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE,
    CONSTRAINT fk_empresa_match FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE,

    CONSTRAINT unique_match UNIQUE (candidato_id, empresa_id)
);
CREATE TABLE IF NOT EXISTS likes (
    id SERIAL PRIMARY KEY,
    candidato_id INT NOT NULL,
    empresa_id INT NOT NULL,
    direction VARCHAR(50) NOT NULL CHECK (direction IN ('CANDIDATO_TO_EMPRESA', 'EMPRESA_TO_CANDIDATO')),
    date_like TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_candidato FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE,
    CONSTRAINT fk_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id) ON DELETE CASCADE,

    CONSTRAINT unique_like UNIQUE (candidato_id, empresa_id, direction)
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
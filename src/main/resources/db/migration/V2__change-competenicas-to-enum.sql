CREATE TYPE competencia_enum AS ENUM (
    'JAVASCRIPT',
    'TYPESCRIPT',
    'PYTHON',
    'JAVA',
    'C#',
    'PHP',
    'KOTLIN',
    'SWIFT',
    'SQL',
    'NOSQL',
    'HTML',
    'CSS',
    'REACT',
    'ANGULAR',
    'VUE',
    'NODE.JS',
    'SPRING BOOT',
    'DJANGO',
    'DOCKER',
    'KUBERNETES',
    'AWS',
    'AZURE',
    'GIT',
    'DEVOPS',
    'TEST-DRIVEN DEVELOPMENT',
    'CI/CD',
    'GO',
    'RUST',
    'SCALA',
    'C++',
    'GRAPHQL',
    'MACHINE LEARNING',
    'CYBERSECURITY'
);

ALTER TABLE candidato_competencia DROP COLUMN competencia_id;
ALTER TABLE candidato_competencia ADD COLUMN competencia competencia_enum NOT NULL;


ALTER TABLE vaga_competencia DROP COLUMN competencia_id;
ALTER TABLE vaga_competencia ADD COLUMN competencia competencia_enum NOT NULL;

DROP TABLE IF EXISTS competencia_by_enum CASCADE;
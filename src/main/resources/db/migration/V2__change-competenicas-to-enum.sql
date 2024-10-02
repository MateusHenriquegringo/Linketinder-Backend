CREATE TYPE competencia_enum AS ENUM (
    'JavaScript',
    'TypeScript',
    'Python',
    'Java',
    'C#',
    'PHP',
    'Kotlin',
    'Swift',
    'SQL',
    'NoSQL',
    'HTML',
    'CSS',
    'React',
    'Angular',
    'Vue',
    'Node.js',
    'Spring Boot',
    'Django',
    'Docker',
    'Kubernetes',
    'AWS',
    'Azure',
    'Git',
    'DevOps',
    'Test-Driven Development',
    'CI/CD',
    'Go',
    'Rust',
    'Scala',
    'C++',
    'GraphQL',
    'Machine Learning',
    'Cybersecurity'
);

ALTER TABLE candidato_competencia DROP COLUMN competencia_id;
ALTER TABLE candidato_competencia ADD COLUMN competencia competencia_enum NOT NULL;


ALTER TABLE vaga_competencia DROP COLUMN competencia_id;
ALTER TABLE vaga_competencia ADD COLUMN competencia competencia_enum NOT NULL;

DROP TABLE IF EXISTS competencia_by_enum CASCADE;
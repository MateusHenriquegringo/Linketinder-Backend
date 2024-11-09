package app.enums

enum CompetenciaENUM {

        JAVASCRIPT("JavaScript"),
        TYPESCRIPT("TypeScript"),
        PYTHON("Python"),
        JAVA("Java"),
        C_SHARP("C#"),
        PHP("PHP"),
        KOTLIN("Kotlin"),
        SWIFT("Swift"),
        SQL("SQL"),
        NOSQL("NoSQL"),
        HTML("HTML"),
        CSS("CSS"),
        REACT("React"),
        ANGULAR("Angular"),
        VUE("Vue"),
        NODE_JS("Node.js"),
        SPRING_BOOT("Spring Boot"),
        DJANGO("Django"),
        DOCKER("Docker"),
        KUBERNETES("Kubernetes"),
        AWS("AWS"),
        AZURE("Azure"),
        GIT("Git"),
        DEVOPS("DevOps"),
        TEST_DRIVEN_DEVELOPMENT("Test-Driven Development"),
        CI_CD("CI/CD"),
        GO("Go"),
        RUST("Rust"),
        SCALA("Scala"),
        C_PLUS_PLUS("C++"),
        GRAPHQL("GraphQL"),
        MACHINE_LEARNING("Machine Learning"),
        CYBERSECURITY("Cybersecurity");

        private final String description;

        CompetenciaENUM(String description) {
                this.description = description;
        }

        String getDescription() {
                return description;
        }

        static CompetenciaENUM fromString(String text) {
                for (CompetenciaENUM c : values()) {
                        if (c.description.equalsIgnoreCase(text)) {
                                return c;
                        }
                }
                throw new IllegalArgumentException("Não é uma competência válida: " + text);
        }
}

package enums

public enum CompetenciasENUM {

        JAVASCRIPT(1, "JavaScript"),
        TYPESCRIPT(2, "TypeScript"),
        PYTHON(3, "Python"),
        JAVA(4, "Java"),
        C_SHARP(5, "C#"),
        PHP(6, "PHP"),
        KOTLIN(7, "Kotlin"),
        SWIFT(8, "Swift"),
        SQL(9, "SQL"),
        NOSQL(10, "NoSQL"),
        HTML(11, "HTML"),
        CSS(12, "CSS"),
        REACT(13, "React"),
        ANGULAR(14, "Angular"),
        VUE(15, "Vue"),
        NODE_JS(16, "Node.js"),
        SPRING_BOOT(17, "Spring Boot"),
        DJANGO(18, "Django"),
        DOCKER(19, "Docker"),
        KUBERNETES(20, "Kubernetes"),
        AWS(21, "AWS"),
        AZURE(22, "Azure"),
        GIT(23, "Git"),
        DEVOPS(24, "DevOps"),
        TEST_DRIVEN_DEVELOPMENT(25, "Test-Driven Development"),
        CI_CD(26, "CI/CD");

        private final int id;
        private final String description;

        CompetenciasENUM(int id, String description) {
                this.id = id;
                this.description = description;
        }

        public int getId() {
                return id;
        }

        public String getDescription() {
                return description;
        }
}

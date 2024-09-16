package enums

enum CompetenciasENUM {

        JAVA,
        PYTHON,
        JAVASCRIPT,
        TYPESCRIPT,
        CSHARP,
        PHP,
        KOTLIN,
        SWIFT,
        SQL,
        NOSQL,
        HTML,
        CSS,
        REACT,
        ANGULAR,
        VUE,
        NODEJS,
        SPRING_BOOT,
        DJANGO,
        DOCKER,
        KUBERNETES,
        AWS,
        AZURE,
        GIT,
        DEVOPS,
        TEST_DRIVEN_DEVELOPMENT,
        CI_CD;

        @Override
        public String toString() {
            return name().replace('_', ' ');
        }

}
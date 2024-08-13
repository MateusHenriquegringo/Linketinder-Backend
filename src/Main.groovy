class Main {
    //Mateus Derossi
    static Scanner scanner = new Scanner(System.in)
    static manager = new EmpresasCandidatosManager()

    static void main(String[] args) {

        while(true) {
            println("Digite 1 para listar candidatos")
            println("Digite 2 para listar empresas")
            println("Digite 0 para sair")
            String input = scanner.nextLine()

        switch (input) {
            case "1":
                manager.listarCandidatos()
                break
            case "2":
                manager.listarEmpresas()
                break
            case "0":
                println("encerrando")
                return
            default:
                "digite um numero valido"
        }
    }

}
}
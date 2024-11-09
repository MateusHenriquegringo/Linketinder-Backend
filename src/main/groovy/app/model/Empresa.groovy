package app.model

import jakarta.persistence.*

@Entity
@Table(name = "empresa")
class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id

    @Column(name = "empresa_name")
    private String empresa_name

    @Column(name = "description")
    private String description

    @Column(name = "email", unique = true)
    private String email

    @Column(name = "cnpj", unique = true)
    private String cnpj

    @Column(name = "state")
    private String state

    @Column(name = "city")
    private String city

    @Column(name = "country")
    private String country

    @Column(name = "password")
    private String password

}

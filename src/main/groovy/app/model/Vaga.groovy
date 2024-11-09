package app.model


import app.enums.CompetenciaENUM
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "vaga")
class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id

    @Column(name = "vaga_name")
    private String vaga_name

    @Column(name = "description")
    private String description

    @Column(name = "email", unique = true)
    private String email

    @Column(name = "city")
    private String city

    @Column(name = "state")
    private String state

    @Column(name = "country")
    private String country

    @OneToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CompetenciaENUM.class)
    @CollectionTable(name = "vaga_competences", joinColumns = @JoinColumn(name = "vaga_id"))
    @Column(name = "competences")
    private Set<CompetenciaENUM> competences

}

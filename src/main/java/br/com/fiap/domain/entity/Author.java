package br.com.fiap.domain.entity;


import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TB_AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AUTHOR")
    @Column(name = "ID_AUTHOR")
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "pessoa",
            referencedColumnName = "ID_PESSOA",
            foreignKey = @ForeignKey(name = "FK_AUTHOR_PESSOA")
    )
    private PessoaFisica pessoa;

    @ManyToMany(mappedBy = "writers")
    @OrderBy("name ASC")
    Set<Book> obras = new LinkedHashSet<>();


    public Author addObra(Book b) {
        this.obras.add( b );
        return this;
    }

    public Author removeObra(Book b) {
        this.obras.remove( b );
        return this;
    }

    public Author() {
        this.obras = new LinkedHashSet<>();
    }

    public Author(Long id, PessoaFisica pessoaFisica, Set<Book> obras) {
        this.id = id;
        this.pessoa = pessoaFisica;
        this.obras = Objects.nonNull( obras ) ? obras : new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Author setId(Long id) {
        this.id = id;
        return this;
    }

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public Author setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", pessoa='" + pessoa + '\'' +
                '}';
    }
}

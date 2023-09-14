package br.com.fiap.domain.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="TB_BOOK", uniqueConstraints = {
        @UniqueConstraint(name="UK_ISBN_BOOK", columnNames = "ISBN_BOOKS")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BOOK")
    @Column(name="ID_BOOK")
    private Long id;

    @Column(name="NM_BOOK", nullable = false)
    private String nome;

    @Column(name="ISBN_BOOK", nullable = false)
    private String ISBN;

    @Column(name="DT_LAUNCH")
    private LocalDateTime launch;

    @ManyToMany
    @JoinTable(
            name = "TB_BOOK_AUTHOR",
            joinColumns = {
                    @JoinColumn(name = "BOOK",
                            referencedColumnName = "ID_BOOK",
                            foreignKey = @ForeignKey(name="FK_BOOK_AUTHOR")
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "AUTHOR",
                            referencedColumnName = "ID_AUTHOR",
                            foreignKey = @ForeignKey(name = "FK_AUTHOR_BOOK")
                    )
            }
    )
    @Column
    private Set<Author> writers;

    public Book() {writers = new LinkedHashSet<>();};
    public Book(Long id, String nome, LocalDateTime launch, String ISBN, Set<Author> writers) {
        this.id = id;
        this.nome = nome;
        this.ISBN = ISBN;
        this.launch = launch;
        this.writers = writers != null ? writers : new LinkedHashSet<>();
    }

    public Book addAuthor(Author a) {
        writers.add(a);
        return this;
    }

    public Book removeAuthor(Author a) {
        writers.remove(a);
        return this;
    }

    public Set<Author> getWriters() {
        return Collections.unmodifiableSet(writers);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public LocalDateTime getLaunch() {
        return launch;
    }

    public void setLaunch(LocalDateTime launch) {
        this.launch = launch;
    }

    @Override
    public String toString() {
        return "Book{" +
                "\nid=" + id +
                "\nnome='" + nome + '\'' +
                "\nISBN='" + ISBN + '\'' +
                "\nlaunch=" + launch +
                "\nwriters=" + writers +
                '}';
    }
}

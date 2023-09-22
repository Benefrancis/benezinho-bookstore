package br.com.fiap.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "TB_BOOK",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_ISBN_BOOK", columnNames = "ISBN_BOOK")
        }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BOOK")
    @Column(name = "ID_BOOK")
    private Long id;

    @Column(name = "NM_BOOK", nullable = false)
    private String name;

    @Column(name = "ISBN_BOOK", nullable = false)
    private String ISBN;

    @Column(name = "DT_LAUNCH")
    private LocalDateTime launch;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_BOOK_AUTHOR",
            joinColumns = {
                    @JoinColumn(name = "BOOK", referencedColumnName = "ID_BOOK", foreignKey = @ForeignKey(name = "FK_BOOK_AUTHOR"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "AUTHOR", referencedColumnName = "ID_AUTHOR", foreignKey = @ForeignKey(name = "FK_AUTHOR_BOOK"))
            }
    )
    private Set<Author> writers;

    public Book() {
        writers = new LinkedHashSet<>();
    }

    public Book(Long id, String name, String ISBN, LocalDateTime launch, Set<Author> writers) {
        this.id = id;
        this.name = name;
        this.ISBN = ISBN;
        this.launch = launch;
        this.writers = writers != null ? writers : new LinkedHashSet<>();
    }

    public Set<Author> getWriters() {
        return Collections.unmodifiableSet(writers);
    }

    public Book addAuthor(Author a) {
        writers.add(a);
        return this;
    }

    public Book removeAuthor(Author a) {
        writers.remove(a);
        return this;
    }

    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    public String getISBN() {
        return ISBN;
    }

    public Book setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public LocalDateTime getLaunch() {
        return launch;
    }

    public Book setLaunch(LocalDateTime launch) {
        this.launch = launch;
        return this;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", launch=" + launch +
                ", writers=" + writers +
                '}';
    }
}
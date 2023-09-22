package br.com.fiap.domain.repository;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class AuthorRepository {

    EntityManagerFactory factory;

    public AuthorRepository() {
        this.factory = Persistence.createEntityManagerFactory( Main.DATABASE, Main.getProperties() );
    }

    public List<Author> findAll() {
        EntityManager manager = factory.createEntityManager();
        List<Author> list = manager.createQuery( "FROM Author" ).getResultList();
        manager.close();
        return list;
    }

    public Author findById(Long id) {
        EntityManager manager = factory.createEntityManager();
        Author author = manager.find( Author.class, id );
        manager.close();
        return author;
    }

    public Author persist(Author author) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist( author );
        manager.getTransaction().commit();
        manager.close();
        return author;
    }

}

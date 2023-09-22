package br.com.fiap.domain.repository;

import br.com.fiap.Main;
import br.com.fiap.domain.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class BookRepository {

    EntityManagerFactory factory;

    public BookRepository() {
        this.factory = Persistence.createEntityManagerFactory( Main.DATABASE, Main.getProperties() );
    }

    public List<Book> findAll() {
        EntityManager manager = factory.createEntityManager();
        List<Book> list = manager.createQuery( "FROM Book" ).getResultList();
        manager.close();
        return list;
    }

    public Book findById(Long id) {
        EntityManager manager = factory.createEntityManager();
        Book book = manager.find( Book.class, id );
        manager.close();
        return book;
    }

    public Book persist(Book book) {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.persist( book );
        manager.getTransaction().commit();
        manager.close();
        return book;
    }

}

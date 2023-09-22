package br.com.fiap;

import br.com.fiap.domain.entity.Author;
import br.com.fiap.domain.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();



        var livro = new Book();

        livro.setId(null);
        livro.setName("Java Mapeamento Objeto Relacional");
        livro.setLaunch(LocalDateTime.now());
        livro.setISBN(UUID.randomUUID().toString());
        // trocar por pessoa!
//        livro.addAuthor(bene);
//        livro.addAuthor(raquel);
//        livro.addAuthor(guilherme);

        manager.getTransaction().begin();

        manager.persist(livro);

        manager.getTransaction().commit();


        manager.close();
        factory.close();

        System.out.println(livro);

    }
}
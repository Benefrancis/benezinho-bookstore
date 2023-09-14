package br.com.fiap;

import br.com.fiap.domain.entity.Author;
import br.com.fiap.domain.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();

        // var ?? agora pode aff
        var bene = new Author(null, "Benefrancis");
        var raquel = new Author(null, "Raquel");
        var gulherme = new Author(null, "Guilherme");

        var livro = new Book();
        livro.setId(null);
        livro.setNome("Java mapeamento Objeto relacional");
        livro.setLaunch(LocalDateTime.now());
        livro.setISBN(UUID.randomUUID().toString());
        livro.addAuthor(raquel);
        livro.addAuthor(gulherme);
        livro.addAuthor(bene);



        factory.close();
        manager.close();



    }
}
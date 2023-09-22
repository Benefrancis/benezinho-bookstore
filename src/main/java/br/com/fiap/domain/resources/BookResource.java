package br.com.fiap.domain.resources;

import br.com.fiap.domain.entity.Book;
import br.com.fiap.domain.repository.BookRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/book")
public class BookResource {

    private BookRepository repo = new BookRepository();

    @GET
    public Response findAll() {
        List<Book> list = repo.findAll();
        return Response.ok( list ).build();
    }

}

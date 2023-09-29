package br.com.fiap;

import br.com.fiap.infra.EntityManagerFactoryProvider;
import br.com.fiap.infra.EntityManagerProvider;
import br.com.fiap.infra.configuration.Password;
import br.com.fiap.infra.configuration.cors.CORSFilter;
import br.com.fiap.infra.configuration.jwt.JsTokenFilterNeeded;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost/";

    public static final String PERSISTENCE_UNIT = "maria-db";

    @PersistenceContext
    static EntityManager manager;

    public static HttpServer startServer() {


        final ResourceConfig rc = new ResourceConfig()
                // Configure container response filters (CORSFilter)
                .register( CORSFilter.class )
                .register( Password.class )
                // Configure container request filters (JsTokenFilterNeeded)
                .register( JsTokenFilterNeeded.class )
                .register(
                        new AbstractBinder() {
                            @Override
                            protected void configure() {
                                bindFactory( EntityManagerFactoryProvider.class )
                                        .to( EntityManagerFactory.class )
                                        .in( Singleton.class );
                                bindFactory( EntityManagerProvider.class )
                                        .to( EntityManager.class )
                                        .in( RequestScoped.class );
                            }
                        }
                ).register( EntityManagerFactoryProvider.of( PERSISTENCE_UNIT ).provide() )
                .packages( "br.com.fiap.domain.resources" );
        return GrizzlyHttpServerFactory.createHttpServer( URI.create( BASE_URI ), rc );
    }

    public static void main(String[] args) {

        final HttpServer server = startServer();
        System.out.println( String.format( "Bookstore app started with endpoints available as %s%nHit Ctrl-C to stop it....", BASE_URI + "authorization") );
        try {
            System.in.read();
            server.stop();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }
}
package br.com.fiap;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Main {


    private static final String BASE_URI = "http://localhost/";
    public static final String DATABASE = "oracle";


    /**
     * Método responsavel por configurar e fornecer um servidor HTTP
     *
     * @return
     */
    private static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig()
                .packages( "br.com.fiap.domain.resources" );
        return GrizzlyHttpServerFactory.createHttpServer( URI.create( BASE_URI ), rc );
    }

    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println(

                String.format( "Bookstore app started with endpoints available " +
                        "as %s%nHit Ctrl-C to stop it....", BASE_URI )
        );

        try {
            System.in.read();
            server.stop();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }

    /**
     * Altera as configurações do persistence.xml
     * @return
     */
    public static Map<String, Object> getProperties() {
        Map<String, String> env = System.getenv();
        Map<String, Object> properties = new HashMap<>();

        for (String chave : env.keySet()) {
            if (chave.contains( "USER_FIAP" )) {
                properties.put( "jakarta.persistence.jdbc.user", env.get( chave ) );
            }
            if (chave.contains( "PASSWORD_FIAP" )) {
                properties.put( "jakarta.persistence.jdbc.password", env.get( chave ) );
            }
            // Outras configurações de propriedade ....
        }
        return properties;
    }


}
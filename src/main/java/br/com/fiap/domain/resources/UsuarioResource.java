package br.com.fiap.domain.resources;

import br.com.fiap.Main;
import br.com.fiap.domain.dto.UsuarioDTO;
import br.com.fiap.domain.entity.Usuario;
import br.com.fiap.domain.service.UsuarioService;
import br.com.fiap.infra.configuration.jwt.JwTokenHelper;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Path("/")
public class UsuarioResource {

    UsuarioService service = UsuarioService.of( Main.PERSISTENCE_UNIT );

    @POST
    @Path(value = "authorization")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorizationService(UsuarioDTO usuarioDTO)   {
        if (Objects.isNull( usuarioDTO ))
            return Response.status( 401 ).entity( "User is required" ).build();
        if (usuarioDTO.username().isEmpty())
            return Response.status( 401 ).entity( "username field cannot be empty!" ).build();
        if (usuarioDTO.password().isEmpty())
            return Response.status( 401 ).entity( "password field cannot be empty!" ).build();

        Usuario usuarioAutenticado = service.autenticar( usuarioDTO );
        if (Objects.isNull( usuarioAutenticado ))
            return Response.status( 401 ).entity( "User or password invalid!" ).build();

        //Gerando a chave JWT
        String privateKey = JwTokenHelper.getInstance().generatePrivateKey( usuarioDTO.username(), usuarioDTO.password() );

        //Adiciono a chave JWT e Responde a Requisição
        UsuarioDTO usuarioAutenticadoDTO = UsuarioDTO.of( usuarioAutenticado, privateKey );
        System.out.println( "You're authenticated successfully.\n" + usuarioAutenticadoDTO + "\nPrivate key will be valid for 30 mins: \n" + privateKey );
        return Response.ok( usuarioAutenticadoDTO ).build();
    }
}
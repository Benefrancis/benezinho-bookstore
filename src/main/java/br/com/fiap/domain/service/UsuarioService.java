package br.com.fiap.domain.service;

import br.com.fiap.domain.dto.UsuarioDTO;
import br.com.fiap.domain.entity.Usuario;
import br.com.fiap.domain.repository.UsuarioRepository;
import br.com.fiap.infra.EntityManagerFactoryProvider;
import br.com.fiap.infra.configuration.Password;
import jakarta.persistence.EntityManagerFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;


public class UsuarioService implements Service<Usuario, Long> {

    private static volatile UsuarioService instance;

    private UsuarioRepository repo;

    private UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public static UsuarioService of(String persistenceUnit) {
        UsuarioService result = instance;
        if (Objects.nonNull( result )) return result;

        synchronized (UsuarioService.class) {
            if (Objects.isNull( instance )) {
                EntityManagerFactory factory = EntityManagerFactoryProvider.of( persistenceUnit ).provide();
                UsuarioRepository repo = UsuarioRepository.of( factory.createEntityManager() );
                instance = new UsuarioService( repo );
            }
            return instance;
        }
    }

    public Usuario autenticar(UsuarioDTO dto)  {
        Usuario a = repo.findByUsername( dto.username() );

        boolean autenticado = Password.check(dto.password(), a.getPassword());

        if (Objects.nonNull( a ) && autenticado) {
            return a;
        }
        return null;
    }


    @Override
    public List<Usuario> findAll() {
        return repo.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return repo.findById( id );
    }

    @Override
    public List<Usuario> findByName(String texto) {
        return repo.findByName( texto );
    }

    @Override
    public Usuario persist(Usuario a) {
        a.setPassword(Password.encoder(a.getPassword()));
        return repo.persist( a );
    }

}

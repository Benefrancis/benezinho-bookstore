package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Pessoa;
import br.com.fiap.domain.entity.PessoaFisica;
import br.com.fiap.domain.repository.PessoaFisicaRepository;
import br.com.fiap.infra.EntityManagerFactoryProvider;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Objects;

public class PessoaFisicaService implements Service<PessoaFisica, Long> {

    private static volatile PessoaFisicaService instance;

    private PessoaFisicaRepository repo;

    private PessoaFisicaService(PessoaFisicaRepository repo) {
        this.repo = repo;
    }

    public static PessoaFisicaService of(String persistenceUnit) {
        PessoaFisicaService result = instance;
        if (Objects.nonNull( result )) return result;

        synchronized (PessoaFisicaService.class) {
            if (Objects.isNull( instance )) {
                EntityManagerFactory factory = EntityManagerFactoryProvider.of( persistenceUnit ).provide();
                PessoaFisicaRepository pessoaRepository = PessoaFisicaRepository.of( factory.createEntityManager() );
                instance = new PessoaFisicaService( pessoaRepository );
            }
            return instance;
        }
    }

    @Override
    public List<PessoaFisica> findAll() {
        return repo.findAll();
    }

    @Override
    public PessoaFisica findById(Long id) {
        return repo.findById( id );
    }


    @Override
    public List<PessoaFisica> findByName(String texto) {
        return repo.findByName( texto );
    }

    @Override
    public PessoaFisica persist(PessoaFisica pessoa) {


        return repo.persist( pessoa );
    }
}

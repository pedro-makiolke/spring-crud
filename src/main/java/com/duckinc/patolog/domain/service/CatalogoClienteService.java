package com.duckinc.patolog.domain.service;

import com.duckinc.patolog.domain.exceptions.DomainException;
import com.duckinc.patolog.domain.model.Cliente;
import com.duckinc.patolog.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CatalogoClienteService {

    private ClienteRepository repository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        boolean emailUsado = repository.findByEmail(cliente.getEmail())
                .stream()
                .anyMatch(x -> !x.equals(cliente));

        if(emailUsado){
            throw new DomainException("E-mail already in use.");
        }
        return repository.save(cliente);
    }

    @Transactional
    public void deletar(Long id){
        repository.deleteById(id);
    }
}

package com.duckinc.patolog.api.controller;
import com.duckinc.patolog.domain.model.Cliente;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClienteController {

    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        Cliente cliente = new Cliente();
        List<Cliente> clienteList = new ArrayList<>();
        cliente.setId(1L);
        cliente.setEmail("email.com");
        cliente.setNome("Pedro3");
        cliente.setTelefone("40028922");

        clienteList.add(cliente);
        return clienteList;
    }

}


package com.duckinc.patolog.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
    private Long id;
    private String email;
    private String nome;
    private String telefone;
}

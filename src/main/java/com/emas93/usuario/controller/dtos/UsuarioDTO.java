package com.emas93.usuario.controller.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private String email;
    private String senha;
    private String nome;
    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;
}

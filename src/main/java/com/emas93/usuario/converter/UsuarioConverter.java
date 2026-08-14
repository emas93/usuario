package com.emas93.usuario.converter;

import com.emas93.usuario.controller.dtos.EnderecoDTO;
import com.emas93.usuario.controller.dtos.TelefoneDTO;
import com.emas93.usuario.controller.dtos.UsuarioDTO;
import com.emas93.usuario.infrastructure.entity.Endereco;
import com.emas93.usuario.infrastructure.entity.Telefone;
import com.emas93.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UsuarioConverter {
    //metodo para converter um usuarioDTO para um usuario
    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email((usuarioDTO.getEmail()))
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();

    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .bairro(enderecoDTO.getBairro())
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS){
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numeroTelefone((telefoneDTO.getNumeroTelefone()))
                .build();
    }


    //metodo para converter um usuario para um usuario DTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email((usuario.getEmail()))
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTOS(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDTOS(usuario.getTelefones()))
                .build();

    }

    public List<EnderecoDTO> paraListaEnderecoDTOS(List<Endereco> endereco) {
        return endereco.stream().map(this::paraEnderecoDTOS).toList();
    }

    public EnderecoDTO paraEnderecoDTOS(Endereco endereco) {
        return EnderecoDTO.builder()
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .bairro(endereco.getBairro())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTOS(List<Telefone> telefone){
        return telefone.stream().map(this::paraTelefoneDTOS).toList();
    }

    public TelefoneDTO paraTelefoneDTOS(Telefone telefone){
        return TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numeroTelefone((telefone.getNumeroTelefone()))
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuario){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome(): usuario.getNome() )
                .id(usuario.getId())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail(): usuario.getEmail())
                .senha(usuarioDTO.getSenha() !=null ? usuarioDTO.getSenha(): usuario.getSenha())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .build();

    }
}

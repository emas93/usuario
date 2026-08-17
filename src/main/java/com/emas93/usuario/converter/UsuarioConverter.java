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
                .id(enderecoDTO.getId())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .bairro(enderecoDTO.getBairro())
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .id(telefoneDTO.getId())
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
                .id(endereco.getId())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .bairro(endereco.getBairro())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTOS(List<Telefone> telefone) {
        return telefone.stream().map(this::paraTelefoneDTOS).toList();
    }

    public TelefoneDTO paraTelefoneDTOS(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numeroTelefone((telefone.getNumeroTelefone()))
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuario) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuario.getNome())
                .id(usuario.getId())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuario.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuario.getSenha())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .build();

    }

    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco endereco) {
        return Endereco.builder()
                .id(endereco.getId())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : endereco.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : endereco.getEstado())
                .logradouro(enderecoDTO.getLogradouro() != null ? enderecoDTO.getLogradouro() : endereco.getLogradouro())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : endereco.getNumero())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : endereco.getCep())
                .bairro(enderecoDTO.getBairro() != null ? enderecoDTO.getBairro() : endereco.getBairro())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : endereco.getComplemento())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone telefone) {
        return Telefone.builder()
                .id(telefone.getId())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefone.getDdd())
                .numeroTelefone(telefoneDTO.getNumeroTelefone() !=null ? telefoneDTO.getNumeroTelefone() : telefone.getNumeroTelefone())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO,Long idUsuario){
        return Endereco.builder()
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .bairro(enderecoDTO.getBairro())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .usuario_id(idUsuario)
                .build();
    }
    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO,Long idUsuario){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numeroTelefone(telefoneDTO.getNumeroTelefone())
                .usuario_id(idUsuario)
                .build();
    }

}

package com.emas93.usuario.business;

import com.emas93.usuario.controller.dtos.UsuarioDTO;
import com.emas93.usuario.converter.UsuarioConverter;
import com.emas93.usuario.infrastructure.entity.Usuario;
import com.emas93.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        // Recebe um usuarioDTO > converte para usuario entity, salva no repository como entity
        // e retorna para usuario DTO
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }




}

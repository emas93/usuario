package com.emas93.usuario.business;

import com.emas93.usuario.controller.dtos.UsuarioDTO;
import com.emas93.usuario.converter.UsuarioConverter;
import com.emas93.usuario.infrastructure.entity.Usuario;
import com.emas93.usuario.infrastructure.exceptions.ConflictException;
import com.emas93.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.emas93.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        // Recebe um usuarioDTO > converte para usuario entity, salva no repository como entity
        // e retorna para usuario DTO
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean emailExiste = verificaEmailExistente(email);
            if (emailExiste) {
                throw new ConflictException("E-mail já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("E-mail já cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }


    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado." + email));
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("E-mail não existe na base de dados. " + email));
        usuarioRepository.deleteByEmail(email);
    }


}




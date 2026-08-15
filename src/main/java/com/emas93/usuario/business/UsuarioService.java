package com.emas93.usuario.business;

import com.emas93.usuario.controller.dtos.EnderecoDTO;
import com.emas93.usuario.controller.dtos.TelefoneDTO;
import com.emas93.usuario.controller.dtos.UsuarioDTO;
import com.emas93.usuario.converter.UsuarioConverter;
import com.emas93.usuario.infrastructure.entity.Endereco;
import com.emas93.usuario.infrastructure.entity.Telefone;
import com.emas93.usuario.infrastructure.entity.Usuario;
import com.emas93.usuario.infrastructure.exceptions.ConflictException;
import com.emas93.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.emas93.usuario.infrastructure.repository.EnderecoRepository;
import com.emas93.usuario.infrastructure.repository.TelefoneRepository;
import com.emas93.usuario.infrastructure.repository.UsuarioRepository;
import com.emas93.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado." + email)));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("E-mail não existe na base de dados. " + email));
        usuarioRepository.deleteByEmail(email);
    }


    public UsuarioDTO alteraDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        //Buscando (extraindo) o usuário via token para não ter que passar o e-mail obrigatóriamente
        //e removendo o bearer.
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        //Criptografa a nova senha caso o usuário informe.
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null? passwordEncoder.encode(usuarioDTO.getSenha()): null);

        //Se encontrar, guarda o e-mail.se não encontrar o email do usuario lança a exceção.
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));
        //Guarda as informações de acordo com os campos que são informados ou não no body (regra no updateUsuario)
        Usuario usuarioEntity = usuarioConverter.updateUsuario(usuarioDTO, usuario);

        //Converte a entity para dto > para retornar no body
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    public EnderecoDTO alteraDadosEndereco(Long id,EnderecoDTO enderecoDTO){
        Endereco enderecoInformado = enderecoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " endereço não existe na base de dados."));
         enderecoInformado = usuarioConverter.updateEndereco(enderecoDTO,enderecoInformado);
        return usuarioConverter.paraEnderecoDTOS(enderecoRepository.save(enderecoInformado));
    }

    public TelefoneDTO alteraDadosTelefone(Long id, TelefoneDTO telefoneDTO){
        Telefone telefoneInformado = telefoneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " telefone não existe na base de dados."));
        telefoneInformado = usuarioConverter.updateTelefone(telefoneDTO,telefoneInformado);
        return usuarioConverter.paraTelefoneDTOS(telefoneRepository.save(telefoneInformado));
    }


}




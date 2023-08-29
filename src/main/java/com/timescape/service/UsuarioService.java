package com.timescape.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.timescape.model.Usuario;
import com.timescape.repository.UsuarioRepository;
import com.timescape.storage.StorageService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	@Autowired
	private final UsuarioRepository usuarioRepository;

	@Autowired
	private final StorageService storageService;

	@Autowired	
	private final PasswordEncoder encoder;
	
	public Usuario findById(Long idUsuario) {
		return usuarioRepository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException(String.format("Usuário não encontrado: %d", idUsuario)));
	}
	
	public List<Usuario> findAll() {
		return usuarioRepository.findAll(Sort.by(Order.asc("nome")));
	}
	
	public Page<Usuario> pagination(int page, int size, Optional<Usuario> usuario, Optional<Direction> direction, Optional<String> orderBy) {
		return usuarioRepository.findAll(Example.of(usuario.orElse(new Usuario())), PageRequest.of(page, size, Sort.by(direction.orElse(Direction.ASC), orderBy.orElse("nome"))));
	}
	
	public Usuario save(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> save(List<Usuario> usuarios) {
		return usuarioRepository.saveAll(usuarios.stream().map(usuario -> {
			usuario.setSenha(encoder.encode(usuario.getSenha()));
			return usuario;
		}).toList());
	}
	
	public Usuario update(Long idUsuario, Usuario usuario) {
		Usuario entity = findById(idUsuario);
		entity.setNome(usuario.getNome());
		entity.setGenero(usuario.getGenero());
		entity.setDataNascimento(usuario.getDataNascimento());
		entity.setEmail(usuario.getEmail());
		return usuarioRepository.save(entity);
	}
	
	public Usuario updatePassword(Long idUsuario, Usuario usuario) {
		Usuario entity = findById(idUsuario);
		entity.setSenha(encoder.encode(usuario.getSenha()));
		return usuarioRepository.save(entity);
	}
	
	public Usuario updatePhoto(Long idUsuario, @RequestParam("fotoPefil") MultipartFile file) {
		Usuario usuario = findById(idUsuario);
		usuario.setFotoPerfil(storageService.store(file));
		return usuarioRepository.save(usuario);
	}
	
	public void delete(Long idUsuario) {
		Usuario usuario = findById(idUsuario);
		usuarioRepository.delete(usuario);
		if (Objects.nonNull(usuario.getFotoPerfil())) {
			storageService.delete(usuario.getFotoPerfil());
		}
	}
}

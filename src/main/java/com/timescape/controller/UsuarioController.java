package com.timescape.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.timescape.model.Usuario;
import com.timescape.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private final UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		return ResponseEntity.ok(usuarioService.findAll());
	}

	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody @Valid Usuario usuario) {
		return ResponseEntity.ok(usuarioService.save(usuario));
	}

	@PostMapping("/lista")
	public ResponseEntity<List<Usuario>> save(@RequestBody @Valid List<Usuario> usuarios) {
		return ResponseEntity.ok(usuarioService.save(usuarios));
	}

	@PutMapping("/{idUsuario}")
	public ResponseEntity<Usuario> update(@PathVariable Long idUsuario, @RequestBody @Valid Usuario usuario) {
		return ResponseEntity.ok(usuarioService.update(idUsuario, usuario));
	}
	
	@PatchMapping("/senha/{idUsuario}")
	public ResponseEntity<Usuario> updatePassword(@PathVariable Long idUsuario, @RequestBody @Valid Usuario usuario) {
		return ResponseEntity.ok(usuarioService.update(idUsuario, usuario));
	}
	
	@PatchMapping("/fotoPerfil/{idUsuario}")
	public ResponseEntity<Usuario> updatePhoto(@PathVariable Long idUsuario, @RequestParam("fotoPerfil") MultipartFile file) {
		return ResponseEntity.ok(usuarioService.updatePhoto(idUsuario, file));
	}

	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Void> delete(@PathVariable Long idUsuario) {
		usuarioService.delete(idUsuario);
		return ResponseEntity.ok().build();
	}
}

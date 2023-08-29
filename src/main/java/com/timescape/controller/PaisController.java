package com.timescape.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.timescape.model.Pais;
import com.timescape.service.PaisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pais")
@RequiredArgsConstructor
public class PaisController {
	
	@Autowired
	private final PaisService paisService;
	
	@GetMapping
	public ResponseEntity<List<Pais>> findAll() {
		return ResponseEntity.ok(paisService.findAll());
	}
	
	@GetMapping("/{idPais}")
	public ResponseEntity<Pais> findById(@PathVariable Long idPais) {
		return ResponseEntity.ok(paisService.findById(idPais));
	}
	
	@GetMapping("/paginacao")
	public ResponseEntity<Page<Pais>> pagination(
			@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam Map<String, String> map, 
			@RequestParam("orderBy") Optional<String> orderBy, @RequestParam("direction") Optional<Direction> direction) {
		return ResponseEntity.ok(paisService.pagination(
				page, 
				size, 
				Pais.builder()
				.nome(map.get("nome"))
				.codigo(map.get("codigo"))
				.telefone(map.get("telefone"))
				.build(), 
				direction, 
				orderBy
		));
	}

	@PostMapping
	public ResponseEntity<Pais> save(@RequestBody @Valid Pais pais) {
		return ResponseEntity.ok(paisService.save(pais));
	}

	@PostMapping("/lista")
	public ResponseEntity<List<Pais>> save(@RequestBody List<Pais> paises) {
		return ResponseEntity.ok(paisService.saveAll(paises));
	}

	@PutMapping("/{idPais}")
	public ResponseEntity<Pais> update(@PathVariable Long idPais, @RequestBody @Valid Pais pais) {
		return ResponseEntity.ok(paisService.update(idPais, pais));
	}

	@DeleteMapping("/{idPais}")
	public ResponseEntity<Void> update(@PathVariable Long idPais) {
		paisService.delete(idPais);
		return ResponseEntity.ok().build();
	}	
}

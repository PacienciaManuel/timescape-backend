package com.timescape.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.timescape.model.Pais;
import com.timescape.repository.PaisRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaisService {

	@Autowired
	private final PaisRepository paisRepository;

	public Pais findById(Long idPais) {
		return paisRepository.findById(idPais).orElseThrow(() -> new EntityNotFoundException(String.format("Pais não encontrado: %d", idPais)));
	}

	public List<Pais> findAll() {
		return paisRepository.findAll(Sort.by(Order.asc("nome")));
	}
	
	public Page<Pais> pagination(int page, int size, Pais pais, Optional<Direction> direction, Optional<String> orderBy) {
		return paisRepository.findAll(
			Example.of(
				pais, 
				ExampleMatcher.matching()
				.withMatcher("nome", match -> match.contains().ignoreCase())
				.withMatcher("codigo", match -> match.contains().ignoreCase())
				.withMatcher("telefone", match -> match.contains().ignoreCase())
			), 
			PageRequest.of(page, size, Sort.by(direction.orElse(Direction.ASC), orderBy.orElse("nome")))
		);
	}
	
	public Pais save(Pais pais) {
		return paisRepository.save(pais);
	}
	
	public List<Pais> saveAll(List<Pais> paises) {
		return paisRepository.saveAll(paises);
	}
	
	public Pais update(Long idPais, Pais pais) {
		Pais entity = this.findById(idPais);
		entity.setNome(pais.getNome());
		entity.setCodigo(pais.getCodigo());
		entity.setTelefone(pais.getTelefone());
		return paisRepository.save(entity);
	}
	
	public void delete(Long idPais) {
		paisRepository.delete(findById(idPais));
	}	
}

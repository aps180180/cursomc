package com.andresantos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andresantos.cursomc.domain.Categoria;
import com.andresantos.cursomc.repositories.CategoriaRepository;
import com.andresantos.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find( Integer id) {
		Optional <Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}
	
	public Categoria insert (Categoria obj) {
		obj.setId(null); // para garantir que estou inserindo sempre uma categoria nova
		return repo.save(obj);
	}
	
	public Categoria update (Categoria obj) {
		find(obj.getId()); // chamo o metodo find para buscar o objeto no banco
		return repo.save(obj);
	}
	
}

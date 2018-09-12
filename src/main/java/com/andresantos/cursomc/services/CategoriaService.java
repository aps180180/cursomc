package com.andresantos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andresantos.cursomc.domain.Categoria;
import com.andresantos.cursomc.repositories.CategoriaRepository;
import com.andresantos.cursomc.services.exceptions.DataIntegrityException;
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
	
	public void delete (Integer id) {
		find(id); // verifica se o objeto existe antes de deletar
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page <Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	
}

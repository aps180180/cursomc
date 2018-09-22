package com.andresantos.cursomc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.andresantos.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{
	
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	
	// aqui no repository se declarar o metodo acima, o spring gera um metodo de busca findBy<campo>
	
}

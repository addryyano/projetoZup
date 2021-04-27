package com.adrianogomes.projetozup.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrianogomes.projetozup.domain.Usuario;
import com.adrianogomes.projetozup.repositories.UsuarioRepository;
import com.adrianogomes.projetozup.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));		
	}

}

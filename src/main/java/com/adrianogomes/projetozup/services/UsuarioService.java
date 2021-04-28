package com.adrianogomes.projetozup.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrianogomes.projetozup.domain.Cidade;
import com.adrianogomes.projetozup.domain.Usuario;
import com.adrianogomes.projetozup.domain.Endereco;
import com.adrianogomes.projetozup.dto.UsuarioDTO;
import com.adrianogomes.projetozup.dto.UsuarioNewDTO;
import com.adrianogomes.projetozup.repositories.UsuarioRepository;
import com.adrianogomes.projetozup.repositories.EnderecoRepository;
//import com.adrianogomes.projetozup.services.exceptions.DataIntegrityException;
import com.adrianogomes.projetozup.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Usuário não encontrado! Id: " + id + "... Por favor verifique o Id e tente novamente." ));
	}
	
	@Transactional
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
/*
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
*/
	
	public List<Usuario> findAll() {
		return repo.findAll();
	}
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		Usuario user = new Usuario(null, objDto.getNome(), objDto.getEmail(), objDto.getCpf(), objDto.getNascimento());
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), user, cid);
		user.getEnderecos().add(end);
		user.getTelefones().add(objDto.getTelefone());
/*		
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
*/			
		return user;
	}
	
	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}

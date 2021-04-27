package com.adrianogomes.projetozup;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adrianogomes.projetozup.domain.Categoria;
import com.adrianogomes.projetozup.domain.Cidade;
import com.adrianogomes.projetozup.domain.Usuario;
import com.adrianogomes.projetozup.domain.Endereco;
import com.adrianogomes.projetozup.domain.Estado;
import com.adrianogomes.projetozup.domain.Produto;
import com.adrianogomes.projetozup.repositories.CategoriaRepository;
import com.adrianogomes.projetozup.repositories.CidadeRepository;
import com.adrianogomes.projetozup.repositories.UsuarioRepository;
import com.adrianogomes.projetozup.repositories.EnderecoRepository;
import com.adrianogomes.projetozup.repositories.EstadoRepository;
import com.adrianogomes.projetozup.repositories.ProdutoRepository;

@SpringBootApplication
public class ZupApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ZupApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica", "01");
		Categoria cat2 = new Categoria(null, "Escritorio", "02");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "MG");
		Estado est2 = new Estado(null, "SP");
		
		Cidade c1 = new Cidade(null, "Belo Horizonte", est1);
		Cidade c2 = new Cidade(null, "Campinas", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est2);
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Usuario user1 = new Usuario(null, "Maria Silva", "maria@emailteste.com", "36378912377", "21/03/1986");
		user1.getTelefones().addAll(Arrays.asList("1999887766", "1199775533"));
		
		Endereco e1 = new Endereco(null, "Rua Flores Melo", "300", "Ap102", "Jardim Nevada", "3132322323", user1, c1);
		Endereco e2 = new Endereco(null, "Avenida Bento Dias", "1001", "Sala 21", "Centro", "1921122112", user1, c2);
		user1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		usuarioRepository.saveAll(Arrays.asList(user1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
	}
}

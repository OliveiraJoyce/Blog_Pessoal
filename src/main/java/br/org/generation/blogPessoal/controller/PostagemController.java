package br.org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.blogPessoal.model.Postagem;
import br.org.generation.blogPessoal.repository.PostagemRepository;
import br.org.generation.blogPessoal.service.PostagemService;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;
	
	@Autowired
	private PostagemService postagemService;

	// essa anotação/metodo retorna tudo que tem no banco de dados por isso é o
	// findAll
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	// Essa possibilita a pesquisa por id exemplo http://localhost:8080/postagens/2
	// e caso não seja encontrado retorna 404-notfound
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	// esse metodo permite pesquisar pela discrição como se fosse o Like no SQL.
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}

	// esse metodo insere postagens no banco de dados.
	@PostMapping
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}

	// esse metodo faz alterações nas postagens.
	@PutMapping
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem) {
		Optional<Postagem> postagemUpdate = repository.findById(postagem.getId());

		if (postagemUpdate.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada!", null);
		}

	}

	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		
		Optional<Postagem> postagem = repository.findById(id);
		
		if (postagem.isPresent()) {
			repository.deleteById(id);
		}else{
			throw new ResponseStatusException(
		          	HttpStatus.NOT_FOUND, "Postagem não encontrada!", null);
		}
	}
	@PutMapping("/curtir/{id}")
	public ResponseEntity<Postagem> putCurtirPostagemId (@PathVariable Long id){
		
		return ResponseEntity.status(HttpStatus.OK).body(postagemService.curtir(id));
	
	}

	@PutMapping("/descurtir/{id}")
	public ResponseEntity<Postagem> putDescurtirPostagemId (@PathVariable Long id){
		
		return ResponseEntity.status(HttpStatus.OK).body(postagemService.descurtir(id));
	
	}
	
}	
	
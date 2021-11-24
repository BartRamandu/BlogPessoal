package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(value = "*", allowedHeaders = "*")
@Api(tags = "Postagem Controller API", description = "Postagem features")
public class PostagemController {
	
	@Autowired
	private PostagemRepository repository;
	
	@ApiOperation(value = "Get All Postagens")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All postagens!"),
	})
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@ApiOperation(value = "Get Postagens By Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Postagem by Id!"),
			@ApiResponse(code = 400, message = "Request error, invalid id!")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Get Title")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Title Postagem!"),
			@ApiResponse(code = 400, message = "Request error, invalid title!")
	})
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@ApiOperation(value = "Create Postagem")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "New post created on the system.")
	})
	@PostMapping
	public ResponseEntity<Postagem> post (@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@ApiOperation(value = "Update Postagem")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Update postagem on the system."),
			@ApiResponse(code = 400, message = "Request error, invalid id!")
	})
	@PutMapping
	public ResponseEntity<Postagem> put (@Valid @RequestBody Postagem postagem){
		
		return repository.findById(postagem.getId())
		        .map(resp -> ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem)))
		        .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@ApiOperation(value = "Delete Postagem")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Deleted postagem"),
			@ApiResponse(code = 400, message = "Request error, invalid idPostagem!")
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {

	        Optional<Postagem> post = repository.findById(id);
	        
	        if(post.isEmpty())
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	        
	        repository.deleteById(id);				
	}
}
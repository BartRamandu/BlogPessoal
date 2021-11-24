package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.TemaRepository;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
@Api(tags = "Theme Controller API", description = "Theme features")
public class TemaController {
	
	@Autowired
	private TemaRepository repository;
	
	@ApiOperation(value = "Get All Themes")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All themes!"),
	})
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@ApiOperation(value = "Get Themes By Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Themes by Id!"),
			@ApiResponse(code = 400, message = "Request error, invalid id!")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Get Theme by Name")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Theme by name!"),
			@ApiResponse(code = 400, message = "Request error, invalid name!")
	})
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> getByName(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(nome));
	}
	
	@ApiOperation(value = "Create Theme")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "New theme created on the system."),
	})
	@PostMapping
	public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}
	
	@ApiOperation(value = "Update Theme")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Update theme on the system."),
			@ApiResponse(code = 400, message = "Request error, invalid id!")
	})
	@PutMapping
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
		
		return repository.findById(tema.getId())
		        .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema)))
		        .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@ApiOperation(value = "Delete Theme")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Deleted theme"),
			@ApiResponse(code = 400, message = "Request error, invalid idTheme!")
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {

	        Optional<Tema> tema = repository.findById(id);
	        if(tema.isEmpty())
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	        repository.deleteById(id);				
	}
}
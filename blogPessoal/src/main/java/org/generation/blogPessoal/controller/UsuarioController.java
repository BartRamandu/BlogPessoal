package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "User Controller API", description = "User utilities")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository repository;

	@ApiOperation(value = "Get All Users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All users!"),
	})
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {

		return ResponseEntity.ok(repository.findAll());
	}

	@ApiOperation(value = "Get User By Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User by Id!"),
			@ApiResponse(code = 400, message = "Request error, invalid id!")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable long id) {

		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Create User")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "New user created on the system."),
			@ApiResponse(code = 400, message = "Request error, email already in use!")
	})
	@PostMapping("/cadastrar")
	public ResponseEntity<Optional<Usuario>> post(@Valid @RequestBody Usuario usuario) {

		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuario));
	}
	
	@ApiOperation(value = "User Login")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "User Acess!"),
			@ApiResponse(code = 400, message = "Request error, invalid email or password!")
	})
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@Valid @RequestBody Optional<UserLogin> user) {
		return usuarioService.logarUsuario(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@ApiOperation(value = "Update User")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "User Credentials!"),
			@ApiResponse(code = 400, message = "Request error, invalid idUser!")
	})
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {

		return usuarioService.atualizarUsuario(usuario).map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}	
}
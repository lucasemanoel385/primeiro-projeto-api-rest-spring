package videos.pic.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import videos.pic.api.categoria.AtualizarDadosCategoria;
import videos.pic.api.categoria.CadastrarCategoria;
import videos.pic.api.categoria.CategoriaRepository;
import videos.pic.api.categoria.DadosDeCategoriaEVideo;
import videos.pic.api.categoria.DetalhesCategoria;
import videos.pic.api.categoria.ListarCategoria;
import videos.pic.api.categoria.service.CategoriaService;
import videos.pic.api.domain.usuario.Usuario;
import videos.pic.api.domain.usuario.UsuarioRepository;
import videos.pic.api.infra.security.SecurityFilter;
import videos.pic.api.infra.security.TokenService;

@RestController
@RequestMapping("categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private UsuarioRepository repositoryUser;
	
	@Autowired
	private CategoriaService service;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private SecurityFilter security;
	
	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid CadastrarCategoria dados, UriComponentsBuilder uriBuilder) {
		
		var categoriaAdd = service.cadastrarCategoria(dados);
		
		var uri = uriBuilder.path("/categoria/{id}").buildAndExpand(categoriaAdd.getId()).toUri();
		return ResponseEntity.created(uri).body(new DetalhesCategoria(categoriaAdd));
		
	}
	
	@GetMapping
	public ResponseEntity<Page<ListarCategoria>> listar(@PageableDefault(page = 0, sort = "id" ,direction = Direction.ASC ,size = 5) Pageable paginacao) {
		
		var lista = service.listarCategoria(paginacao);
		return ResponseEntity.ok(lista);
		
	}
	
	@GetMapping("/{id}/videos")
	public ResponseEntity<Page<DadosDeCategoriaEVideo>> mostrarVideo(@PathVariable Long id, Pageable page) {
		
		service.validarId(id, "Categoria não encontrada");
		
		var pagee = repository.findAllById(id, page).map(DadosDeCategoriaEVideo::new);
		return ResponseEntity.ok(pagee);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity listar(@PathVariable Long id) {
		
		var categoria = service.listarCategoriaPorId(id);
		return ResponseEntity.ok(new DetalhesCategoria(categoria));
	}
	
	@PutMapping("/att")
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid AtualizarDadosCategoria dados) {
		
		var categoria = service.atualizarCategoria(dados);
		return ResponseEntity.ok(new DetalhesCategoria(categoria));
		
	}
	
	@DeleteMapping("/del/{id}")
	@Transactional
	public ResponseEntity<String> deletarCategoria(@PathVariable Long id, HttpServletRequest request) {
		
		service.validarId(id,"Categoria invalida");

		repository.deleteById(id);
		
		return new ResponseEntity<>("Deletado com sucesso", HttpStatusCode.valueOf(202));                   //noContent().eTag("Deletado com sucesso").build();
		//new ResponseEntity<>("Deletado com sucesso", HttpStatus.ACCEPTED);  	
	}


}

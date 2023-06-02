package videos.pic.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import videos.pic.api.domain.ValidacaoException;
import videos.pic.api.domain.categoria.Categoria;
import videos.pic.api.domain.categoria.CategoriaRepository;
import videos.pic.api.domain.videos.AtualizarDadosVideos;
import videos.pic.api.domain.videos.CadastroVideo;
import videos.pic.api.domain.videos.DadosDetalhadosVideo;
import videos.pic.api.domain.videos.VideoExclusao;
import videos.pic.api.domain.videos.Videos;
import videos.pic.api.domain.videos.VideosRepository;
import videos.pic.api.domain.videos.services.VideoService;

@RestController
@RequestMapping("{videos}")
public class VideosController {
	
	@Autowired
	private VideosRepository repository;
	@Autowired
	private VideoService videoService;
	
	
	@Transactional
	@PostMapping
	public ResponseEntity cadastrar(@RequestBody @Valid CadastroVideo dados, UriComponentsBuilder uriBuilder) {

		//return videoService.cadastrarVideo(dados, uriBuilder);
	
				
		var cadastroVideo = new Videos(dados);
		videoService.cadastrarVideo(cadastroVideo);
		var uri = uriBuilder.path("/videos/{}").buildAndExpand(cadastroVideo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhadosVideo(cadastroVideo));
		
		
	}
	
	@GetMapping												
	public ResponseEntity<Page<DadosDetalhadosVideo>> mostrarVideo(@PageableDefault(page = 0, sort = "titulo" ,direction = Direction.ASC) Pageable page) {
		
		//var pagee = repository.findAll(page).map(DadosDetalhadosVideo::new);
		
		var pagee = videoService.listarVideos(page);
		
		return ResponseEntity.ok(pagee);
		
	}
	
	@GetMapping("/")										//requisição no parametro opcional por causa do required false
	public ResponseEntity<Page<DadosDetalhadosVideo>> mostrarVideoEspecificoPeloParametro(@RequestParam(required = true) String titulo, Pageable page) {
		
		//Contains serve pra executar uma pesquisa de texto completo do SQL Server em colunas indexadas de texto completo que contêm tipos de dados baseados em caracteres.
		//var pagee = repository.findByTituloContains(search, page).map(DadosDetalhadosVideo::new);
		
		var pagee = videoService.mostrarVideoPeloParametro(titulo, page);
		return ResponseEntity.ok(pagee);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity mostrarVideoEspecifico(@PathVariable Long id) {
		videoService.validarId(id);
		
		var video = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhadosVideo(video));
	}
	
	
	@PutMapping("/att")
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid AtualizarDadosVideos dados) {
		var video = videoService.atualizarVideo(dados);
		return ResponseEntity.ok(new DadosDetalhadosVideo(video));
		//return repository.findById(dados.id()).stream().map(MostrarVideos::new).toList();
	}
	
	
	@DeleteMapping("/del/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		videoService.excluir(id);
		String mensagem = "Video id = " + id + " deletado com sucesso";
		return ResponseEntity.ok(new VideoExclusao(mensagem));
	}

	@GetMapping("/free")											
	public ResponseEntity<Page<DadosDetalhadosVideo>> listaVideosFree(@PageableDefault(page = 0, size = 2, sort = "id" ,direction = Direction.ASC)Pageable page) {
		
		//var pagee = repository.findAll(page).map(DadosDetalhadosVideo::new);
		Page<DadosDetalhadosVideo> pagee = videoService.listarVideosFree(page);
		
		return ResponseEntity.ok(pagee);
		
	}
	
}

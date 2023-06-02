package videos.pic.api.domain.videos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import videos.pic.api.domain.ValidacaoException;
import videos.pic.api.domain.categoria.CategoriaRepository;
import videos.pic.api.domain.videos.AtualizarDadosVideos;
import videos.pic.api.domain.videos.DadosDetalhadosVideo;
import videos.pic.api.domain.videos.Videos;
import videos.pic.api.domain.videos.VideosRepository;
import videos.pic.api.domain.videos.validations.TodosCamposValidosAtualizar;

@Service
public class VideoService {
	@Autowired
	private VideosRepository repository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private TodosCamposValidosAtualizar validarAtt;
	
	public boolean validarId(Long id) {
		var validar = repository.existsById(id);
		if(!validar) {
			throw new ValidacaoException("Id não encontrado");
		}
		return validar;
	}
	
	public Videos cadastrarVideo(@RequestBody @Valid Videos video) {
//		var validarCategoria = categoriaRepository.existsById(video.getCategoria().getId());
//		if (!validarCategoria) {
//			throw new ValidacaoException("Categoria não existe");
//		}
		//validadores.forEach(v -> v.validar(video));
		var t = categoriaRepository.existsById(video.getCategoria().getId());
		if (!t) {
			throw new ValidacaoException("Essa categoria não existe");
		}
		var v = repository.save(video);
		var videoo = repository.getReferenceById(v.getId());
		return videoo;

		//return ResponseEntity.created(uri).body(new DadosDetalhadosVideo(cadastroVideo));
	}
	
	public Videos atualizarVideo(@RequestBody @Valid AtualizarDadosVideos dados) {
		validarId(dados.id());
		var video = repository.getReferenceById(dados.id());
		validarAtt.validarAtualizacao(dados);
		//video.atualizarDadosVideo(dados);
		return video;

	}
	
	public void excluir(@PathVariable Long id) {
		validarId(id);
		repository.deleteById(id);
	}
	
											
	public Page<DadosDetalhadosVideo> listarVideos(Pageable page) {
		var v = repository.findAll(page).map(DadosDetalhadosVideo::new);
		
		return v;
		//return v.getContent();

	}
	
	public Page<DadosDetalhadosVideo> mostrarVideoPeloParametro(String titulo, Pageable page) {
		
		//Contains serve pra executar uma pesquisa de texto completo do SQL Server em colunas indexadas de texto completo que contêm tipos de dados baseados em caracteres.
		var pagee = repository.findByTituloContains(titulo, page).map(DadosDetalhadosVideo::new);
		
		return pagee;
		
	}
	
	public Page<DadosDetalhadosVideo> listarVideosFree(Pageable page) {
		var v = repository.findAll(page).map(DadosDetalhadosVideo::new);
		
		return v;
		//return v.getContent();

	}
	
}

package videos.pic.api.categoria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import videos.pic.api.categoria.AtualizarDadosCategoria;
import videos.pic.api.categoria.CadastrarCategoria;
import videos.pic.api.categoria.Categoria;
import videos.pic.api.categoria.CategoriaRepository;
import videos.pic.api.categoria.ListarCategoria;
import videos.pic.api.domain.ValidacaoException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public void validarId(Long id, String msg) {
		var c = repository.existsById(id);
		if (!c) {
			throw new ValidacaoException(msg);
		}
	}
	
	public Categoria cadastrarCategoria(CadastrarCategoria dados) {
		var categoria = new Categoria(1L ,"LIVRE", "#FFFFFF");
		repository.save(categoria);
		
		var categoriaAdd = new Categoria(dados);
		repository.save(new Categoria(dados));
		
		return categoriaAdd;
	}
	
	public Categoria listarCategoriaPorId(Long id) {
		validarId(id, "Categoria não encontrada");
		return repository.getReferenceById(id);
		
	}
	
	public Categoria atualizarCategoria(AtualizarDadosCategoria dados) {
		var categoria = repository.getReferenceById(dados.id());
		categoria.atualizarDadosCategoria(dados);
		
		return categoria;
	}
	
	public Page<ListarCategoria> listarCategoria(Pageable page) {
		var lista = repository.findAll(page).map(ListarCategoria::new);
		return lista;

	}
	
	

}

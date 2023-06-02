package videos.pic.api.domain.categoria;

import java.util.List;

import videos.pic.api.domain.videos.Videos;

public record ListarCategoria(Long id, String nome) {
	
	public ListarCategoria(Categoria categoria) {
		this(categoria.getId(), categoria.getNome());
	}



}

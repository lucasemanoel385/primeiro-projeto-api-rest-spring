package videos.pic.api.categoria;

import java.util.List;

import videos.pic.api.videos.Videos;

public record ListarCategoria(Long id, String nome) {
	
	public ListarCategoria(Categoria categoria) {
		this(categoria.getId(), categoria.getNome());
	}



}

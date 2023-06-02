package videos.pic.api.videos;

import java.awt.print.Pageable;

import org.springframework.data.domain.PageRequest;

import videos.pic.api.categoria.Categoria;

public record MostrarVideos(Long id, String titulo, String descricao, String url, Categoria categoria) {
	
	public MostrarVideos(Videos video) {
		this(video.getId(), 
				video.getTitulo(), 
				video.getDescricao(), 
				video.getUrl(), 
				video.getCategoria());
	}


}

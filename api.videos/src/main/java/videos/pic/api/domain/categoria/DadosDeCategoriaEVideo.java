package videos.pic.api.domain.categoria;

import java.util.List;

public record DadosDeCategoriaEVideo(List videos) {
	
	public DadosDeCategoriaEVideo(Categoria categoria) {
		this(categoria.getVideo());
	}

}

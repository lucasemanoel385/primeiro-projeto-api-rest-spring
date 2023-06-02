package videos.pic.api.videos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import videos.pic.api.categoria.Categoria;

public record AtualizarDadosVideos(
		@NotNull
		Long id, 
		String titulo, 
		String descricao, 
		String url,
		Categoria categoria) {

	

}

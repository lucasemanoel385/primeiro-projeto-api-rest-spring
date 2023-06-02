package videos.pic.api.categoria;

import org.hibernate.annotations.ColumnDefault;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import videos.pic.api.videos.Videos;

public record CadastrarCategoria(
		@NotEmpty(message = "O nome é obrigatório")
		String nome,
		@NotEmpty(message = "A cor é obrigatória")
		String cor) {

}

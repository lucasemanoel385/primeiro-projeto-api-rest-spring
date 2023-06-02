package videos.pic.api.domain.videos;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import videos.pic.api.domain.categoria.Categoria;


public record CadastroVideo(
		@NotEmpty(message = "titulo obrigatorio")
		String titulo,
		@NotEmpty(message = "descricao obrigatoria")
		String descricao,
		@NotBlank
		@URL(message = "url invalida")
		String url,
		Long categoria) {
	
	public CadastroVideo(String titulo, 
			String descricao,
			String url,
			Long categoria) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.url = url;
		this.categoria = verificar(categoria);
		}
	
	
	public Long verificar(Long categoria) {
		int numero;
		
		if (categoria == null) {
			numero = 1;
			return (long) numero;
		}
		
		return categoria;
	}
	

}

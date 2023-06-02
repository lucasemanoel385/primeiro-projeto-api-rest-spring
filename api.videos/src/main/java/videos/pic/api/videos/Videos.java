package videos.pic.api.videos;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import videos.pic.api.categoria.Categoria;
import videos.pic.api.categoria.CategoriaRepository;
import videos.pic.api.domain.ValidacaoException;

@Table(name = "tb_videos")
@Entity(name = "Videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Videos {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	public Videos(CadastroVideo dados) {
		this.titulo = dados.titulo();
		this.descricao = dados.descricao();
		this.url = dados.url();
		this.categoria = new Categoria(dados.categoria());
		
		
	}
	
	public Videos(String titulo, String descricao, String url, Long id) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.url = url;
		this.categoria = new Categoria(id);

	}
		
}
	

	



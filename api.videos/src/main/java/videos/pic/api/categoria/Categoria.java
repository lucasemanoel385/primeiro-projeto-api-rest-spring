package videos.pic.api.categoria;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import videos.pic.api.videos.Videos;

@Entity(name = "Categoria")
@Table(name = "tb_categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cor;
	
	@JsonIgnore
	@OneToMany(mappedBy = "categoria")
	private List<Videos> video = new ArrayList<>();
	
	public Categoria(Long id) {
		
		this.id = id;
	}
	
	public Categoria(Long id, String nome, String cor) {
		
		this.id = id;
		this.nome = nome;
		this.cor = cor;
	}
	
	public Categoria(CadastrarCategoria dados) {
		
		this.nome = dados.nome();
		this.cor = dados.cor();
	}
	
	public void atualizarDadosCategoria(@Valid AtualizarDadosCategoria dados) {
		
		if(dados.id() != null) {
			this.nome = dados.nome();
		}
		if(dados.id() != null) {
			this.cor = dados.cor();
		}
		
		
	}
	

}

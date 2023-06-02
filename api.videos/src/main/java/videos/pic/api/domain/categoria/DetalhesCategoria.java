package videos.pic.api.domain.categoria;

public record DetalhesCategoria(String nome, String cor) {
	
	public DetalhesCategoria(Categoria categoria) {
		this(categoria.getNome(), categoria.getCor());
	}

}

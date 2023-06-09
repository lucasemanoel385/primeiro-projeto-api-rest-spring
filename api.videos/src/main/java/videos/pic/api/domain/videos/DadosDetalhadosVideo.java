package videos.pic.api.domain.videos;

import videos.pic.api.domain.categoria.Categoria;

public record DadosDetalhadosVideo(Long id, String nomeCategoria, String titulo, String descricao, String url) {
	
	public DadosDetalhadosVideo(Videos video) {
		this(video.getId(), video.getCategoria().getNome(), video.getTitulo(), video.getDescricao(), video.getUrl());
	}

}

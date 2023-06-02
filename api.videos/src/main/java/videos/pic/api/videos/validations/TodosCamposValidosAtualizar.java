package videos.pic.api.videos.validations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import videos.pic.api.categoria.Categoria;
import videos.pic.api.domain.ValidacaoException;
import videos.pic.api.videos.AtualizarDadosVideos;
import videos.pic.api.videos.Videos;
import videos.pic.api.videos.VideosRepository;
@Order(value = 1)  //define a ordem de classificação de um componente ou bean anotado.
@Component
public class TodosCamposValidosAtualizar {
	
	@Autowired
	private VideosRepository repository;
	
	public void validarAtualizacao(@Valid AtualizarDadosVideos dad) {
		
		var videos = repository.getReferenceById(dad.id());
		
        if (videos == null) {
            String mensagem = "video nulo";
            throw new IllegalArgumentException(mensagem);
        }
       validarCampos(dad, videos);
    }
	
    private void validarCampos(AtualizarDadosVideos dados, Videos videos) {
        String titulo    = dados.titulo();
        String descricao = dados.descricao();
        String url       = dados.url();
        Categoria categoriaId = dados.categoria();
        
        if(titulo != null && titulo != "") {
			videos.setTitulo(titulo);
		}
        if(descricao != null && descricao != "") {
			videos.setDescricao(descricao);
		}
        if(url != null && url != "") {
			videos.setUrl(url);
		}
		
        if(categoriaId != null) {
			videos.setCategoria(dados.categoria());
		}
    }

}

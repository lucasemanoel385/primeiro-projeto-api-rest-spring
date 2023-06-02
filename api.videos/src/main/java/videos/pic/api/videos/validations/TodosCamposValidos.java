package videos.pic.api.videos.validations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import videos.pic.api.domain.ValidacaoException;
import videos.pic.api.videos.Videos;
@Order(value = 1)  //define a ordem de classificação (quem vem primeiro) de um componente ou bean anotado.
@Component
public class TodosCamposValidos implements ValidacoesVideo {
	
	public void validar(Videos video) {
        if (video == null) {
            String mensagem = "video nulo";
            throw new IllegalArgumentException(mensagem);
        }

        List<String> errosDeValidacao = validarCampos(video);
        if (!errosDeValidacao.isEmpty()) {
            String mensagem = "preenchimento invalido do video, validacoes = " + errosDeValidacao;
            throw new ValidacaoException(mensagem);
        }
    }

    private List<String> validarCampos(Videos video) {
        List<String> errosDeValidacao = new ArrayList<>();

        String titulo    = video.getTitulo();
        String descricao = video.getDescricao();
        String url       = video.getUrl();

        if (StringUtils.isEmpty(titulo))
            errosDeValidacao.add("titulo vazio");

        if (StringUtils.isEmpty(descricao))
            errosDeValidacao.add("descricao vazia");

        if (StringUtils.isEmpty(url))
            errosDeValidacao.add("url vazio");

        return errosDeValidacao;
    }

}

package videos.pic.api.categoria;

import jakarta.validation.constraints.NotNull;

public record AtualizarDadosCategoria(@NotNull Long id ,String nome, String cor) {

}

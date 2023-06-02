package videos.pic.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import videos.pic.api.domain.categoria.CadastrarCategoria;
import videos.pic.api.domain.categoria.Categoria;
import videos.pic.api.domain.categoria.service.CategoriaService;
import videos.pic.api.domain.videos.CadastroVideo;
import videos.pic.api.domain.videos.Videos;

@SpringBootTest //Pra testar um controller no spring
@AutoConfigureMockMvc //Serve pra injetar o mockmvc na classe junto com o autowired
@AutoConfigureJsonTesters //Pro spring rodar o jacksonTesters
public class CategoriaControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<CadastrarCategoria> dadosCadastroJson;
	
	@MockBean
	private CategoriaService service;
	
	@Test
	@DisplayName("Deveria devolver status code 201")
	@WithMockUser
	void cadastrar_categoria1() throws Exception {
		var cadastro = new CadastrarCategoria("cat", "#FERTES");
		var toJson = dadosCadastroJson.write(cadastro).getJson();
		when(service.cadastrarCategoria(any())).thenReturn(new Categoria(cadastro));
		
		//Dispara uma requisição post de cadastro de categoria sem retorno body
		var response = mvc.perform(
									MockMvcRequestBuilders.post("/categoria")
									//.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON)
									.content(toJson))
									//.andDo(print());
				//pega o resulta e o response, joga numa variavel 
									.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		//verify(service, times(1)).cadastrarVideo(new Videos(cadastro));
	}
	
	@Test
	@DisplayName("Deveria devolver status code 200")
	@WithMockUser
	void listarCategorias_categoria2() throws Exception {
		
		var response = mvc.perform(
									MockMvcRequestBuilders.get("/categoria"))
									.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		//verify(service, times(1)).cadastrarVideo(new Videos(cadastro));
	}
}
	
	

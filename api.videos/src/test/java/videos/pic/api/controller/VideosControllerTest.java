package videos.pic.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import videos.pic.api.domain.categoria.Categoria;
import videos.pic.api.domain.videos.CadastroVideo;
import videos.pic.api.domain.videos.Videos;
import videos.pic.api.domain.videos.services.VideoService;

@SpringBootTest //Pra testar um controller no spring
@AutoConfigureMockMvc //Serve pra injetar o mockmvc na classe junto com o autowired
@AutoConfigureJsonTesters //Pro spring rodar o jacksonTesters
public class VideosControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<CadastroVideo> dadosCadastroJson;
	
	@MockBean
	private VideoService service;
	
	@Test
	@DisplayName("Deveria devolver status code 201")
	@WithMockUser
	void cadastrar_cenario1() throws Exception {
		
		var cadastro = new CadastroVideo("Titulo", "descricao", "https://www.youtube.com/", 1l);
		var toJson = dadosCadastroJson.write(cadastro).getJson();
		when(service.cadastrarVideo(any())).thenReturn(new Videos(cadastro));
		
		//Dispara uma requisição com endereço agendar via metodo post sem levar nenhum corpo
		var response = mvc.perform(
									MockMvcRequestBuilders.post("/videos")
									//.accept(MediaType.APPLICATION_JSON)
									.contentType(MediaType.APPLICATION_JSON)
									.content(toJson))
									//.andDo(print());
				//pega o resulta e pega o response joga numa variavel 
									.andReturn().getResponse();
		//verificamos se o status que está no response é 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		//verify(service, times(1)).cadastrarVideo(new Videos(cadastro));
	}
	
	@Test
	@DisplayName("Deve devolver status code 200")
	@WithMockUser
	void listarVideos_cenario2() throws Exception {
	
		//Dispara uma requisição com endereço agendar via metodo post sem levar nenhum corpo
		var response = mvc.perform(MockMvcRequestBuilders.get("/videos"))
				//pega o resulta e pega o response joga numa variavel 
			.andReturn().getResponse();
		//verificamos se o status que está no response é 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("Deve devolver status code 200")
	@WithMockUser
	void listarVideos_cenario3() throws Exception {
		//Dispara uma requisição com endereço agendar via metodo post sem levar nenhum corpo
		var response = mvc.perform(MockMvcRequestBuilders.get("/videos/?titulo="))
				//pega o resulta e pega o response joga numa variavel 
			.andReturn().getResponse();
		//verificamos se o status que está no response é 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("Deve devolver status code 200")
	@WithMockUser
	void listarVideos_cenario4() throws Exception {
		//Dispara uma requisição com endereço agendar via metodo post sem levar nenhum corpo
		Long id = 1l;
		var response = mvc.perform(MockMvcRequestBuilders.get("/videos/{id}", id))
				//pega o resulta e pega o response joga numa variavel 
			.andReturn().getResponse();
		//verificamos se o status que está no response é 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@DisplayName("Deve devolver status code 200")
	@WithMockUser
	void atualizarVideo_cenario5() throws Exception {
		Videos video = mockarUmVideo();
		String toJson = toJson(video);
		
		when(service.atualizarVideo(any())).thenReturn(video);
		
		
		//Dispara uma requisição com endereço agendar via metodo post sem levar nenhum corpo
		var response = mvc.perform(
									MockMvcRequestBuilders.put("/videos/att")
									.contentType(MediaType.APPLICATION_JSON)
									.content(toJson))
				//pega o resulta e pega o response joga numa variavel 
			.andReturn().getResponse();
		//verificamos se o status que está no response é 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		//verify(service, times(1)).atualizarVideo(any());
	}
	
	@Test
	@DisplayName("Deve retornar erro 400 por campo de id invalido")
	@WithMockUser
	void atualizarVideoSemId_cenario6() throws Exception {
		Videos video = mockarUmVideo();
		video.setId(null);
		String toJson = toJson(video);
		
		//when(service.atualizarVideo(any(Videos.class))).thenReturn(video);
		
		
		//Dispara uma requisição com endereço agendar via metodo post sem levar nenhum corpo
		var response = mvc.perform(
									MockMvcRequestBuilders.put("/videos/att")
									.contentType(MediaType.APPLICATION_JSON)
									.content(toJson))
									.andDo(print())
									.andExpect(status().isBadRequest())
//									.andExpect(jsonPath("$.httpStatusCodigo",is(400)))
//					                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
//					                .andExpect(jsonPath("$.mensagem", containsString("id obrigatorio")));
				//pega o resulta e pega o response joga numa variavel 
			.andReturn().getResponse();
		//verificamos se o status que está no response é 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		//verify(service, times(0)).atualizarVideo(any());
	}
	
	
	@Test
	@DisplayName("Deve devolver status code 200")
	@WithMockUser(roles = {"ADMIN"})
	void excluirVideo_cenario5() throws Exception {
		//Dispara uma requisição com endereço agendar via metodo post sem levar nenhum corpo
		Long id = 1l;
		var response = mvc.perform(MockMvcRequestBuilders.delete("/videos/del/{id}", id))
				//pega o resulta e pega o response joga numa variavel 
			.andReturn().getResponse();
		//verificamos se o status que está no response é 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	
	
	
    private String toJson(Object obj) throws JsonProcessingException {
		ObjectMapper object = new ObjectMapper();
		return object.writeValueAsString(obj);
	}
//
//	private Page<Videos> mockarVideosPaginados() {
//        Videos video = mockarUmVideo();
//        List<Videos> videos = Arrays.asList(video);
//        Page<Videos> videosPaginados = new PageImpl<>(videos);
//        return videosPaginados;
//    }

    private Videos mockarUmVideo() {
        Categoria categoria = new Categoria(1L, "LIVRE", "#FFFFFF");
        Videos video = new Videos(1l,"video","descricao do video", "http://www.site.com.br", categoria);
        return video;
    }


}

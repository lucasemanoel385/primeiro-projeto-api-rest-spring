package videos.pic.api.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import videos.pic.api.domain.usuario.AutenticacaoService;
import videos.pic.api.domain.usuario.DadosAutenticacao;
import videos.pic.api.domain.usuario.Usuario;
import videos.pic.api.infra.security.DadosTokenJWT;
import videos.pic.api.infra.security.TokenService;


@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired //Injetamos a classe TokenService na classe controler
	private TokenService tokenService;
	
	@Autowired
	private AutenticacaoService service;
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		//Instancia um DTO do tipo token
		service.validarLoginESenha(dados);
		
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(authenticationToken);
		
		//getPrincipal pega o usuario logado.  getPrincipal devolve um object por isso temos que fazer um cast pra usuario e o Spring conhece identificar pq implementamos a interface UserDetails na classe usuario 
		var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
		
		
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
		
	}

}
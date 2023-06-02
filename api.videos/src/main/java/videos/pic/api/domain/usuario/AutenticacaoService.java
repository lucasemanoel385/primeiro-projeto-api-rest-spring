package videos.pic.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import videos.pic.api.domain.AutenticacaoException;
import videos.pic.api.domain.ValidacaoException;

//Essa classe faz serviço de autenticacao
//Service é um componente e com a annotations o spring carrega ela, e ela faz um serviço()
@Service									//Tem que conter essa interface pro spring fazer a autenticacao 
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder test;

	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		var t = repository.findByLogin(username);
		return t;
	}
	
	public UserDetails validarLoginESenha(DadosAutenticacao dados) {
		var t = repository.findByLogin(dados.login());
		
		if(t == null) {
			throw new AutenticacaoException("Login invalido");
		}
		
		var p = test.matches(dados.senha(), t.getPassword());
		
		if(!p) {
			throw new AutenticacaoException("Senha invalida");
		}
		return t;
	}
	

	
}

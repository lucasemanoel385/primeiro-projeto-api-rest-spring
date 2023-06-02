package videos.pic.api.domain.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	//Metodo que vai fazer a consulta do usuario no banco de dados
	UserDetails findByLogin(String login);

	//Optional<Usuario> findByLogin(String username);

}

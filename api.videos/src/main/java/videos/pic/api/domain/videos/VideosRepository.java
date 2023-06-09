package videos.pic.api.domain.videos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VideosRepository extends JpaRepository<Videos, Long> {

	Page<Videos> findByTituloContains(String titulo, Pageable page);




}

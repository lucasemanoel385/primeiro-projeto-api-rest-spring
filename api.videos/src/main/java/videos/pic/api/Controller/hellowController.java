package videos.pic.api.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hellow")
public class hellowController {
	
	@GetMapping
	public String olaMundo() {
		return "alo mundo";
	}

}

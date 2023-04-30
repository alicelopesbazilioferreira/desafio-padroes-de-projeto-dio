package one.digitalinnovation.lppsp.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import one.digitalinnovation.lppsp.model.Endereco;

@Component
@FeignClient(name = "viacep", url = "${viacep.url}")
public interface ViaCepFeign {

	@GetMapping(value = "/{cep}/json/")
	Endereco consultarCep(@PathVariable("cep") String cep);
}

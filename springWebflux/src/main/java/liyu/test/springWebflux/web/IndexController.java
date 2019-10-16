package liyu.test.springWebflux.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
@RestController
public class IndexController {
	@RequestMapping("/")
	public String sccess() {
		return "success";
	}
	
	@Autowired private Scheduler scheduler;

    @RequestMapping(value = "/product")
    public Mono<ProductInfo> product() {
        Mono<ProductInfo> result = Mono.fromCallable(() ->
            new ProductInfo(1)
        ).subscribeOn(scheduler);
        return result;
    }
}

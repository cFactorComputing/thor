package in.cfcomputing.thor.core.swagger;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;

@RestController
@ApiIgnore
public class SwaggerApiController {
    @Autowired(
            required = false
    )
    private SecurityConfiguration securityConfiguration;
    @Autowired(
            required = false
    )
    private UiConfiguration uiConfiguration;
    private final SwaggerResourcesProvider swaggerResources;

    @Autowired
    public SwaggerApiController(SwaggerResourcesProvider swaggerResources) {
        this.swaggerResources = swaggerResources;
    }

    @RequestMapping(value = {"/configuration/security"}, method = RequestMethod.GET)
    ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity(Optional.fromNullable(this.securityConfiguration).or(new SecurityConfiguration((String) null, (String) null, (String) null, (String) null, (String) null, ApiKeyVehicle.HEADER, "api_key", ",")), HttpStatus.OK);
    }

    @RequestMapping(value = {"/configuration/ui"}, method = RequestMethod.GET)
    ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity(Optional.fromNullable(this.uiConfiguration).or(new UiConfiguration(null)), HttpStatus.OK);
    }
}
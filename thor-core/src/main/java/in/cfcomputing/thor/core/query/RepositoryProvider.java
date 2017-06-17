package in.cfcomputing.thor.core.query;

import in.cfcomputing.odin.core.factory.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class RepositoryProvider extends ApplicationContextProvider {

    @Inject
    public RepositoryProvider(final ApplicationContext applicationContext) {
        super(applicationContext);
    }
}
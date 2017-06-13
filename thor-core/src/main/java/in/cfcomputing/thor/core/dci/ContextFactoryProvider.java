package in.cfcomputing.thor.core.dci;

import in.cfcomputing.odin.core.factory.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ContextFactoryProvider extends ApplicationContextProvider {

    @Inject
    public ContextFactoryProvider(final ApplicationContext applicationContext) {
        super(applicationContext);
    }
}
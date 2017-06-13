package in.cfcomputing.thor.core;


import in.cfcomputing.odin.core.services.security.provider.AuthenticatedUserProvider;
import in.cfcomputing.thor.core.dci.ContextFactoryProvider;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractDciFacade {
    
    private final ContextFactoryProvider contextFactoryProvider;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Deprecated//Use the other constructor instead
    protected AbstractDciFacade(final ContextFactoryProvider contextFactoryProvider) {
        this(contextFactoryProvider, null);
    }

    protected AbstractDciFacade(final ContextFactoryProvider contextFactoryProvider,
                                final AuthenticatedUserProvider authenticatedUserProvider) {
        this.contextFactoryProvider = contextFactoryProvider;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }


    protected ContextFactoryProvider factoryProvider() {
        return contextFactoryProvider;
    }

    protected String userId() {
        if (authenticatedUserProvider != null) {
            return authenticatedUserProvider.userId();
        }
        return StringUtils.EMPTY;
    }
}
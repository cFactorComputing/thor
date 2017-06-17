package in.cfcomputing.thor.core;


import in.cfcomputing.odin.core.factory.ApplicationContextProvider;
import in.cfcomputing.odin.core.services.security.provider.AuthenticatedUserProvider;
import in.cfcomputing.thor.core.dci.ContextFactoryProvider;
import in.cfcomputing.thor.core.query.RepositoryProvider;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractDciFacade {
    private final ApplicationContextProvider applicationContextProvider;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    protected AbstractDciFacade(final ApplicationContextProvider applicationContextProvider,
                                final AuthenticatedUserProvider authenticatedUserProvider) {
        this.applicationContextProvider = applicationContextProvider;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    protected ContextFactoryProvider factoryProvider() {
        return (ContextFactoryProvider) applicationContextProvider;
    }

    protected RepositoryProvider repositoryProvider() {
        return (RepositoryProvider) applicationContextProvider;
    }

    protected String userId() {
        if (authenticatedUserProvider != null) {
            return authenticatedUserProvider.userId();
        }
        return StringUtils.EMPTY;
    }
}
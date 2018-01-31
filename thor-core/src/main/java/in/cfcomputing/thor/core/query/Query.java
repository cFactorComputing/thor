package in.cfcomputing.thor.core.query;

import in.cfcomputing.odin.core.services.security.oauth2.access.domain.OdinUserDetails;
import in.cfcomputing.odin.core.services.security.provider.AuthenticatedUserProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

import static in.cfcomputing.thor.core.query.Query.PROPERTY.USER;


public abstract class Query<R, S> {
    enum PROPERTY {
        USER
    }

    private final Map<Object, Object> properties = new HashMap<>();

    public <T> T get(final Class key) {
        return (T) properties.get(key.getName());
    }

    public <T extends Query<R, S>> T put(final Object value) {
        putValue(value.getClass(), value);
        return (T) this;
    }

    private void putValue(final Class clazz, final Object value) {
        properties.put(clazz.getName(), value);
    }

    public S query() {
        initialize();
        final R t = doQuery();
        return toResult(t);
    }

    protected void initialize() {
        final Class clazz = this.getClass();
        Validate.isTrue(clazz.isAnnotationPresent(Repositories.class),
                "Repositories should be provided in a query.");

        final Repositories repositories = this.getClass().getAnnotation(Repositories.class);
        final Class[] values = repositories.values();
        final RepositoryProvider provider = get(RepositoryProvider.class);
        Validate.notNull(provider, "Repository provider is empty.");

        for (Class repositoryClazz : values) {
            Validate.notNull(repositoryClazz, "Repository class is empty.");
            final Object repository = provider.provide(repositoryClazz);

            putValue(repositoryClazz, repository);
        }

        final AuthenticatedUserProvider authenticatedUserProvider = provider.provide(AuthenticatedUserProvider.class);
        putValue(AuthenticatedUserProvider.class, authenticatedUserProvider);
    }

    protected String userId() {
        final String userId;
        final Object user = properties.get(USER.getClass().getName());
        if (user instanceof OdinUserDetails) {
            final OdinUserDetails userDetails = (OdinUserDetails) user;
            userId = userDetails.getUserId();
        } else {
            userId = (String) user;
        }
        Validate.isTrue(StringUtils.isNotEmpty(userId), "Authenticated user not found in context.");
        return userId;
    }

    protected <U> U user() {
        final Object user = properties.get(USER.getClass().getName());
        Validate.isTrue(user instanceof OdinUserDetails, "BaseAuthenticated user not set in context.");
        final OdinUserDetails userDetails = (OdinUserDetails) user;
        return (U) userDetails.getAuthenticatedUser();
    }

    protected abstract R doQuery();

    protected abstract S toResult(final R result);

    public static <T extends Query> T of(final Class<? extends T> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T extends Query<R, S>> T withRepositoryProvider(final RepositoryProvider provider) {
        return put(provider);
    }

    public <T extends Query<R, S>> T withUser(final Object user) {
        putValue(USER.getClass(), user);
        return (T) this;
    }
}
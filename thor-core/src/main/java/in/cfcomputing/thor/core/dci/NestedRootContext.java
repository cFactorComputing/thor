package in.cfcomputing.thor.core.dci;


import in.cfcomputing.odin.core.services.security.domain.BaseAuthenticatedUser;
import org.springframework.transaction.annotation.Propagation;

import java.util.ArrayList;
import java.util.List;

import static in.cfcomputing.thor.core.dci.ContextHolder.isNotEmpty;

public abstract class NestedRootContext extends DeferredContext {
    private final List<Context> children = new ArrayList<>();

    public NestedRootContext add(final Context child) {
        children.add(child);
        return this;
    }

    public NestedRootContext add(final Class<? extends Context> child) {
        final Context context = Context.create(child);
        return add(context);
    }

    @Override
    public void initialize() {
        initializeRoot();
        for (Context child : children) {
            child.addContextFactory();
            child.initialize();
        }
        children.clear();
    }

    public abstract void initializeRoot();

    @Override
    public <T extends Context> T transactional(final Propagation value) {
        if (isSameContext()) {
            getContextProperties().addTransactionPropagation(value);
            return (T) this;
        }
        return existingContext().transactional(value);
    }

    @Override
    public boolean isTransactional() {
        if (isSameContext()) {
            return getContextProperties().isTransactional();
        }
        return existingContext().isTransactional();
    }

    @Override
    public Propagation getTransactionPropagation() {
        if (isSameContext()) {
            return getContextProperties().getTransactionPropagation();
        }
        return existingContext().getTransactionPropagation();
    }

    @Override
    public <T> T get(final Class key) {
        if (isSameContext()) {
            return getContextProperties().get(key);
        }
        return existingContext().get(key);
    }

    @Override
    public Context put(final Object value) {
        if (isSameContext()) {
            getContextProperties().put(value);
            return this;
        }
        return existingContext().put(value);
    }

    @Override
    public <T extends Context> T withUser(final Object user) {
        if (isSameContext()) {
            getContextProperties().addUser(user);
            return (T) this;
        }
        return existingContext().withUser(user);
    }

    @Override
    public String userId() {
        if (isSameContext()) {
            return getContextProperties().getUserId();
        }
        return existingContext().userId();
    }

    @Override
    public BaseAuthenticatedUser user() {
        if (isSameContext()) {
            return getContextProperties().getUser();
        }
        return existingContext().user();
    }

    @Override
    public void clearResult() {
        if (isSameContext()) {
            getContextProperties().removeResult();
        } else {
            existingContext().clearResult();
        }
    }

    @Override
    public <T extends Context> T withFactoryProvider(final Object factoryObject) {
        if (isSameContext()) {
            getContextProperties().addProvider(factoryObject);
            return (T) this;
        }
        return existingContext().withFactoryProvider(factoryObject);
    }

    @Override
    public ContextFactoryProvider factoryProvider() {
        if (isSameContext()) {
            return getContextProperties().getFactoryProvider();
        }
        return existingContext().factoryProvider();
    }

    @Override
    public <R> R getResult() {
        if (isSameContext()) {
            return getContextProperties().getResult();
        }
        return existingContext().getResult();
    }

    @Override
    public void addResult(final Object result) {
        if (isSameContext()) {
            getContextProperties().addResult(result);
        } else {
            existingContext().addResult(result);
        }
    }

    @Override
    public boolean hasProperty(final Object key) {
        if (isSameContext()) {
            return getContextProperties().hasProperty(key);
        }
        return existingContext().hasProperty(key);
    }

    @Override
    public void remove(final Object key) {
        if (isSameContext()) {
            getContextProperties().remove(key);
        } else {
            existingContext().remove(key);
        }
    }

    @Override
    public boolean hasPropertyValue(final Class key, final Object value) {
        if (isSameContext()) {
            return getContextProperties().hasPropertyValue(key, value);
        }
        return existingContext().hasPropertyValue(key, value);
    }

    @Override
    public <T extends Context> T putIfAbsent(final Object value) {
        if (isSameContext()) {
            getContextProperties().putIfAbsent(value);
            return (T) this;
        }
        return existingContext().putIfAbsent(value);
    }

    private Context existingContext() {
        return isNotEmpty() ? resolve() : this;
    }

    private boolean isSameContext() {
        return existingContext() == this;
    }
}
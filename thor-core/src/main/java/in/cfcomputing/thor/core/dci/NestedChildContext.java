package in.cfcomputing.thor.core.dci;


import org.apache.commons.lang3.Validate;
import org.springframework.transaction.annotation.Propagation;

public abstract class NestedChildContext extends DeferredContext {
    @Override
    public Context put(final Object value) {
        return rootContext().put(value);
    }

    @Override
    public <T> T get(final Class key) {
        return rootContext().get(key);
    }

    @Override
    public void remove(final Object key) {
        rootContext().remove(key);
    }

    @Override
    public <T extends Context> T putIfAbsent(final Object value) {
        return rootContext().putIfAbsent(value);
    }

    @Override
    public boolean hasPropertyValue(final Class key, final Object value) {
        return rootContext().hasPropertyValue(key, value);
    }

    @Override
    public boolean hasProperty(final Object key) {
        return rootContext().hasProperty(key);
    }

    @Override
    public void addResult(final Object result) {
        rootContext().addResult(result);
    }

    @Override
    public <R> R getResult() {
        return rootContext().getResult();
    }

    @Override
    public <T extends Context> T withFactoryProvider(final Object factoryObject) {
        return rootContext().withFactoryProvider(factoryObject);
    }

    @Override
    public <T extends Context> T withUser(final String user) {
        return rootContext().withUser(user);
    }

    @Override
    public <T extends Context> T transactional(final Propagation value) {
        return rootContext().transactional(value);
    }

    @Override
    public boolean isTransactional() {
        return rootContext().isTransactional();
    }

    @Override
    public Propagation getTransactionPropagation() {
        return rootContext().getTransactionPropagation();
    }

    @Override
    public void clearResult() {
        rootContext().clearResult();
    }

    @Override
    public String userId() {
        return rootContext().userId();
    }

    @Override
    public void doExecute() {
        //Empty method. Override if necessary in sub classes.
    }

    @Override
    public ContextFactoryProvider factoryProvider() {
        return rootContext().factoryProvider();
    }

    private Context rootContext() {
        final Context rootContext = resolve();
        Validate.notNull(rootContext, "Missing root context");
        return rootContext;
    }
}
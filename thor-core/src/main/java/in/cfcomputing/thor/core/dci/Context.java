package in.cfcomputing.thor.core.dci;


import org.apache.commons.lang3.Validate;
import org.springframework.transaction.annotation.Propagation;

import java.lang.reflect.InvocationTargetException;

public abstract class Context {
    private final ContextProperties contextProperties = new ContextProperties();

    public <T> T get(final Class key) {
        return (T) contextProperties.get(key);
    }

    public <T extends Context> T put(final Object value) {
        contextProperties.put(value);
        return (T) this;
    }

    public <T extends Context> T transactional(final Propagation value) {
        contextProperties.addTransactionPropagation(value);
        return (T) this;
    }

    public boolean isTransactional() {
        return contextProperties.isTransactional();
    }

    public Propagation getTransactionPropagation() {
        return contextProperties.getTransactionPropagation();
    }

    public <T extends Context> T withFactoryProvider(final Object factoryObject) {
        contextProperties.addProvider(factoryObject);
        return (T) this;
    }

    public <T extends Context> T withUser(final Object user) {
        contextProperties.addUser(user);
        return (T) this;
    }

    public void clearResult() {
        contextProperties.removeResult();
    }

    public String userId() {
        return contextProperties.getUserId();
    }

    public abstract void initialize();

    public abstract void doExecute();

    public <T> void trait(final Class<T> traitClazz, final Object argument) {
        try {
            final Object traitImpl = traitClazz.getConstructor(argument.getClass()).newInstance(argument);
            put(traitImpl);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    public <R> R getResult() {
        return contextProperties.getResult();
    }

    public void addResult(final Object result) {
        contextProperties.addResult(result);
    }

    public static Context resolve() {
        return ContextHolder.get();
    }

    public boolean hasPropertyValue(final Class key, final Object value) {
        return contextProperties.hasPropertyValue(key, value);
    }

    public static <T extends Context> T create(final Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T extends Context> T putIfAbsent(final Object value) {
        contextProperties.putIfAbsent(value);
        return (T) this;
    }

    public boolean hasProperty(final Object key) {
        return contextProperties.hasProperty(key);
    }

    public void remove(final Object key) {
        contextProperties.remove(key);
    }

    public void bind() {
        ContextHolder.bind(this);
    }

    public void unbind() {
        ContextHolder.unbind();
    }

    public <T> T execute() {
        bind();
        addContextFactory();
        initialize();
        doExecute();
        unbind();
        return getResult();
    }

    protected void addContextFactory() {
        final Class clazz = this.getClass();
        Validate.isTrue(clazz.isAnnotationPresent(ContextFactory.class),
                "ContextFactory should be provided in a context.");

        final ContextFactory factory = this.getClass().getAnnotation(ContextFactory.class);
        final Class factoryClazz = factory.value();
        final ContextFactoryProvider provider = factoryProvider();

        Validate.notNull(factoryClazz, "Context Factory class is empty.");
        Validate.notNull(provider, "Context Factory provider is empty.");

        final Object factoryImpl = provider.provide(factoryClazz);
        put(factoryImpl);
    }

    public ContextFactoryProvider factoryProvider() {
        return contextProperties.getFactoryProvider();
    }

    protected ContextProperties getContextProperties() {
        return contextProperties;
    }
}
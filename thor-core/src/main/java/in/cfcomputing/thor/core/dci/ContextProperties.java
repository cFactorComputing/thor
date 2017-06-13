package in.cfcomputing.thor.core.dci;


import org.springframework.transaction.annotation.Propagation;

import java.util.HashMap;
import java.util.Map;

class ContextProperties {

    private enum Property {
        RESULT,
        FACTORY_PROVIDER,
        USER_ID,
        TRANSACTIONAL
    }

    private final Map<Object, Object> properties = new HashMap<>();

    protected <T> T get(final Class key) {
        return (T) properties.get(key.getName());
    }

    protected void put(final Object value) {
        properties.put(value.getClass().getName(), value);
    }

    protected void remove(final Object key) {
        if (hasProperty(key)) {
            properties.remove(key.getClass().getName());
        }
    }

    protected void putIfAbsent(final Object value) {
        if (!properties.containsKey(value.getClass().getName())) {
            put(value);
        }
    }

    protected <T> T getFactoryProvider() {
        return (T) properties.get(Property.FACTORY_PROVIDER);
    }

    protected boolean hasProperty(final Object key) {
        return properties.containsKey(key.getClass().getName());
    }

    protected void addResult(final Object result) {
        properties.put(Property.RESULT, result);
    }

    protected void addProvider(final Object factoryObject) {
        properties.put(Property.FACTORY_PROVIDER, factoryObject);
    }

    protected void addUser(final String userId) {
        properties.put(Property.USER_ID, userId);
    }

    protected String getUser() {
        return (String) properties.get(Property.USER_ID);
    }

    protected <R> R getResult() {
        return (R) properties.get(Property.RESULT);
    }

    protected void removeResult() {
        properties.remove(Property.RESULT);
    }

    protected boolean hasPropertyValue(final Class key, final Object value) {
        final Object existingValue = get(key);
        return value.equals(existingValue);
    }

    protected void addTransactionPropagation(final Propagation propagation) {
        properties.put(Property.TRANSACTIONAL, propagation);
    }

    protected boolean isTransactional() {
        return properties.containsKey(Property.TRANSACTIONAL);
    }

    protected Propagation getTransactionPropagation() {
        return (Propagation) properties.get(Property.TRANSACTIONAL);
    }
}
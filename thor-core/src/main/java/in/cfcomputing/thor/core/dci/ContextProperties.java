package in.cfcomputing.thor.core.dci;


import in.cfcomputing.odin.core.services.security.domain.BaseAuthenticatedUser;
import in.cfcomputing.odin.core.services.security.oauth2.access.domain.OdinUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.transaction.annotation.Propagation;

import java.util.HashMap;
import java.util.Map;

class ContextProperties {

    private enum Property {
        RESULT,
        FACTORY_PROVIDER,
        USER,
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

    protected void addUser(final Object userId) {
        properties.put(Property.USER, userId);
    }

    protected String getUserId() {
        final String userId;
        final Object user = properties.get(Property.USER);
        if (user instanceof OdinUserDetails) {
            final OdinUserDetails userDetails = (OdinUserDetails) user;
            userId = userDetails.getUserId();
        } else {
            userId = (String) user;
        }
        Validate.isTrue(StringUtils.isNotEmpty(userId), "Authenticated user not found in context.");
        return userId;
    }

    protected BaseAuthenticatedUser getUser() {
        final Object user = properties.get(Property.USER);
        Validate.isTrue(user instanceof OdinUserDetails, "BaseAuthenticated user not set in context.");
        final OdinUserDetails userDetails = (OdinUserDetails) user;
        return (BaseAuthenticatedUser) userDetails.getAuthenticatedUser();
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
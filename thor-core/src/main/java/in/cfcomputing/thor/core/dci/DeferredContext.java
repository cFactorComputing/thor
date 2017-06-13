package in.cfcomputing.thor.core.dci;


import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

public abstract class DeferredContext<T> extends Context implements Supplier<T> {

    @Override
    public T get() {
        if (isTransactional()) {
            final ContextFactoryProvider factoryProvider = factoryProvider();
            Validate.notNull(factoryProvider, "Factory provider is empty");
            final TransactionProvider transactionProvider = factoryProvider.provide(TransactionProvider.class);
            Validate.notNull(transactionProvider, "Transaction provider is empty");

            //Currently just returning as REQUIRES_NEW transaction. Add more if required.
            return transactionProvider.requiresNew(this);
        } else {
            return execute();
        }
    }
}
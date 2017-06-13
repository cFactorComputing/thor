package in.cfcomputing.thor.core.query;

import java.util.function.Supplier;

public abstract class DeferredQuery<R, S> extends Query<R, S> implements Supplier<S> {

    @Override
    public S get() {
        return query();
    }
}
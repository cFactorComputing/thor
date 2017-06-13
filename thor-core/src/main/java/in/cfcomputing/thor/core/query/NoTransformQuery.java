package in.cfcomputing.thor.core.query;

public abstract class NoTransformQuery<R> extends DeferredQuery<R, R> {

    protected R toResult(final R result) {
        return result;
    }
}
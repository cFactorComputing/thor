package in.cfcomputing.thor.core;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Created by gibugeorge on 13/06/2017.
 */
public class AbstractNioController {

    protected <T> DeferredResult<ResponseEntity<T>> executeDeferred(final Supplier<T> supplier) {
        final DeferredResult<ResponseEntity<T>> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(supplier)
                .whenCompleteAsync((result, throwable) -> {
                    if (throwable != null) {
                        deferredResult.setErrorResult(throwable.getCause());
                    } else {
                        deferredResult.setResult(ResponseEntity.ok(result));
                    }
                });

        return deferredResult;
    }
}
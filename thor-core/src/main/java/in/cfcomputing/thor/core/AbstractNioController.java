package in.cfcomputing.thor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Created by gibugeorge on 13/06/2017.
 */
public class AbstractNioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNioController.class);

    protected <T> DeferredResult<ResponseEntity<T>> executeDeferred(final Supplier<T> supplier) {
        final DeferredResult<ResponseEntity<T>> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(supplier)
                .whenCompleteAsync((result, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error("Error in executing request.", throwable);
                        deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                    } else {
                        final ResponseEntity responseEntity = result != null ? ResponseEntity.ok(result)
                                : ResponseEntity.ok().build();
                        deferredResult.setResult(responseEntity);
                    }
                });

        return deferredResult;
    }
}
package in.cfcomputing.thor.core.dci;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionProvider {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T requiresNew(final Context context) {
        return context.execute();
    }
}
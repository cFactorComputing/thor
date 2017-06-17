package in.cfcomputing.thor.core.dci;

import org.apache.commons.lang3.Validate;

class ContextHolder {
    private static final ThreadLocal<Context> userThreadLocal = new ThreadLocal<>();

    private ContextHolder() {
    }

    static void bind(Context context) {
        userThreadLocal.set(context);
    }

    static void unbind() {
        userThreadLocal.remove();
    }

    static boolean isNotEmpty() {
        return userThreadLocal.get() != null;
    }

    static Context get() {
        final Context context = userThreadLocal.get();
        Validate.notNull(context, "Context is empty.");
        return context;
    }
}
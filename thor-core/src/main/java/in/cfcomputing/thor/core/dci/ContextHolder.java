package in.cfcomputing.thor.core.dci;

import org.apache.commons.lang3.Validate;

public class ContextHolder {
    private static final ThreadLocal<Context> userThreadLocal = new ThreadLocal<>();

    private ContextHolder() {
    }

    public static void bind(Context context) {
        userThreadLocal.set(context);
    }

    public static void unbind() {
        userThreadLocal.remove();
    }

    public static boolean isNotEmpty() {
        return userThreadLocal.get() != null;
    }

    public static Context get() {
        final Context context = userThreadLocal.get();
        Validate.notNull(context, "Context is empty.");
        return context;
    }

    public static void rebind(final Context context) {
        userThreadLocal.remove();
        userThreadLocal.set(context);
    }
}
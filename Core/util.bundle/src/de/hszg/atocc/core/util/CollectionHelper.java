package de.hszg.atocc.core.util;

import java.util.List;

public final class CollectionHelper {

    private CollectionHelper() {

    }

    public static <T> String makeString(List<T> collection, String delimiter) {
        final StringBuilder builder = new StringBuilder();

        if (!collection.isEmpty()) {
            builder.append(collection.get(0));
        }

        for (int i = 1; i < collection.size(); ++i) {
            builder.append(delimiter);
            builder.append(collection.get(i));
        }

        return builder.toString();
    }

}

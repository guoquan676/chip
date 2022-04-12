package com.template.chip.controller.test;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Collection2<T> extends Collection<T> {

    default void forEachIf(Consumer<T> action, Predicate<T> filter){
        Objects.requireNonNull(action);
        for (T t : this) {
            boolean test = filter.test(t);
            if(test){
                action.accept(t);
            }
        }
    }
}

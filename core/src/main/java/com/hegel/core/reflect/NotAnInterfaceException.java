package com.hegel.core.reflect;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class NotAnInterfaceException extends RuntimeException {
    public NotAnInterfaceException(String message) {
        super(message);
    }
}

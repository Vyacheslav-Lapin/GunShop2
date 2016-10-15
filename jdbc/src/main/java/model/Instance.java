package model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Instance {
    private final long id;
    private final Gun gun;
}

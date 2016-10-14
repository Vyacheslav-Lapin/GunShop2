package model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Gun {
    private final long id;
    private final String name;
    private final double caliber;
}

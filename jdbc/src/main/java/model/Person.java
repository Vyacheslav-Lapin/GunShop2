package model;import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
public class Person {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final boolean permission;
    private final LocalDate dob;
    private final String email;
    private final String password;
    private final String address;
    private final String telephone;
}
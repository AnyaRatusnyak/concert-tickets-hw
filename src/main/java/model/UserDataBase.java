package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDataBase {
    private Long id;
    private String name;
    private LocalDate creationDate;
}

package ar.unrn.tp.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {
    private String id;
    private String title;
    private String text;
    private String author;
    private LocalDate date;
}
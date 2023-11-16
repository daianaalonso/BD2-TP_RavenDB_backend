package ar.unrn.tp.domain;

import lombok.*;

import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private String id;
    private String title;
    private String text;
    private List<String> tags;
    private String resume;
    private List<String> relatedLinks;
    private String author;
    private LocalDate date;
}
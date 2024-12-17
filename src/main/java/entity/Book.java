package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@Data
@Audited
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Column(name = "published_year", nullable = false)
    private int publishedYear;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public Book(String dune, Genre genre, int i, int i1, Author author1) {
    }
}
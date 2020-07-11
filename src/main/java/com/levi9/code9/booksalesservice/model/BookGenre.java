package com.levi9.code9.booksalesservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "BookGenre")
@Table(name = "book_genre")
public class BookGenre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private GenreEntity genre;

    public BookGenre(BookEntity book, GenreEntity genre) {
        this.book = book;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BookGenre)) return false;
        BookGenre bookGenre = (BookGenre) o;
        return  Objects.equals(book, bookGenre.book) &&
                Objects.equals(genre, bookGenre.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, genre);
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "id=" + id +
                ", book=" + book +
                ", genre=" + genre +
                '}';
    }
}

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL,
    isbn VARCHAR(13) NOT NULL
);

CREATE TABLE patron (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info VARCHAR(255) NOT NULL
);

CREATE TABLE borrowing_record (
    id SERIAL PRIMARY KEY,
    book_id INT NOT NULL,
    patron_id INT NOT NULL,
    borrowed_at TIMESTAMP NOT NULL,
    returned_at TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (patron_id) REFERENCES patron(id)
);

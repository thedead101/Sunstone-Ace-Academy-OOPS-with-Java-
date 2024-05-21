import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    // Getters and Setters
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrowBook() {
        this.isBorrowed = true;
    }

    public void returnBook() {
        this.isBorrowed = false;
    }
}

class Member {
    private String memberId;
    private String name;
    private List<Book> borrowedBooks;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Methods
    public void borrowBook(Book book) {
        if (!book.isBorrowed()) {
            book.borrowBook();
            borrowedBooks.add(book);
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.returnBook();
            borrowedBooks.remove(book);
        } else {
            System.out.println("This book is not borrowed by this member.");
        }
    }
}

class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // Methods to manage books
    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(String bookId) {
        books.removeIf(book -> book.getBookId().equals(bookId));
    }

    public void updateBook(String bookId, String title, String author) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                book.setTitle(title);
                book.setAuthor(author);
                break;
            }
        }
    }

    // Methods to manage members
    public void addMember(Member member) {
        members.add(member);
    }

    public void deleteMember(String memberId) {
        members.removeIf(member -> member.getMemberId().equals(memberId));
    }

    public void updateMember(String memberId, String name) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                member.setName(name);
                break;
            }
        }
    }

    // Methods to handle borrowing and returning books
    public void borrowBook(String memberId, String bookId) {
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);
        if (member != null && book != null) {
            member.borrowBook(book);
        } else {
            System.out.println("Member or Book not found.");
        }
    }

    public void returnBook(String memberId, String bookId) {
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);
        if (member != null && book != null) {
            member.returnBook(book);
        } else {
            System.out.println("Member or Book not found.");
        }
    }

    public void checkBookAvailability(String bookId) {
        Book book = findBookById(bookId);
        if (book != null) {
            System.out.println("Book " + (book.isBorrowed() ? "is not available." : "is available."));
        } else {
            System.out.println("Book not found.");
        }
    }

    public void listAllBooks() {
        for (Book book : books) {
            System.out.println("ID: " + book.getBookId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Borrowed: " + (book.isBorrowed() ? "Yes" : "No"));
        }
    }

    // Helper methods to find book and member by their IDs
    private Book findBookById(String bookId) {
        return books.stream()
                .filter(book -> book.getBookId().equals(bookId))
                .findFirst()
                .orElse(null);
    }

    private Member findMemberById(String memberId) {
        return members.stream()
                .filter(member -> member.getMemberId().equals(memberId))
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Sample data
        Book book1 = new Book("B001", "1984", "George Orwell");
        Book book2 = new Book("B002", "To Kill a Mockingbird", "Harper Lee");
        Book book3 = new Book("B003", "The Great Gatsby", "F. Scott Fitzgerald");
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        Member member1 = new Member("M001", "Harsh");
        Member member2 = new Member("M002", "Yash");
        library.addMember(member1);
        library.addMember(member2);

        // Example operations
        System.out.println("Borrowing Books:");
        library.borrowBook("M001", "B001");
        library.borrowBook("M001", "B003");
        library.borrowBook("M002", "B002");

        System.out.println("\nChecking Book Availability:");
        library.checkBookAvailability("B001");
        library.checkBookAvailability("B003");
        library.checkBookAvailability("B002");

        System.out.println("\nReturning Books:");
        library.returnBook("M001", "B001");
        library.returnBook("M002", "B002");

        System.out.println("\nChecking Book Availability after Returning:");
        library.checkBookAvailability("B001");
        library.checkBookAvailability("B002");

        System.out.println("\nUpdating and Deleting Books:");
        library.updateBook("B003", "The Great Gatsby", "Fitzgerald");
        library.deleteBook("B002");

        System.out.println("\nListing All Books:");
        library.listAllBooks();

        System.out.println("\nUpdating and Deleting Members:");
        library.updateMember("M001", "Johnathan Doe");
        library.deleteMember("M002");

        System.out.println("\nBorrowing and Returning Books with Updated Member:");
        library.borrowBook("M001", "B003");
        library.returnBook("M001", "B003");

        System.out.println("\nListing All Books:");
        library.listAllBooks();

        scanner.close();
    }

}

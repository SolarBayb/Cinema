package lpnu.entity;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cinema {
    private Long id;

    @NotBlank
    private String name;

    private List<Hall> halls = new ArrayList<>();

    public Cinema() {

    }

    public Cinema(final Long id, final String name, final List<Hall> halls) {
        this.id = id;
        this.name = name;
        this.halls = halls;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(final List<Hall> halls) {
        this.halls = halls;
    }

    public void addHall(final Hall hall) {
        halls.add(hall);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(name, cinema.name) && Objects.equals(halls, cinema.halls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, halls);
    }
}

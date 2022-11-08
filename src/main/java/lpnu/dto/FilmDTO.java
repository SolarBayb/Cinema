package lpnu.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class FilmDTO {
    public static final double MARK_UP = 30;
    public static final double STANDART_PRICE = 110;

    private Long id;

    @Min(1)
    @Max(210)
    private int duration;

    @NotBlank
    private String name;

    @Min(0)
    @Max(18)
    private int minAge;

    private double priceTechnology;

    @NotBlank
    private String technology;

    public FilmDTO(){

    }

    public FilmDTO(final Long id, final int duration, final String name, final int minAge,final double priceTechnology, final String technology) {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.minAge = minAge;
        this.priceTechnology= priceTechnology;
        this.technology = technology;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(final int minAge) {
        this.minAge = minAge;
    }

    public double getPriceTechnology() {
        return priceTechnology;
    }

    public void setPriceTechnology(final double priceTechnology) {
        this.priceTechnology = priceTechnology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(final String technology) {
        this.technology = technology;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmDTO filmDTO = (FilmDTO) o;
        return duration == filmDTO.duration && minAge == filmDTO.minAge && Double.compare(filmDTO.priceTechnology, priceTechnology) == 0 && Objects.equals(name, filmDTO.name) && Objects.equals(technology, filmDTO.technology);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, name, minAge, priceTechnology, technology);
    }
}


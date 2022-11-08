package lpnu.dto;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {
    private Long id;

    @Pattern(regexp="([A-Z][a-z]+[\\s-]?)*[A-Z][a-z]+", message="Invalid user name")
    private String name;

    @Pattern(regexp="([A-Z][a-z]+[\\s-]?)*[A-Z][a-z]+", message="Invalid user surname")
    private String surname;

    @Pattern(regexp="[a-zA-Z0-9]{4,20}@[a-z]{1,5}\\.[a-z]{1,3}", message="Invalid user email")
    private String email;

    private List<TicketDTO> ticketDTOList = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(final Long id, final String name, final String surname, final String email, final List<TicketDTO> ticketDTOList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.ticketDTOList = ticketDTOList;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<TicketDTO> getTicketDTOList() {
        return ticketDTOList;
    }

    public void setTicketDTOList(final List<TicketDTO> ticketDTOList) {
        this.ticketDTOList = ticketDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(name, userDTO.name) && Objects.equals(surname, userDTO.surname) && Objects.equals(email, userDTO.email) && Objects.equals(ticketDTOList, userDTO.ticketDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, ticketDTOList);
    }
}
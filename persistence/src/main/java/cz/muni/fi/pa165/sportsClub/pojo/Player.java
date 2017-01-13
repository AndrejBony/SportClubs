package cz.muni.fi.pa165.sportsClub.pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import cz.muni.fi.pa165.sportsClub.pojo.validation.Past;

/**
 * Represents player in roster
 *
 * @author Simon Sudora 461460
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "unique_email", columnNames = {"email"}),
    @UniqueConstraint(name = "unique_mobile", columnNames = {"mobile"})})
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    @Min(75)
    @Max(250)
    private double height;

    @NotNull
    @Column(nullable = false)
    @Min(20)
    @Max(150)
    private double weight;

    @NotNull
    @Column(nullable = false)
    @Past
    private LocalDate dateOfBirth;

    @NotNull
    @Column(nullable = false)
    @Pattern(regexp = "[^@]+@[^@]+\\.[^@]+")
    private String email;

    @Pattern(regexp = "(\\+|00)?\\d+")
    private String mobile;

    @ManyToOne
    @NotNull
    private Manager manager;

    @OneToMany(mappedBy = "player", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<PlayerInfo> playerInfos = new ArrayList<>();

    public Player() {
    }

    public Player(Long id) {
        this.id = id;
    }

    /**
     * Gets id of player
     *
     * @return player id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id of player
     *
     * @param id player id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets first name of player
     *
     * @return first name of player
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name of player
     *
     * @param firtsName first name of player
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name of player
     *
     * @return last name of player
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name of player
     *
     * @param lastName last name of player
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets height of player
     *
     * @return height of player
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets height of player
     *
     * @param height height of player
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets weight of player
     *
     * @return weight of player
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets weight of player
     *
     * @param weight weight of player
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets date of birth of player
     *
     * @return date of birth of player
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth of player
     *
     * @param dateOfBirth date of birth of player
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets email of player
     *
     * @return email of player
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email of player
     *
     * @param email email of player
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets mobile of player
     *
     * @return mobile of player
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets mobile of player
     *
     * @param mobile mobile of player
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets list of player info
     *
     * @return list of player info
     */
    public List<PlayerInfo> getPlayerInfos() {
        return Collections.unmodifiableList(playerInfos);
    }

    /**
     * Adds player info to list
     *
     * @param pi added player info
     */
    public void addPlayerInfo(PlayerInfo pi) {
        playerInfos.add(pi);
    }

    /**
     * Removes player info to list
     *
     * @param pi removed player info
     */
    public void removePlayerInfo(PlayerInfo pi) {
        playerInfos.remove(pi);
    }

    /**
     * Updates player info to list
     *
     * @param pi updated player info
     */
    public void updatePlayerInfo(PlayerInfo pi) {
        playerInfos.remove(pi);
        playerInfos.add(pi);
    }

    /**
     * Gets id of player
     *
     * @return player id
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets id of player
     *
     * @param id player id
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player other = (Player) obj;
        if (getEmail() == null) {
            if (other.getEmail() != null) {
                return false;
            }
        } else if (!getEmail().equals(other.getEmail())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", dateOfBirth=" + dateOfBirth + ", email=" + email + ", managerId="
                + manager.getId() + "]";
    }

    /**
     * Returns string of INSERT statement into Player
     *
     * @return string of INSERT statement
     */
    public String toInsertStatement() {
        return "INSERT INTO Player (id,firstName,lastName,email,mobile,weight,height,dateOfBirth,manager_id) VALUES ("
                + getId() + "," + DBEntityUtils.quote(getFirstName()) + "," + DBEntityUtils.quote(getLastName()) + ","
                + DBEntityUtils.quote(getEmail()) + "," + DBEntityUtils.quote(getMobile()) + "," + getWeight() + "," + getHeight() + ","
                + DBEntityUtils.quote(getDateOfBirth().toString()) + ","
                + +getManager().getId()
                + ");"
                + System.lineSeparator();
    }

}

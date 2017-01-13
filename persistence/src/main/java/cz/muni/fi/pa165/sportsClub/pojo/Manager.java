package cz.muni.fi.pa165.sportsClub.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Respresents manager of team
 *
 * @author David Koncak (410155)
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "unique_email", columnNames = {"email"}),
    @UniqueConstraint(name = "unique_club_name", columnNames = {"clubName"}),
    @UniqueConstraint(name = "unique_mobile", columnNames = {"mobile"})})
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String clubName;

    @OneToMany(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Player> players = new ArrayList<>();

    @NotNull
    @Column(nullable = false)
    @Size(max = 32, min = 2)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    @Size(max = 32, min = 2)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    @Pattern(regexp = "[^@]+@[^@]+\\.[^@]+")
    private String email;

    @Pattern(regexp = "(\\+|00)?\\d+")
    private String mobile;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String role;

    public Manager() {
    }

    public Manager(Long id) {
        this.id = id;
    }

    /**
     * Gets id of manager
     *
     * @return manager id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id of manager
     *
     * @param id manager id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name of club
     *
     * @return name of club
     */
    public String getClubName() {
        return clubName;
    }

    /**
     * Sets name of club
     *
     * @param name name of club
     */
    public void setClubName(String name) {
        this.clubName = name;
    }

    /**
     * Gets first name of manager
     *
     * @return first name of manager
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets first name of manager
     *
     * @param firstName first name of manager
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name of manager
     *
     * @return last name of manager
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets last name of manager
     *
     * @param lastName last name of manager
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email of manager
     *
     * @return email of manager
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets email of manager
     *
     * @param email email of manager
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets mobile of manager
     *
     * @return mobile of manager
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * Sets mobile of manager
     *
     * @param mobile mobile of manager
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets password of manager
     *
     * @return password of manager
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets password of manager
     *
     * @param password password of manager
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role for manager
     *
     * @return manager role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Sets role for manager
     *
     * @param role manager role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets list of teams
     *
     * @return list of teams
     */
    public List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }

    /**
     * Adds team to list of teams
     *
     * @param team added team
     */
    public void addTeam(Team team) {
        teams.add(team);
    }

    /**
     * Removes team from list of teams
     *
     * @param team removed team
     */
    public void removeTeam(Team team) {
        teams.remove(team);
    }

    /**
     * Updates team in list of teams
     *
     * @param team updated team
     */
    public void updateTeam(Team team) {
        teams.remove(team);
        teams.add(team);
    }

    /**
     * Gets list of players
     *
     * @return list of players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Adds player to list of players
     *
     * @param player added player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes player from list of players
     *
     * @param player removed player
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Updates player in list of players
     *
     * @param player updated player
     */
    public void updatePlayer(Player player) {
        players.remove(player);
        players.add(player);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + (this.getEmail() != null ? this.getEmail().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Manager)) {
            return false;
        }
        final Manager other = (Manager) obj;
        if ((getEmail() == null) ? (other.getEmail() != null) : !getEmail().equals(other.getEmail())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Manager [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", mobile=" + mobile + ", password=" + password + ", clubName=" + clubName + "]";
    }

    /**
     * Returns string of INSERT statement into Manager
     *
     * @return string of INSERT statement
     */
    public String toInsertStatement() {
        return "INSERT INTO Manager (id,firstName,lastName,email,mobile,password,clubName,role) VALUES (" + getId()
                + ","
                + DBEntityUtils.quote(getFirstName()) + "," + DBEntityUtils.quote(getLastName()) + ","
                + DBEntityUtils.quote(getEmail()) + "," + DBEntityUtils.quote(getMobile()) + ","
                + DBEntityUtils.quote(getPassword()) + "," + DBEntityUtils.quote(getClubName()) + ","
                + DBEntityUtils.quote(getRole()) + ");"
                + System.lineSeparator();
    }
}

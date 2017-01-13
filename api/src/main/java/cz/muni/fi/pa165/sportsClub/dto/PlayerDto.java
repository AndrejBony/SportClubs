package cz.muni.fi.pa165.sportsClub.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cz.muni.fi.pa165.sportsClub.rest.serializers.LocalDateDeserializer;
import cz.muni.fi.pa165.sportsClub.rest.serializers.LocalDateSerializer;

public class PlayerDto {
	private Long id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@Min(75)
	@Max(250)
	private double height;

	@Min(20)
	@Max(150)
	private double weight;

	@NotNull
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateOfBirth;

	@NotNull
	@Pattern(regexp = "[^@]+@[^@]+\\.[^@]+")
	private String email;

	@Pattern(regexp = "(\\+|00)?\\d+")
	private String mobile;

	private ManagerDto manager;

	private List<TeamOfPlayerDto> playerInfos = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<TeamOfPlayerDto> getPlayerInfos() {
		return playerInfos;
	}

	public void addPlayerInfo(TeamOfPlayerDto t) {
		playerInfos.add(t);
	}

	public void removePlayerInfo(TeamOfPlayerDto t) {
		playerInfos.remove(t);
	}

	public void updatePlayerInfo(TeamOfPlayerDto t) {
		playerInfos.remove(t);
		playerInfos.add(t);
	}

	public ManagerDto getManager() {
		return manager;
	}

	public void setManager(ManagerDto manager) {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PlayerDto))
			return false;
		PlayerDto other = (PlayerDto) obj;
		if (getEmail() == null) {
			if (other.getEmail() != null)
				return false;
		} else if (!getEmail().equals(other.getEmail()))
			return false;
		return true;
	}
}

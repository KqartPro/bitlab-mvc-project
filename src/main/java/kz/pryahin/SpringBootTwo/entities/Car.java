package kz.pryahin.SpringBootTwo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cars")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String model;
	private int year;
	private int price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Owner owner;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Country> countries;

}

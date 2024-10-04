package kz.pryahin.SpringBootTwo.controllers;

import kz.pryahin.SpringBootTwo.auth.Client;
import kz.pryahin.SpringBootTwo.auth.Permission;
import kz.pryahin.SpringBootTwo.auth.repositories.ClientRepository;
import kz.pryahin.SpringBootTwo.entities.Car;
import kz.pryahin.SpringBootTwo.entities.Country;
import kz.pryahin.SpringBootTwo.entities.Owner;
import kz.pryahin.SpringBootTwo.repositories.CarRepository;
import kz.pryahin.SpringBootTwo.repositories.CountryRepository;
import kz.pryahin.SpringBootTwo.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/add-car")
	public String openAddCar(Model model) {
		model.addAttribute("owners", ownerRepository.findAll());
		model.addAttribute("countries", countryRepository.findAll());
		return "add-car";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/home")
	public String openHome(Model model,
	                       @RequestParam(required = false, name = "search") String search,
	                       @RequestParam(required = false, name = "sort") String sort) {
		if (search != null) {
			model.addAttribute("cars", carRepository.findAllByModelContainsIgnoreCase(search));
		} else if ("asc".equals(sort)) {
			model.addAttribute("cars", carRepository.findAllByOrderByPriceAsc());
		} else if ("desc".equals(sort)) {
			model.addAttribute("cars", carRepository.findAllByOrderByPriceDesc());
		} else {
			model.addAttribute("cars", carRepository.findAll());
		}
		return "home";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/details")
	public String openDetails(Model model, @RequestParam("id") Long id) {
		model.addAttribute("car", carRepository.findAllById(id));
		model.addAttribute("owners", ownerRepository.findAll());

		Car car = carRepository.findAllById(id);
		model.addAttribute("currentOwnerId", car.getOwner().getId());

		List<Country> countries = countryRepository.findAll();
		countries.removeAll(car.getCountries());
		model.addAttribute("countries", countries);

		return "details";
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/add-owner")
	public String openAddOwner() {
		return "add-owner";
	}

	@PreAuthorize("isAnonymous()")
	@GetMapping("/login")
	public String openLogin() {
		return "login";
	}

	@GetMapping("/register")
	public String openRegister() {
		return "register";
	}

	@PostMapping("add-car")
	public String addCar(@RequestParam("model") String model, @RequestParam("year") int year, @RequestParam("price") int price, @RequestParam("ownerId") Owner owner, @RequestParam("countryId") List<Country> countries) {
		Car car = new Car();
		car.setModel(model);
		car.setYear(year);
		car.setPrice(price);
		car.setOwner(owner);
		car.setCountries(countries);
		carRepository.save(car);
		return "redirect:home";
	}

	@PostMapping("/update-car")
	public String updateCar(@RequestParam("id") Long id, @RequestParam("model") String model, @RequestParam("year") int year, @RequestParam("price") int price, @RequestParam("ownerId") Owner owner, @RequestParam("countryId") List<Country> countries) {
		Car car = new Car();
		car.setId(id);
		car.setModel(model);
		car.setYear(year);
		car.setPrice(price);
		car.setOwner(owner);
		car.setCountries(countries);
		carRepository.save(car);
		return "redirect:home";
	}

	@PostMapping("delete-car")
	public String deleteCar(@RequestParam("id") Long id) {
		carRepository.deleteById(id);
		return "redirect:home";
	}

	@PostMapping("/add-owner")
	public String addOwner(@RequestParam("fullName") String fullName,
	                       @RequestParam("age") int age) {
		Owner owner = new Owner();
		owner.setFullName(fullName);
		owner.setAge(age);
		ownerRepository.save(owner);
		return "redirect:home";
	}

	@PostMapping("/register")
	public String register(@RequestParam("full-name") String fullName,
	                       @RequestParam("email") String email,
	                       @RequestParam("password") String password,
	                       @RequestParam("re-password") String rePassword) {
		Client checkClient = clientRepository.findAllByEmail(email);
		String redirect = "register?userExist";
		if (checkClient == null) {
			redirect = "register?passwordsNotMatch";
			if (password.equals(rePassword)) {
				Client client = new Client();
				client.setFullName(fullName);
				client.setEmail(email);
				client.setPassword(passwordEncoder.encode(password));
				Permission permission = new Permission();
				permission.setId(3L);
				List<Permission> permissions = new ArrayList<>();
				permissions.add(permission);
				client.setPermissions(permissions);
				clientRepository.save(client);
				redirect = "login";
			}
		}
		return "redirect:" + redirect;
	}

}

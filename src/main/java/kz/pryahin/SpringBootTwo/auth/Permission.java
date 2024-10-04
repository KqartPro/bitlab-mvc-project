package kz.pryahin.SpringBootTwo.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permissions")

@Getter
@Setter
public class Permission implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String role;

	@Override
	public String getAuthority() {
		return role;
	}
}


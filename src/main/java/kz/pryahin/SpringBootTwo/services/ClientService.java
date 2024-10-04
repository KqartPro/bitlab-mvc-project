package kz.pryahin.SpringBootTwo.services;

import kz.pryahin.SpringBootTwo.auth.Client;
import kz.pryahin.SpringBootTwo.auth.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements UserDetailsService {
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client client = clientRepository.findAllByEmail(username);
		if (client == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		return client;
	}
}

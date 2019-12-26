package repositories;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;

import dtos.UserDTO;
import models.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
	public UserDTO findById(int id);

	public UserDTO findByUsernameAndPassword(String username, String password);

	public AppUser findByUsername(String username);

	public User save(User user);
}

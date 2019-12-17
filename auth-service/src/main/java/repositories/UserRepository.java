package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dtos.UserDTO;
import models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public UserDTO findById(int id);

	public UserDTO findByUsernameAndPassword(String username, String password);

	public User findByUsername(String username);
}

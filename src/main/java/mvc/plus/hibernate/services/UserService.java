package mvc.plus.hibernate.services;

import mvc.plus.hibernate.dao.UserDAO;
import mvc.plus.hibernate.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserDAO userDAO;
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public List<User> getAll() {
        return userDAO.getAll();
    }
    public User get(int id) {
        return userDAO.get(id);
    }
    @Transactional
    public void create(User user) {
        userDAO.create(user);
    }
    @Transactional
    public void update(int id, User updatedPerson) {
        userDAO.update(id, updatedPerson);
    }
    @Transactional
    public void delete(int id) {
        userDAO.delete(id);
    }
    public Optional<User> findByName(String name) {
        return userDAO.getUserByName(name);
    }
}

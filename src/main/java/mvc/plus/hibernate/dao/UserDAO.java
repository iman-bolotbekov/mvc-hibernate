package mvc.plus.hibernate.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import mvc.plus.hibernate.models.User;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private final EntityManager entityManager;
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional(readOnly = true)
    public User get(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, id);
    }
    @Transactional(readOnly = true)
    public List<User> getAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from User", User.class).getResultList();
    }
    @Transactional
    public void create(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.save(user);
    }
    @Transactional
    public void update(int id, User user) {
        Session session = entityManager.unwrap(Session.class);
        User user1 = session.get(User.class, id);
        user1.setName(user.getName());
        user1.setAge(user.getAge());
        user1.setEmail(user.getEmail());
    }
    @Transactional
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(session.get(User.class, id));
    }
    @Transactional(readOnly = true)
    public Optional<User> getUserByName(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User where name = :name");
        query.setParameter("name", name);
        List<User> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
}

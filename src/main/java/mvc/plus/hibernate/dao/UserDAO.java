package mvc.plus.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import mvc.plus.hibernate.models.User;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private final SessionFactory sessionFactory;
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public User get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User", User.class).getResultList();
    }
    @Transactional
    public void create(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        Session session = sessionFactory.getCurrentSession();
        User user1 = session.get(User.class, id);
        user1.setName(user.getName());
        user1.setAge(user.getAge());
        user1.setEmail(user.getEmail());
    }
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(User.class, id));
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User where name = :name");
        query.setParameter("name", name);
        List<User> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

}

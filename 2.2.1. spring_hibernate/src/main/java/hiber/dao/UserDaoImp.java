package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("FROM User", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      try {
         String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
         return sessionFactory.getCurrentSession()
                 .createQuery(hql, User.class)
                 .setParameter("model", model)
                 .setParameter("series", series)
                 .getSingleResult();
      } catch (NoResultException e) {
         System.out.println("Пользователь с машиной " + model + " " + series + " не найден.");
         return null;
      }
   }
}

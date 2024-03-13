package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.UserRes;
import peaksoft.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query("select u from User u where  u.email = :email")
    Optional<User> findByEmail(String email);
    @Query("select new peaksoft.dto.response.UserRes(u.id,u.name,u.email,count (distinct (ubp)),count (distinct (ufp))) from User u left join u.basketProducts ubp left join u.favoriteProducts ufp where u.role = 'CLIENT' group by u.id,u.name,u.email")
    List<UserRes> getAllClients();
    @Query("select u from User u where  u.email = :email")
    User getByEmail(String email);

}
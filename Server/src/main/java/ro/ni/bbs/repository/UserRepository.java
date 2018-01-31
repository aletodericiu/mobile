package ro.ni.bbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ni.bbs.model.UserEntity;

/**
 * *  Created by rares on 1/10/2018.
 */


public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsernameAndPassword(String username, String password);
}

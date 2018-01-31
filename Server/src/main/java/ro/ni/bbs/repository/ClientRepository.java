package ro.ni.bbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ni.bbs.model.ClientEntity;

/**
 * Created by Rares Abrudan 19.09.2017.
 */

public interface ClientRepository extends JpaRepository<ClientEntity, Long>
{
    ClientEntity findByUuid(String uuid);
}

package rip.jack.waitlistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rip.jack.waitlistapi.domain.TableType;

public interface TableTypeRepository extends JpaRepository<TableType, Integer> {

}

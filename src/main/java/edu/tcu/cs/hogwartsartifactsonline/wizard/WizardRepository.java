package wizard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.sterrotype.Repository;

@Repository
public interface WizardRepository  extends JpaRepository<Wizard, Interger>{
}

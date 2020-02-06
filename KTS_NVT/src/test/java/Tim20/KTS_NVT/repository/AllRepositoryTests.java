package Tim20.KTS_NVT.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SectorRepositoryIntegrationTest.class,
	TicketRepositoryIntegrationTest.class,
	EventRepositoryIntegrationTest.class,
	DayRepositoryIntegrationTest.class,
	UserRoleRepositoryIntegrationTest.class
})
public class AllRepositoryTests {

}

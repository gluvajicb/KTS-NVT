package Tim20.KTS_NVT.service;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@SuiteClasses({
	SectorServiceUnitTest.class,
	TicketServiceUnitTest.class,
	UserServiceUnitTest.class,
	UserServiceIntegrationTest.class,
	SectorServiceIntegrationTest.class,
	LocationServiceIntegrationTest.class,
	EventDayServiceIntegrationTest.class,
	SectorPriceServiceIntegrationTest.class,
	EventServiceIntegrationTest.class,
	TicketServiceIntegrationTest.class
	
})
public class AllServiceTests {

}

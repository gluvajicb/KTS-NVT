package Tim20.KTS_NVT.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	LocationControllerIntegrationTest.class,
	EventControllerIntegrationTest.class,
	TicketControllerIntegrationTest.class,
	SecurityControllerIntegrationTest.class
})
public class AllControllerTests {

}

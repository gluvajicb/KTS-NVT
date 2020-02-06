package Tim20.KTS_NVT.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.repository.SectorPriceRepository;
import tim20.KTS_NVT.service.SectorPriceService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SectorPriceServiceIntegrationTest {

	@Autowired
	private SectorPriceService priceService;

	@Autowired
	private SectorPriceRepository priceRepository;
	
	@Test
	public void findAllTest() {
		List<SectorPrice> prices = priceService.findAll();
		
		assertNotNull(prices);
		assertEquals(3, prices.size());
	}
	
	@Test
	public void findOneTest() {

		SectorPrice price = priceService.findOne(111l);

		assertNotNull(price);
		assertEquals(111, price.getId());
	}

	@Test
	public void findOneNotFoundTest() {

		SectorPrice price = priceService.findOne(7851l);

		assertNull(price);
	}
	
	@Test
	public void savePriceTest() {

		SectorPrice price = new SectorPrice();

		SectorPrice saved = priceService.saveSectorPrice(price);

		assertNotNull(saved);
		assertNotNull(saved.getId());
		
		assertNotNull(priceRepository.findById(saved.getId()));
		
		priceRepository.deleteById(saved.getId());
	}

	@Test
	public void savePriceWithId() {
		SectorPrice price = new SectorPrice();
		price.setId(352l);

		assertNull(priceService.saveSectorPrice(price));

	}
	
}

package geocodingIT;

import connection.TqsBasicHttpClient;
import geocodingIT.AddressResolverIT;
import geocodingIT.Address;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class AddressResolverITTest {

    private AddressResolverIT resolver;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.resolver = new AddressResolverIT(new TqsBasicHttpClient());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        this.resolver = null;
    }

    @Test
    void whenGoodAlboiGps_returnAddress() throws ParseException, IOException, URISyntaxException {

        // Test
        Address result = resolver.findAddressForLocation(40.640661, -8.656688);

        // Return
        assertEquals(new Address( "Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246", null), result);

    }

    @Test
    public void whenBadCoordidates_throwBadArrayindex() throws IOException, URISyntaxException, ParseException {

    }
}

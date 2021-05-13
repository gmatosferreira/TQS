package pt.tqsua.homework.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CacheUnitTest {

    private static String exKey = "Entry1";
    private static String exVal = "I am a value!";
    private static String exKeyTTL = "Entry1 TTL";
    private static String exValTTL = "I am a value for TTL!";
    private static int exTTL = 3;

    @Nested
    public class Populated {

        private Cache<String> cache;


        @BeforeEach
        public void setUp() {
            // Construct new cache
            this.cache = new Cache<>();

            // populate with sample data
            this.cache.put(exKey, exVal);
            this.cache.put(exKeyTTL, exValTTL, exTTL);
        }

        @AfterEach
        public void cleanUp() {
            // Clear memory
            this.cache = null;
        }

        @Test
        public void whenPut_getReturns() {
            // get and validate
            assertThat(this.cache.get(exKey).isPresent(), is(true));
            assertThat(this.cache.get(exKey).get(), equalTo(exVal));
        }

        @Test
        public void whenPut_containsKey() {
            // contains and validate
            assertThat(this.cache.containsKey(exKey), is(true));
        }

        @Test
        public void whenRemove_doesNotContain() {
            // contains and validate
            assertThat(this.cache.containsKey(exKey), is(true));

            // remove
            this.cache.remove(exKey);

            // contains and validate
            assertThat(this.cache.containsKey(exKey), is(false));
        }

        @Test
        public void whenClear_removeAll() {
            // clear and validate
            assertThat(this.cache.clear(), is(2));
        }

        @Test
        public void whenTTLNotExceeded_contains() {
            // contains and validate
            assertThat(this.cache.containsKey(exKeyTTL), is(true));
        }

        @Test
        public void whenTTLExceeded_doesNotContain() throws Exception {
            // wait for TTL to exceed
            TimeUnit.SECONDS.sleep(exTTL+1);
            // contains and validate
            assertThat(this.cache.containsKey(exKeyTTL), is(false));
        }

        @Test
        public void whenTTLNotExceeded_cleanDoesNotReturn() {
            // clean and validate
            assertThat(this.cache.clean(), is(0));
        }

        @Test
        public void whenTTLNotExceeded_cleanReturns() throws Exception {
            // wait for TTL to exceed
            TimeUnit.SECONDS.sleep(exTTL+1);
            // clean and validate
            assertThat(this.cache.clean(), is(1));
        }

        @Test
        public void validateStats() {
            // validate initial value
            assertThat(this.cache.getHits(), is(0));
            assertThat(this.cache.getMisses(), is(0));
            assertThat(this.cache.getSize(), is(2));

            // make operations to change their value
            this.cache.get(exKey);
            this.cache.get(exKey);
            this.cache.get(exKeyTTL);
            this.cache.get("Non existent key");
            this.cache.get("Non existent key 2");

            // validate updated values
            assertThat(this.cache.getHits(), is(3));
            assertThat(this.cache.getMisses(), is(2));
            assertThat(this.cache.getSize(), is(2));
        }

    }

    @Nested
    public class Empty {

        private Cache<String> cache;

        @BeforeEach
        public void setUp() {
            // Construct new cache
            this.cache = new Cache<>();
        }

        @AfterEach
        public void cleanUp() {
            // Clear memory
            this.cache = null;
        }

        @Test
        public void whenEmpty_getReturnsEmpty() {
            // get and validate
            assertThat(this.cache.get("Entry1").isPresent(), is(false));
        }

        @Test
        public void whenEmpty_doesNotContain() {
            // contains and validate
            assertThat(this.cache.containsKey("ABC"), is(false));
        }

        @Test
        public void whenClearEmpty_removeNone() {
            // clear and validate
            assertThat(this.cache.clear(), is(0));
        }

    }

}

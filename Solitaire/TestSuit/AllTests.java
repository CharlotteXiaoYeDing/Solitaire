import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCard.class, TestDeck.class, TestSuitStackManager.class })
public class AllTests {

}

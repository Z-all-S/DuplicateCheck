import org.junit.Test;

public class TestMain {
    @Test
    public void test() {
        String[] args = {"D://test//orig.txt", "D://test//orig_0.8_dis_15.txt", "D://test//result.txt"};
        DuplicateCheck.main(args);
        try {
            Thread.sleep(200 * 1000);
        }catch (InterruptedException ignore){}
    }
}

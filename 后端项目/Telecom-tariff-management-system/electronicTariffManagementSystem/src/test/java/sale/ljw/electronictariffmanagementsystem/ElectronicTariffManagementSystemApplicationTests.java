package sale.ljw.electronictariffmanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@SpringBootTest
class ElectronicTariffManagementSystemApplicationTests {

    @Test
    void contextLoads() {
        ArrayList<Integer> oldModuleIds = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        ArrayList<Integer> newModuleIds = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        System.out.println(newModuleIds);
        System.out.println(oldModuleIds);
        int m = 0, i = 0;
        while (true) {
            if (oldModuleIds.size() == 0 || newModuleIds.size() == 0 || m>=oldModuleIds.size()) {
                break;
            }
            if (Objects.equals(oldModuleIds.get(m), newModuleIds.get(i))) {
                newModuleIds.remove(newModuleIds.get(i));
                oldModuleIds.remove(oldModuleIds.get(m));
            } else {
                i++;
            }
            if (i >= newModuleIds.size()) {
                i = 0;
                m++;
            }
        }
        System.out.println(newModuleIds);
        System.out.println(oldModuleIds);
    }

}

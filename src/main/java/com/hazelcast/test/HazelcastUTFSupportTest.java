package com.hazelcast.test;

import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

public class HazelcastUTFSupportTest {

    private static final String MULTIBYTE_MAP = "MULTIBYTE-MAP";

    public static void main(String[] args) {
        ManagementCenterConfig manCenterCfg = new ManagementCenterConfig();
        manCenterCfg.setEnabled(true).setUrl("http://localhost:18000/hazelcast-mancenter");

/*        Config config = new Config();;
        MapConfig mapConfig= new MapConfig();
        mapConfig.setName("my-map");
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE));
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
        mapConfig.setTimeToLiveSeconds(-1);
        config.addMapConfig(mapConfig);*/

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        Map<String, String> entries = hazelcastInstance.getMap(MULTIBYTE_MAP);

        String key_english = "myutfKey";
        String key_multibyte = "xyzä123Key";
        String value_multibyte = "xyzä123 イロハニホヘト チリヌルヲ ワカヨタレソ ツネナラム";

        entries.put(key_english, value_multibyte);
        entries.put(key_multibyte, value_multibyte);

        Map<String, String> multibyte_map = hazelcastInstance.getMap(MULTIBYTE_MAP);

        assertEquals(value_multibyte, multibyte_map.get(key_english));
        assertEquals(value_multibyte, multibyte_map.get(key_multibyte));

        System.out.println("Total number of entries " + multibyte_map.size());

        System.out.println("Entry with key myutfkey = " + multibyte_map.get(key_english));
        System.out.println("Entry with key xyzä123 = " + multibyte_map.get(key_multibyte));

        Hazelcast.shutdownAll();

/*        HazelcastInstance hzclient = HazelcastClient.newHazelcastClient();
        System.out.println(hzclient);*/
    }

    private static void assertEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected = '" + expected + "' does not match actual = '" + actual + "'");
        } else {
            System.out.println("Expected = '" + expected + "' matches actual = '" + actual + "'");
        }
    }
}

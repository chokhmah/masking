package dotx.lib.masking;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MapMapperTest {
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = GsonFactory.getGson();
    }

    @Test
    public void testHash() {
        Map m1 = new HashMap();
        m1.put("name", "samoka");
        m1.put("pin", "12321");
        String before = gson.toJson(m1);

        List<String> filter = new ArrayList<>();
        filter.add("pin");
        Mask mask = new Mask(filter, str -> {
//            return Base64.getEncoder().encodeToString(String.valueOf(str).getBytes());
//            return String.valueOf(str.hashCode());
            return "*******";
        });

        Map map = mask.map(m1);
        System.out.println(map);
    }

//    @Test
//    public void testNotNullButEmptyFilter() {
//        Map m1 = new HashMap();
//        m1.put("name", "samoka");
//        String before = gson.toJson(m1);
//
//        MapMask mask = new MapMask(new ArrayList<>(), "******");
//        Map m2 = mask.map(m1);
//
//        String after = gson.toJson(m2);
//
//        assertEquals("must be equal", before, after);
//    }

    @Test
    public void testNullMaskAndFilterList() {
        List list1 = new ArrayList();
        list1.add("name");

        String before = gson.toJson(list1);

        Mask mask = new Mask(null, null);
        List list2 = mask.list(list1);

        String after = gson.toJson(list2);

        assertEquals("must be equal", before, after);
    }

    @Test
    public void testNullMaskAndFilterMap() {
        Map m1 = new HashMap();
        m1.put("name", "samoka");

        String orig = gson.toJson(m1);

        Mask mask = new Mask(null, null);
        Map m2 = mask.map(m1);

        String after = gson.toJson(m2);

        assertEquals("must be equal", orig, after);
    }

//    @Test
//    public void testMapper() throws Exception {
//        Map m4 = new HashMap();
//        m4.put("password", "1234");
//        m4.put("pin", "9809");
//        m4.put("name", "juan");
//
//        List l2 = new ArrayList();
//        l2.add("Choy");
//
//        List l1 = new ArrayList();
//        l1.add("mao ni");
//        l1.add(m4);
//        l1.add(l2);
//
//        Map m2 = new HashMap();
//        m2.put("pin", "1212");
//        m2.put("other", l1);
//
//        Map m1 = new HashMap();
//        m1.put("child", m2);
//        m1.put("samoka", "keeu");
//
//        String before = gson.toJson(m1);
//        System.out.println("Before: " + before);
//
//        List<String> filter = new ArrayList<>();
//        filter.add("pin");
//        filter.add("password");
//        String mask = "******";
//        MapMask mapper = new MapMask(filter, mask);
//        Map res = mapper.map(m1);
//
//        String after = gson.toJson(res);
//        System.out.println("After: " + after);
//
//        assertTrue("must be equal", after.equals("{\"samoka\":\"keeu\",\"child\":{\"other\":[\"mao ni\",{\"password\":\""+ mask +"\",\"pin\":\""+ mask +"\",\"name\":\"juan\"},[\"Choy\"]],\"pin\":\""+ mask +"\"}}"));
//    }
}
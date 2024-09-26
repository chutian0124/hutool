package cn.hutool.core.lang;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleCacheTest {

	@BeforeEach
	public void putTest(){
		final SimpleCache<String, String> cache = new SimpleCache<>();
		ThreadUtil.execute(()->cache.put("key1", "value1"));
		ThreadUtil.execute(()->cache.get("key1"));
		ThreadUtil.execute(()->cache.put("key2", "value2"));
		ThreadUtil.execute(()->cache.get("key2"));
		ThreadUtil.execute(()->cache.put("key3", "value3"));
		ThreadUtil.execute(()->cache.get("key3"));
		ThreadUtil.execute(()->cache.put("key4", "value4"));
		ThreadUtil.execute(()->cache.get("key4"));
		ThreadUtil.execute(()->cache.get("key5", ()->"value5"));

		cache.get("key5", ()->"value5");
	}

	@Test
	public void getTest(){
		final SimpleCache<String, String> cache = new SimpleCache<>();
		cache.put("key1", "value1");
		cache.get("key1");
		cache.put("key2", "value2");
		cache.get("key2");
		cache.put("key3", "value3");
		cache.get("key3");
		cache.put("key4", "value4");
		cache.get("key4");
		cache.get("key5", ()->"value5");

		assertEquals("value1", cache.get("key1"));
		assertEquals("value2", cache.get("key2"));
		assertEquals("value3", cache.get("key3"));
		assertEquals("value4", cache.get("key4"));
		assertEquals("value5", cache.get("key5"));
		assertEquals("value6", cache.get("key6", ()-> "value6"));
	}

	@Test
	public void getConcurrencyTest(){
		final SimpleCache<String, String> cache = new SimpleCache<>();
		final ConcurrencyTester tester = new ConcurrencyTester(9000);
		tester.test(()-> cache.get("aaa", ()-> {
			ThreadUtil.sleep(200);
			return "aaaValue";
		}));

		assertTrue(tester.getInterval() > 0);
		assertEquals("aaaValue", cache.get("aaa"));
		IoUtil.close(tester);
	}
}

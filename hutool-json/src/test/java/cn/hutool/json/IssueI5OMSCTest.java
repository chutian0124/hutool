package cn.hutool.json;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Predicate多层过滤
 */
public class IssueI5OMSCTest {

	@Test
	public void filterTest(){
		final JSONObject json = JSONUtil.parseObj(ResourceUtil.readUtf8Str("issueI5OMSC.json"));

		final String s = json.toJSONString(0, (entry) -> {
			final Object key = entry.getKey();
			if(key instanceof String){
				return ListUtil.of("store", "bicycle", "color", "book", "author").contains(key);
			}
			return true;
		});
		assertEquals("{\"store\":{\"bicycle\":{\"color\":\"red\"},\"book\":[{\"author\":\"Evelyn Waugh\"},{\"author\":\"Evelyn Waugh02\"}]}}", s);
	}
}

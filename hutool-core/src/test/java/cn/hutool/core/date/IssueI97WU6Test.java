package cn.hutool.core.date;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IssueI97WU6Test {
	@Test
	public void getTermTest() {
		// 润十月没有三十，十月有三十
		final ChineseDate chineseDate = new ChineseDate(1984, 10, 30, false);
		assertEquals("甲子鼠年 寒月三十", chineseDate.toString());
		assertEquals("小雪", chineseDate.getTerm());
	}
}

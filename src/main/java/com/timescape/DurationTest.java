package com.timescape;

import java.time.Duration;

public class DurationTest {

	public static void main(String[] args) {
		System.out.println(Duration.ofHours(1).toMillis());
		System.out.println(Duration.ofDays(1).toMillis());
	}

}

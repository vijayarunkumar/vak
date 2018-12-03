package org.vak.lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class StreamExample
{
	public static void main(String... strings)
	{
		Collection<String> collection = Arrays.asList("a", "b", "c", "1", "6", "45");
		Stream<String> streamOfCollection = collection.stream();

		System.out.println(streamOfCollection);
		Stream<String> streamFilter = streamOfCollection.filter((s) -> {

			if (s.equals("a"))
				return false;
			return true;
		});
		streamFilter.forEach(s -> System.out.println(s));

		collection.stream().forEachOrdered(s -> System.out.println(s));
		Map<String, String> map = new HashMap<String, String>();

		map.put("1", "1");
		map.put("s", "12");
		map.put("2", "13");
		map.put("3", "14");
		mapTest(map);

	}

	static public void mapTest(Map<String, String> map)
	{
		Stream<Integer> streamFilter = map.keySet().stream().filter((s) -> {

			System.out.println(s);
			if (s.equals("s"))
				return false;
			return true;
		}).map((maps) -> {
			return (int) maps.charAt(0);
		});

		streamFilter.forEach(s -> System.out.println(s));
		System.out.println("-----------------------------");

		Stream<String> streamper = map.keySet().stream().parallel();
		streamper.forEach(s -> System.out.println(s));
	}
}

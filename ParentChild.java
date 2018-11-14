package org.vak.kannan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParentChild
{
	public static void main(String a[])
	{
		ParentChild parentChild = new ParentChild();
		parentChild.test();
	}

	public void test()
	{
		List<Bean> bean = new ArrayList<Bean>();
		createBeans(bean);
		System.out.println(bean);
		Collections.sort(bean);
		System.out.println(bean);

		Bean pareantBean = makeParentChild(bean);
		System.out.println("parent :: " + pareantBean);

	}

	public Bean makeParentChild(List<Bean> beans)
	{
		Map<Integer, Bean> map = new LinkedHashMap<Integer, Bean>();

		for (Bean bean : beans)
		{
			map.put(bean.a, bean);
			List<Integer> childList = bean.b;
			for (Integer childNumber : childList)
			{
				Bean childBean = map.get(childNumber);
				bean.child.add(childBean);
			}
		}

		return beans.get(beans.size() - 1);
	}

	public void createBeans(List<Bean> bean)
	{
		List<Integer> b = new ArrayList<Integer>();
		b.add(13);
		Bean be = new Bean(12, "ARUN", b);
		bean.add(be);

		b = new ArrayList<Integer>();
		be = new Bean(11, "VIJAY", b);
		bean.add(be);

		b = new ArrayList<Integer>();
		b.add(11);
		b.add(12);
		be = new Bean(10, "KANNAN", b);
		bean.add(be);

		b = new ArrayList<Integer>();
		be = new Bean(13, "KUMAR", b);
		bean.add(be);
	}
}

class Bean implements Comparable<Bean>
{

	public Bean(int a, String c, List<Integer> b)
	{
		this.a = a;
		this.c = c;
		this.b = b;
	}

	Integer a = 0;
	List<Integer> b = new ArrayList<Integer>();
	List<Bean> child = new ArrayList<Bean>();
	String c = "";

	@Override
	public int compareTo(Bean o)
	{
		System.out.println(this);
		System.out.println(o);

		return this.a.compareTo(o.a) * (-1);
	}

	@Override
	public String toString()
	{
		return "Bean [a=" + a + ", b=" + b + ", child=" + child + ", c=" + c + "]";
	}

}

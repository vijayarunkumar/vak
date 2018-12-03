package org.vak.kannan;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParentChild_V1
{
	public static void main(String a[])
	{
		ParentChild parentChild = new ParentChild();
		parentChild.test();
	}

	public void test()
	{
		List<Bean_V1> bean = new ArrayList<Bean_V1>();
		createBeans(bean);
		System.out.println(bean);

		Bean_V1 pareantBean = makeParentChild(bean);
		System.out.println("parent :: " + pareantBean);

	}

	public Bean_V1 makeParentChild(List<Bean_V1> beans)
	{
		Map<Integer, Bean_V1> map = new LinkedHashMap<Integer, Bean_V1>();

		for (Bean_V1 bean : beans)
		{
			map.put(bean.a, bean);
		}

		for (Bean_V1 bean : beans)
		{
			List<Integer> childList = bean.b;
			for (Integer childNumber : childList)
			{
				Bean_V1 childBean = map.get(childNumber);
				bean.child.add(childBean);
			}
		}

		return beans.get(beans.size() - 1);
	}

	public void createBeans(List<Bean_V1> bean)
	{
		List<Integer> b = new ArrayList<Integer>();
		b.add(13);
		Bean_V1 be = new Bean_V1(12, "ARUN", b);
		bean.add(be);

		b = new ArrayList<Integer>();
		be = new Bean_V1(11, "VIJAY", b);
		bean.add(be);

		b = new ArrayList<Integer>();
		b.add(11);
		b.add(12);
		be = new Bean_V1(10, "KANNAN", b);
		bean.add(be);

		b = new ArrayList<Integer>();
		be = new Bean_V1(13, "KUMAR", b);
		bean.add(be);
	}
}

class Bean_V1
{

	public Bean_V1(int a, String c, List<Integer> b)
	{
		this.a = a;
		this.c = c;
		this.b = b;
	}

	Integer a = 0;
	List<Integer> b = new ArrayList<Integer>();
	List<Bean_V1> child = new ArrayList<Bean_V1>();
	String c = "";

	@Override
	public String toString()
	{
		return "Bean_V1 [a=" + a + ", b=" + b + ", child=" + child + ", c=" + c + "]";
	}

}

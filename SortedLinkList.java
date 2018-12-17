package org.vak.lambda;

public class SortedLinkList<T>
{
	private DataHolder<T> head = null;
	private int size = 0;

	public SortedLinkList()
	{
		head = new DataHolder<T>();
	}

	public boolean insert(T data)
	{
		if (size == 0)
		{
			head.data = data;
			
		} else
		{
			doSort(data, head);
		}
		this.size++;
		return true;
	}

	private void doSort(T data, DataHolder<T> node)
	{
		int compare = this.compare(data, node.data);

		if (compare < 0)
		{
			DataHolder<T> cNode = new DataHolder<T>();
			if (node.prv == null)
				this.head = cNode;

			cNode.data = data;
			cNode.next = node;
			cNode.prv = node.prv;
			node.prv = cNode;
			if (cNode.prv != null)
				cNode.prv.next = cNode;
		} else if (compare > 0)
		{
			if (node.next == null)
			{
				DataHolder<T> cNode = new DataHolder<T>();
				cNode.data = data;
				node.next = cNode;
				cNode.prv = node;
				return;
			}
			doSort(data, node.next);
		}

	}

	private int compare(T data, T data1)
	{
		return ((Comparable<T>) data).compareTo(data1);
	}

	@SuppressWarnings("hiding")
	private class DataHolder<T>
	{

		DataHolder<T> next = null;
		DataHolder<T> prv = null;
		T data = null;

		@Override
		public String toString()
		{
			return data + "";
		}

	}

	@Override
	public String toString()
	{
		StringBuilder bu = new StringBuilder("SortedLinkList [");
		getALlDataAsString(head, bu);
		return bu.toString() + "]";
	}

	private void getALlDataAsString(DataHolder<T> node, StringBuilder out)
	{
		if (node.next == null)
		{
			out.append(node.data);
			return;
		}

		out.append(node.data+" ,");
		getALlDataAsString(node.next, out);
	}

}

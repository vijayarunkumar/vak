package org.vak.lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Solution
{	

	public static void main(String[] args)
	{
		API api = API.getAPI(100);
		int noOfLicense = 300;
		List<License> licenseList = new ArrayList<License>();
		List<String> type = new ArrayList<String>();
		type.add("LOW");
		type.add("MEDIUM");
		type.add("HIGH");
		for (int i = 0; i < noOfLicense; i++)
		{
			int r = (int) (Math.random() * 3);

			licenseList.add(api.getLicense("L" + i, type.get(r)));
		}

		for (int i = 0; i < 4000; i++)
		{
			int r = (int) (Math.random() * noOfLicense);
			try
			{
				Thread.sleep(5);
				api.serviceRequest(licenseList.get(r));
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}

		try
		{
			Thread.sleep(500);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 1000; i++)
		{
			int r = (int) (Math.random() * noOfLicense);
			api.serviceRequest(licenseList.get(r));
		}

		try
		{
			Thread.sleep(500);
			api.shutDown();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 10; i++)
		{

			int r = (int) (Math.random() * noOfLicense);
			api.serviceRequest(licenseList.get(r));
		}

	}

}

enum TYPE
{
	LOW, MEDIUM, HIGH;
}

class API
{

	private static API abi;
	private int maxCapacity = 0;
	private List<LicenseImpl> serviceQueue = new ArrayList<LicenseImpl>();
	private boolean isAPIShutdown = false;

	private API(int maxCapacity)
	{
		this.maxCapacity = maxCapacity;
		Service.startService(serviceQueue, maxCapacity);
	}

	public static API getAPI(int maxCapacity)
	{
		if (abi == null)
		{
			abi = new API(maxCapacity);
		}
		return abi;
	}

	public void serviceRequest(License license)
	{
		System.out.println(license);
		if (!isAPIShutdown)
			serviceQueue.add((LicenseImpl) license);
		else
			throw new RuntimeException("API Going to shutdown no further request taken to service ");

	}

	public void shutDown()
	{
		isAPIShutdown = true;
		Service.stopService();
	}

	public License getLicense(String name, String type)
	{
		return new LicenseImpl(type, name);
	}

}

class Service implements Runnable
{

	private List<LicenseImpl> serviceRequest;
	private static Service service = null;
	private boolean isStart = true;
	private int maxCapacity = 0;

	private Service(List<LicenseImpl> serviceRequest, int maxCapacity)
	{
		this.serviceRequest = serviceRequest;
		this.maxCapacity = maxCapacity;

	}

	public static void startService(List<LicenseImpl> serviceRequest, int maxCapacity)
	{
		System.out.println("Service Startted  :: " + serviceRequest);
		if (service == null)
		{
			service = new Service(serviceRequest, maxCapacity);

			Thread t = new Thread(service, "API Service");

			t.start();
		}
	}

	public static void stopService()
	{
		service.isStart = false;
	}

	@Override
	public void run()
	{
		service();

	}

	private void service()
	{

		Map<LicenseImpl, Integer> map = new HashMap<LicenseImpl, Integer>();
		Map<Integer, LicenseImpl> serviceMap = new HashMap<Integer, LicenseImpl>();
		while (isStart || this.serviceRequest.size() > 0)
		{
			System.out.println("Engine Start ::: " + this.serviceRequest.size());
			int currentService = 0;
			int count = 0;
			int serviceCount = 0;
			synchronized (serviceRequest)
			{
				System.out.println("this.serviceRequest  :: " + this.serviceRequest);

				while (this.serviceRequest.size() > count && currentService < this.maxCapacity)
				{
					LicenseImpl license = serviceRequest.get(count);

					if (map.containsKey(license))
					{
						int type = license.getService();
						if (map.get(license) < type)
						{
							map.put(license, (map.get(license) + 1));
							serviceMap.put(serviceCount++, license);
							currentService++;
							serviceRequest.remove(count);
						} else
						{
							System.out.println("Count :: " + count);
							count++;
						}

					} else
					{

						map.put(license, 1);
						serviceMap.put(serviceCount++, license);
						currentService++;
						serviceRequest.remove(count);
					}
				}

			}
			try
			{
				serve(serviceMap);
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map = new HashMap<LicenseImpl, Integer>();

		}
	}

	private void serve(Map<Integer, LicenseImpl> map)
	{
		System.out.println("map  :: " + map);
		map.keySet().forEach(new Consumer<Integer>()
		{
			@Override
			public void accept(Integer t)
			{
				System.out.println("Serving for customer :: " + t + " :: " + map.get(t).getName());

			}
		});

	}

}

class LicenseImpl implements License
{

	private static final Map<String, LicenseImpl> list = new HashMap<String, LicenseImpl>();
	private String type = null;
	int service = 0;
	private String name;

	public LicenseImpl(String type, String name)
	{

		this.type = type;
		this.name = name;
		if (TYPE.LOW.toString().equals(type))
		{
			service = 10;
		} else if (TYPE.MEDIUM.toString().equals(type))
		{
			service = 20;
		} else
		{
			service = 30;
		}
		list.put(name, this);

	}

	public static License getLicense(String type, String name)
	{
		if (list.containsKey(name))
			return list.get(name);
		else
			return new LicenseImpl(type, name);
	}

	@Override
	public String getLicenseType()
	{
		return this.type;
	}

	String getName()
	{
		return this.name;
	}

	int getService()
	{
		return this.service;
	}

	@Override
	public boolean equals(Object obj)
	{
		System.out.println("this.name" + this.name);
		return this.name.equals(((LicenseImpl) obj).name);
	}

	@Override
	public int hashCode()
	{
		return this.name.hashCode();
	}

	@Override
	public String toString()
	{
		return "LicenseImpl [type=" + type + ", service=" + service + ", name=" + name + "]";
	}

}

interface License
{
	public String getLicenseType();
}

package me.Pablo97.SignColors;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateChecker {
	
	private SignColors plugin;
	private URL filesFeed;
	
	private String version;
	private String link;

	public UpdateChecker(SignColors signColors, String url)
	{
		this.plugin = signColors;
		
		try
		{
			this.filesFeed = new URL(url);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean updateNeeded()
	{
		try
		{
			InputStream input = this.filesFeed.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input); 
			
			Node latestFile = document.getElementsByTagName("item").item(0);
			NodeList children = latestFile.getChildNodes();
			
			this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
			this.link = children.item(3).getTextContent();
			
			if (!plugin.getDescription().getVersion().equals(version))
			{
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String getVersion()
	{
		return this.version;
	}
	
	public String getLink()
	{
		return this.link;
	}
	
	
}

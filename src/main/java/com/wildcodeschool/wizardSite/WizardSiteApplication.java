package com.wildcodeschool.wizardSite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
@SpringBootApplication
public class WizardSiteApplication
{

	static Connection con;
	public static void main(String[] args) throws Exception
	{
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/wild_db_quest","root","start123!");
		SpringApplication.run(WizardSiteApplication.class, args);
	}

	@RequestMapping("/")
	@ResponseBody
	public String index() throws Exception
	{
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select * from team");
		String output="<h1>list of teams</h1><ul>";
		while(rs.next())
		{
			output +="<li>"+rs.getString(2)+"</li>";
		}
		output += "</ul>";
		return output;

	}
@RequestMapping("/team/{team_id}")
@ResponseBody
public String hello(@PathVariable int team_id) throws Exception
{
	Statement stmt=con.createStatement();
	ResultSet rs=stmt.executeQuery("select * from team where id = "+team_id);
	if(rs.next()) {
		String output="<h1>" +rs.getString("name")+"</h1><ul>";
		Statement stmt2=con.createStatement();
		ResultSet rs2=stmt2.executeQuery("select * from team where name = 'Frankfurt';");
	while(rs2.next())
	{
		output +="<li>"+rs2.getString("firstname")+" "+rs2.getString("lastname")+"</li>";
	}
	output += "</ul>";
	return output;
	} else {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such team!);
	}
		}
	}

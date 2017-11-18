package com.ccoew.finalproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.*;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.csvreader.CsvReader;

@Controller
public class FraudDetectionController {
	@Autowired
	private JavaMailSender mailSender;
	ArrayList <DS_Definition> list=new ArrayList<DS_Definition>();
	
	@RequestMapping(value="/welcomepage.html",method=RequestMethod.GET)
	public ModelAndView Page1(){
		ModelAndView model=new ModelAndView("WelcomePage");
		return model;
	}
	
	public int[] Dist(ArrayList<DS_Definition> ds)
	{
		int temp[]={0};
		int i;
		for(i=0;i<ds.size();i++)
		{
			temp[i]=ds.get(i).dist;
			
		}
		return temp;	
	}
	double fraudAmountTotal=0.0, fraudAmount=0.0, fraudPercent=0.0;
	@RequestMapping(value="/successfulupload.html" , method=RequestMethod.POST)
	public ModelAndView Page2(@RequestParam("file")MultipartFile file) throws Exception{
		InputStream inputStream = null;
		BufferedReader br = null;
		int i=1,j=0;
		String[] rows=null;
		String[] columns=null;
		String[] columns1=null;
		String nextLine[], nextLine1[];
		
		int k;
		double count=0.0;
		
		ArrayList <PD> percent=new ArrayList<PD>();
		//ArrayList <Def> list1=new ArrayList<Def>();
		try {
			if(!file.isEmpty()) {
	        	byte[] bytes = file.getBytes();
	            String completeData = new String(bytes);
	            rows = completeData.split("\n");
	            System.out.println(rows.length);
	            }
			
				while(i<rows.length)
				{
					columns1=rows[i].split(",");
					
					int flag=0;
					if(percent.size()==0){
						PD pd = new PD(0.0, 0, 0);
						pd.setPercent(Double.parseDouble(columns1[2]));
						System.out.println(Double.parseDouble(columns1[2]));
						//System.out.println(columns1[18]+" hi");
						if(columns1[18].charAt(0)=='F')
						{
							System.out.println("Worked");
							pd.setFraud(1);
						
						}else pd.setNonFraud(1);
					
						percent.add(pd);
					
					
					}
					else{
					
						for(j=0 ; j<percent.size() ; j++){
							if(Double.parseDouble(columns1[2])==percent.get(j).getPercent()){
								//System.out.println(columns1[18]+" hi2");
								if(columns1[18].charAt(0)=='F')
									percent.get(j).fraud +=1;
								else percent.get(j).nonFraud +=1;
								flag=1;
								
							}
							
						}
						if(flag==0){
							PD pd = new PD(0.0, 0, 0);
							pd.setPercent(Double.parseDouble(columns1[2]));
							//System.out.println(columns1[18]+" hi3");
							if(columns1[18].charAt(0)=='F')
								pd.setFraud(1);
							else pd.setNonFraud(1);
							percent.add(pd);
							
						}
					}
					i++;
				}
		
				//System.out.println("Percent ArrayList Size : "+percent.size());
				for(i=0; i<percent.size(); i++){
			//System.out.println((i+1)+". Percent : "+percent.get(i).percent+" Fraud : "+percent.get(i).fraud+" NonFraud : "+percent.get(i).nonFraud);
			}
			
				for(i=0 ; i<percent.size() ; i++){
					if(percent.get(i).fraud>=percent.get(i).nonFraud){
						fraudAmountTotal+=percent.get(i).percent;
						count+=1.0;
					}
				}
				//System.out.println(fraudAmountTotal);
				//System.out.println("Fraud Percent : "+fraudPercent);
				fraudAmount=fraudAmountTotal/count;
				
				//System.out.println("FraudAmount : "+fraudAmount);
				
				fraudPercent = fraudAmount/50000.0;
				//System.out.println("FraudPercent : "+(fraudPercent*100)+"% ");
				
	
			
	i=1;
			
		while (i<rows.length) 
			{
				
				columns = rows[i].split(",");
				
				int seventyCross=-1;	
				int location=-1;	
				int periodOfDay=-1;
				int output=-1;
				int dist=-1;
				int addChange=-1;
				int time=-1;
				
				
		String s="NF";
		//System.out.println(columns[2]+" trx");
		if(Double.parseDouble(columns[2])>=(fraudPercent*Double.parseDouble(columns[1])))
		{	
			//System.out.println("seventycross");
			seventyCross=1;
		}
		if(!(columns[4].equalsIgnoreCase(columns[3])))
		
			{
			//System.out.println("location");
				location=1;
			}
					
					
				DateFormat df=new SimpleDateFormat("HH:mm");
				String time_new=columns[6];
				Date cTrxTime=df.parse(time_new);
				//System.out.println("Time of Trx : "+cTrxTime);
				Date d1=df.parse("01:00");
				Date d2=df.parse("07:00");
				if(cTrxTime.after(d1)) 
					{
					
				  if(cTrxTime.before(d2))
				{		
					
					periodOfDay=1;
					//System.out.println("PeriodOfDay : "+periodOfDay);
				}
					}
				String date_new1=columns[5]+" "+columns[6];
				String previous_date=columns[8]+" "+columns[9];
				DateFormat df1=new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date startDate=df1.parse(date_new1);

				Date previousDate=df1.parse(previous_date);
				
				//long diff = startDate.getTime() - previousDate.getTime();
		        //long diffSeconds = diff / 1000 % 60;
		        //long diffMinutes = diff / (60 * 1000) % 60;
		        //long diffHours = diff / (60 * 60 * 1000)%24;
		        int diffInMinutes = (int) ((startDate.getTime() - previousDate.getTime()) / (1000 * 60 ));
				
				//System.out.println("Diff In Minutes : "+diffInMinutes);
				
				if(Integer.parseInt(columns[10])>0)
					dist=1;
				
				if(diffInMinutes<=60)
					time=1;
				
				DateFormat dformat=new SimpleDateFormat("dd/MM/yyyy");
				Date issCardDate1=dformat.parse(columns[14]);
				Date adChangeDate2=dformat.parse(columns[12]);
				int diffInDays = (int) ((issCardDate1.getTime() - adChangeDate2.getTime()) / (1000 * 60 * 60 * 24 ));
				
				if(diffInDays>0 && diffInDays<15)
					addChange=1;
				
				if(columns[18].charAt(0)=='F')
					output=1;
				
				DS_Definition t=new DS_Definition(seventyCross,location,periodOfDay,dist,time,/*addChange,*/output);
				list.add(t);
				
				//System.out.println(list.size());

					i++;	
		}
		
			
		
		}
	 catch (Exception e) {
		
		e.printStackTrace();
	}
			 
		ModelAndView model=new ModelAndView("SuccessfulUpload");
		//model.addObject("msg",list);
		return model;
	}
	
	
		
	BackPropogation bp=new BackPropogation();
	double oin1=0.0;
		@RequestMapping(value="/train.html")
		public ModelAndView Train(){
			ModelAndView model=new ModelAndView("Train");
			
			ArrayList <Def> trainOutput=new ArrayList<Def>();
				//System.out.println(list.size());
				bp.initialize(5, 5, list);
				bp.trainNetwork(trainOutput);
				
				for(int x=0;x<trainOutput.size();x++)
	            {
	            	System.out.println(trainOutput.get(x).epoch+" "+trainOutput.get(x).instanceNum+" "+"Expected "+trainOutput.get(x).in+"  Actual "+trainOutput.get(x).out);
	            }
				
				oin1=bp.oin;
				System.out.println(bp.oin+" oin in training");
				
				
				model.addObject("msg",trainOutput);
		
			return model;
		}

		@RequestMapping(value="/test.html" , method=RequestMethod.GET)
		public ModelAndView Test(){
			ModelAndView model=new ModelAndView("Test");
			return model;
		}

		@RequestMapping(value="/submitDetails.html" , method=RequestMethod.POST)
		public ModelAndView Test1(@RequestParam("ccno") String ccno,@RequestParam("trxamt") double trxamt,@RequestParam("cloc") String cloc,@RequestParam("cdate") String cdate,@RequestParam("ctime") String ctime) throws ClassNotFoundException, SQLException{
			ModelAndView model=new ModelAndView("Test1");
			Class.forName("com.mysql.jdbc.Driver");  
			  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/temp","manasi","manasi123");  
			 //System.out.println("Success");
			
			 Statement stmt1=con.createStatement();
			 String query="select creditlimit,hometown from mail_info3 where ccno='"+ccno+"'";
		
			 ResultSet rs1=stmt1.executeQuery(query);
			
			
			 String htown="";
			double limit=0;
			while(rs1.next())
			{
				limit=rs1.getInt(1);
				htown=rs1.getString(2);
				
			}
			
			System.out.println("Limit: "+limit+" Hometown: "+htown);
			try{
			String ploc = null, pdate=null, ptime=null;
			
			int distance;
				
			int seventyCross=-1;	
			int location=-1;	
			int periodOfDay=-1;
			
			int dist=-1;
			int time=-1;
			
			String temp=""; char c=' ';
			for(int i=0; i<ccno.length(); i++){
					if(ccno.charAt(i)=='-')
						c='_';
					else
						c=ccno.charAt(i);
					temp=temp+c;		
			}
			String sql123="select * from "+temp+" order by trxID DESC LIMIT 1";
			ResultSet rs123=stmt1.executeQuery(sql123);
			
			while(rs123.next())
			{
				ploc=rs123.getString(2);
				pdate=rs123.getString(3);
				ptime=rs123.getString(4);
			}

			System.out.println("Ploc : "+ploc+" Pdate : "+pdate+" Ptime : "+ptime);
			if(trxamt>=(fraudPercent*limit))
			{	
				//System.out.println("seventycross");
				seventyCross=1;
			}
			if(!(cloc.equalsIgnoreCase(htown)))
			
				{
				//System.out.println("location");
					location=1;
				}
						
			String[] date=pdate.split("-");
			String date1=date[2];
			date1=date1+"-"+date[1]+"-"+date[0];
			
			String[] time12=ptime.split(":");
			String time1=time12[0];
			time1=time1+":"+time12[1];
			System.out.println(cdate);
			String[] date5=cdate.split("/");
			String date6=date5[0];
			date6=date6+"-"+date5[1]+"-"+date5[2];
			
					DateFormat df=new SimpleDateFormat("HH:mm");
					String time_new=ctime;
					Date cTrxTime=df.parse(time_new);
					//System.out.println("Time of Trx : "+cTrxTime);
					Date d1=df.parse("01:00");
					Date d2=df.parse("07:00");
					if(cTrxTime.after(d1)) 
						{
						
					  if(cTrxTime.before(d2))
					{		
						
						periodOfDay=1;
						//System.out.println("PeriodOfDay : "+periodOfDay);
					}
						}
					
					
					
					String date_new1=date6+" "+ctime;
					String previous_date=date1+" "+time1;
					System.out.println("Previous Location retrived : "+ploc);
					System.out.println("Previous date retrived : "+date1+" Previous time retrived : "+time1);
					DateFormat df1=new SimpleDateFormat("dd-MM-yyyy HH:mm");
					Date startDate=df1.parse(date_new1);

					Date previousDate=df1.parse(previous_date);
					int diffInMinutes = (int) ((startDate.getTime() - previousDate.getTime()) / (1000 * 60 ));
					
					//System.out.println(diffInMinutes);
					
					if(cloc.equals(ploc))
						distance=0;
					else distance=999;
					
					if(distance>0)
						dist=1;
					
					if(diffInMinutes<60)
						time=1;
					
					System.out.println(seventyCross+" "+location+" "+periodOfDay+" "+dist+" "+time);
					
					//System.out.println(bp.oin+" oin");
					
					
					double INPUTS[]=new double[]{0,0,0,0,0};
					
					INPUTS[0]=seventyCross;
					INPUTS[1]=location;
					INPUTS[2]=periodOfDay;
					INPUTS[3]=dist;
					INPUTS[4]=time;
					
					int g=3;
					
					double oout=bp.examineTransaction(INPUTS);
					
					
					
					if(oout>0)
					{
						model.addObject("msg","This transaction is probably fradulent");
						model.addObject("msg1","Predicted Value of Neural Network is "+oout);
						
						String[] date4;
						date4=cdate.split("-");
						
						String date3=date4[2];
						
						date3=date3+"-"+date4[1]+"-"+date4[0];
						
						String time4=ctime+":00";
						
						System.out.println(g+" "+cloc+" "+date3+" "+time4);
						
						String sql1234="insert into "+temp+" values("+g+", '"+cloc+"', '"+date3+"', '"+time4+"');";
						
						stmt1.executeUpdate(sql1234);
						g++;
						
						
						Class.forName("com.mysql.jdbc.Driver");  
						  
						
						 Statement stmt=con.createStatement(); 
						
						 ResultSet rs=stmt.executeQuery("select emai_id from mail_info3 where ccno='"+ccno+"'");  
						 
						 SimpleMailMessage email = new SimpleMailMessage();
						 while(rs.next())
						  {
							  System.out.println(rs.getString(1));
							  email.setTo(rs.getString(1));
						  }
						  
						
						//System.out.println(rs.getString(4));
						
				       
				        email.setSubject("FRADULENT TRANSACTION");
				        email.setText("Hello Sir/Madam,This is to ensure that the transaction made by ***** account has been made by you at ***** time,please reply with a confirmation");
				         
				        // sends the e-mail
				        mailSender.send(email);
					}
					else
					{
						model.addObject("msg","This transaction is not likely to be fradulent");
						model.addObject("msg1","Predicted Value of Neural Network is "+oout);			}
					
					 
					/*SimpleMailMessage email = new SimpleMailMessage();
				        email.setTo("samargandhi@gmail.com");
				        email.setSubject("Hello");
				        email.setText("Hi");
				         
				        // sends the e-mail
				        mailSender.send(email);*/
			}
			 catch (Exception e) {
					
					e.printStackTrace();
				}
			
			return model;
		}

		
}
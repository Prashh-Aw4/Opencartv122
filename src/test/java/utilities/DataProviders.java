package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	    @DataProvider (name ="LoginData")
		public String [][] getData() throws IOException {
			
			String path = ".\\testData\\Opencart_LoginData.xlsx"; //taking xl file from tesdata
			
			ExcelUtils xlutil = new ExcelUtils(path); //creating object for ExcelUtils
			
			int totalrows = xlutil.getRowCount("Sheet1");
			int totalcolos = xlutil.getCellCount("Sheet1", 1);
			
			String logindata [][]= new String [totalrows][totalcolos]; //created for two dimensional which can store the excel value 
			
			for (int i=1;i<=totalrows;i++) {    //read the data from excel and storing in 2D Array
			for (int j=0;j<totalcolos;j++)	{
				logindata [i-1][j]=xlutil.getCellData("Sheet1", i, j);
			}
				
			}
			return logindata;
		
		}
	}




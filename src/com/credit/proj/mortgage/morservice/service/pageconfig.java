package com.credit.proj.mortgage.morservice.service;

public class pageconfig {

	public static int VehicleID=1;
	public static String VehicleValue="车辆";
	public static int StockownershipID=2;
	public static String StockownershipValue="股权";
	public static int CompanyID=3;
	public static String CompanyValue="无限连带责任-公司";
	public static int PersonID=4;
	public static String PersonValue="无限连带责任-个人";
	public static int MachineinfoID=5;
	public static String MachineinfoValue="机器设备";
	public static int ProductID=6;
	public static String ProductValue="存货/商品";
	public static int HouseID=7;
	public static String HouseValue="住宅";
	public static int OfficebuildingID=8;
	public static String OfficebuildingValue="商铺写字楼";
	public static int HousegroundID=9;
	public static String HousegroundValue="住宅用地";
	public static int BusinessID=10;
	public static String BusinessValue="商业用地";
	public static int BusinessandliveID=11;
	public static String businessandliveValue="商住用地";
	public static int EducationID=12;
	public static String EducationValue="教育用地";
	public static int IndustryID=13;
	public static String IndustryValue="工业用地";
	public static int DdroitID=14;
	public static String DdroitValue="无形权利";
	
	//对应的抵质押物材料清单的dicKey
	public static String mortgageDicKey(int typeId){
		
		String dicKey = "";
		
		switch(typeId){
		case 1:
			dicKey = "m_vehicle";
			break;
		case 2:
			dicKey = "m_stockownership";
			break;
		case 3:
			dicKey = "m_company";
			break;
		case 4:
			dicKey = "m_person";
			break;
		case 5:
			dicKey = "m_machineinfo";
			break;
		case 6:
			dicKey = "m_product";
			break;
		case 7:
			dicKey = "m_house";
			break;
		case 8:
			dicKey = "m_officebuilding";
			break;
		case 9:
			dicKey = "m_houseground";
			break;
		case 10:
			dicKey = "m_business";
			break;
		case 11:
			dicKey = "m_businessandlive";
			break;
		case 12:
			dicKey = "m_education";
			break;
		case 13:
			dicKey = "m_industry";
			break;
		case 14:
			dicKey = "m_droit";
			break;
		default:
			dicKey = "m_person";//暂无材料-默认
		}
		
		return dicKey;
	}
}

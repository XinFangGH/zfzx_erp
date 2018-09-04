package com.zhiwei.credit.action.creditFlow.customer.enterprise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseEmployeeStructure;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseEmployeeStructureService;

/**
 * 企业-员工结构
 * 
 * @author Jiang Wanyu
 *
 */
public class EnterpriseEmployeeStructureAction extends BaseAction{
	
	private String id_xxx_xxx1;private String id_xxx_xxx2;private String id_xxx_xxx3;private String id_xxx_xxx4;
	private String id_xxx_xxx5;private String id_xxx_xxx6;private String id_xxx_xxx7;private String id_xxx_xxx8;
	private String id_xxx_xxx9;private String id_xxx_xxx10;private String id_xxx_xxx11;private String id_xxx_xxx12;
	private String id_xxx_xxx13;private String id_xxx_xxx14;private String id_xxx_xxx15;private String id_xxx_xxx16;
	private String id_xxx_xxx17;private String id_xxx_xxx18;private String id_xxx_xxx19;private String id_xxx_xxx20;
	private String id_xxx_xxx21;private String id_xxx_xxx22;private String id_xxx_xxx23;private String id_xxx_xxx24;
	private String id_xxx_xxx25;private String id_xxx_xxx26;private String id_xxx_xxx27;private String id_xxx_xxx28;
	private String id_xxx_xxx29;private String id_xxx_xxx30;
	
	private int enterpriseId;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private EnterpriseEmployeeStructureService enterpriseEmployeeStructureService;
	/*保存员工结构*/
	public void saveEmpoyeeSt(){
		
		Collection<EnterpriseEmployeeStructure> collection= new ArrayList<EnterpriseEmployeeStructure>();
		
		collection = this.addEmployee(collection);
		
		try {
			this.clearData();
			creditBaseDao.saveOrUpdateAll(collection);
			
			JsonUtil.responseJsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.responseJsonFailure();
		}
		
		
	}
	
	//
	public void getEmployeeSt(){
		
		
		List<EnterpriseEmployeeStructure> list = null;
		
		try {
			list =enterpriseEmployeeStructureService.getListByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JsonUtil.jsonFromList(list);
		
	} 
	
	public String getEmployeeStForIphone(){
		
		List<EnterpriseEmployeeStructure> list = null;
		
		try {
			list =enterpriseEmployeeStructureService.getListByEnterpriseId(enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonUtil.jsonFromListForIphone(list);
		
	} 
	
	//清空数据库 ，为什么？ 因为员工结构对企业来说是唯一的

	private void clearData() throws Exception{
		String hql = "delete from EnterpriseEmployeeStructure e where e.enterpriseId=?";
		creditBaseDao.excuteSQL(hql, this.enterpriseId);
	}
	
	private Collection<EnterpriseEmployeeStructure> addEmployee(Collection<EnterpriseEmployeeStructure> collection){
		
		EnterpriseEmployeeStructure ees1 = new EnterpriseEmployeeStructure();
		ees1.setTextfieldId("id_xxx_xxx1");
		ees1.setTextfieldText(id_xxx_xxx1);
		ees1.setEnterpriseId(enterpriseId);
		collection.add(ees1);
		
		EnterpriseEmployeeStructure ees2 = new EnterpriseEmployeeStructure();
		ees2.setTextfieldId("id_xxx_xxx2");
		ees2.setTextfieldText(id_xxx_xxx2);
		ees2.setEnterpriseId(enterpriseId);
		collection.add(ees2);
		
		EnterpriseEmployeeStructure ees3 = new EnterpriseEmployeeStructure();
		ees3.setTextfieldId("id_xxx_xxx3");
		ees3.setTextfieldText(id_xxx_xxx3);
		ees3.setEnterpriseId(enterpriseId);
		collection.add(ees3);
		
		EnterpriseEmployeeStructure ees4 = new EnterpriseEmployeeStructure();
		ees4.setTextfieldId("id_xxx_xxx4");
		ees4.setTextfieldText(id_xxx_xxx4);
		ees4.setEnterpriseId(enterpriseId);
		collection.add(ees4);
		
		EnterpriseEmployeeStructure ees5 = new EnterpriseEmployeeStructure();
		ees5.setTextfieldId("id_xxx_xxx5");
		ees5.setTextfieldText(id_xxx_xxx5);
		ees5.setEnterpriseId(enterpriseId);
		collection.add(ees5);
		
		EnterpriseEmployeeStructure ees6 = new EnterpriseEmployeeStructure();
		ees6.setTextfieldId("id_xxx_xxx6");
		ees6.setTextfieldText(id_xxx_xxx6);
		ees6.setEnterpriseId(enterpriseId);
		collection.add(ees6);
		
		EnterpriseEmployeeStructure ees7 = new EnterpriseEmployeeStructure();
		ees7.setTextfieldId("id_xxx_xxx7");
		ees7.setTextfieldText(id_xxx_xxx7);
		ees7.setEnterpriseId(enterpriseId);
		collection.add(ees7);
		
		EnterpriseEmployeeStructure ees8 = new EnterpriseEmployeeStructure();
		ees8.setTextfieldId("id_xxx_xxx8");
		ees8.setTextfieldText(id_xxx_xxx8);
		ees8.setEnterpriseId(enterpriseId);
		collection.add(ees8);
		
		EnterpriseEmployeeStructure ees9 = new EnterpriseEmployeeStructure();
		ees9.setTextfieldId("id_xxx_xxx9");
		ees9.setTextfieldText(id_xxx_xxx9);
		ees9.setEnterpriseId(enterpriseId);
		collection.add(ees9);
		
		EnterpriseEmployeeStructure ees10 = new EnterpriseEmployeeStructure();
		ees10.setTextfieldId("id_xxx_xxx10");
		ees10.setTextfieldText(id_xxx_xxx10);
		ees10.setEnterpriseId(enterpriseId);
		collection.add(ees10);
		
		EnterpriseEmployeeStructure ees11 = new EnterpriseEmployeeStructure();
		ees11.setTextfieldId("id_xxx_xxx11");
		ees11.setTextfieldText(id_xxx_xxx11);
		ees11.setEnterpriseId(enterpriseId);
		collection.add(ees11);
		
		EnterpriseEmployeeStructure ees12 = new EnterpriseEmployeeStructure();
		ees12.setTextfieldId("id_xxx_xxx12");
		ees12.setTextfieldText(id_xxx_xxx12);
		ees12.setEnterpriseId(enterpriseId);
		collection.add(ees12);
		
		EnterpriseEmployeeStructure ees13 = new EnterpriseEmployeeStructure();
		ees13.setTextfieldId("id_xxx_xxx13");
		ees13.setTextfieldText(id_xxx_xxx13);
		ees13.setEnterpriseId(enterpriseId);
		collection.add(ees13);
		
		EnterpriseEmployeeStructure ees14 = new EnterpriseEmployeeStructure();
		ees14.setTextfieldId("id_xxx_xxx14");
		ees14.setTextfieldText(id_xxx_xxx14);
		ees14.setEnterpriseId(enterpriseId);
		collection.add(ees14);
		
		EnterpriseEmployeeStructure ees15 = new EnterpriseEmployeeStructure();
		ees15.setTextfieldId("id_xxx_xxx15");
		ees15.setTextfieldText(id_xxx_xxx15);
		ees15.setEnterpriseId(enterpriseId);
		collection.add(ees15);
		
		EnterpriseEmployeeStructure ees16 = new EnterpriseEmployeeStructure();
		ees16.setTextfieldId("id_xxx_xxx16");
		ees16.setTextfieldText(id_xxx_xxx16);
		ees16.setEnterpriseId(enterpriseId);
		collection.add(ees16);
		
		EnterpriseEmployeeStructure ees17 = new EnterpriseEmployeeStructure();
		ees17.setTextfieldId("id_xxx_xxx17");
		ees17.setTextfieldText(id_xxx_xxx17);
		ees17.setEnterpriseId(enterpriseId);
		collection.add(ees17);
		
		EnterpriseEmployeeStructure ees18 = new EnterpriseEmployeeStructure();
		ees18.setTextfieldId("id_xxx_xxx18");
		ees18.setTextfieldText(id_xxx_xxx18);
		ees18.setEnterpriseId(enterpriseId);
		collection.add(ees18);
		
		EnterpriseEmployeeStructure ees19 = new EnterpriseEmployeeStructure();
		ees19.setTextfieldId("id_xxx_xxx19");
		ees19.setTextfieldText(id_xxx_xxx19);
		ees19.setEnterpriseId(enterpriseId);
		collection.add(ees19);
		
		EnterpriseEmployeeStructure ees20 = new EnterpriseEmployeeStructure();
		ees20.setTextfieldId("id_xxx_xxx20");
		ees20.setTextfieldText(id_xxx_xxx20);
		ees20.setEnterpriseId(enterpriseId);
		collection.add(ees20);
		
		EnterpriseEmployeeStructure ees21 = new EnterpriseEmployeeStructure();
		ees21.setTextfieldId("id_xxx_xxx21");
		ees21.setTextfieldText(id_xxx_xxx21);
		ees21.setEnterpriseId(enterpriseId);
		collection.add(ees21);
		
		EnterpriseEmployeeStructure ees22 = new EnterpriseEmployeeStructure();
		ees22.setTextfieldId("id_xxx_xxx22");
		ees22.setTextfieldText(id_xxx_xxx22);
		ees22.setEnterpriseId(enterpriseId);
		collection.add(ees22);
		
		EnterpriseEmployeeStructure ees23 = new EnterpriseEmployeeStructure();
		ees23.setTextfieldId("id_xxx_xxx23");
		ees23.setTextfieldText(id_xxx_xxx23);
		ees23.setEnterpriseId(enterpriseId);
		collection.add(ees23);
		
		EnterpriseEmployeeStructure ees24 = new EnterpriseEmployeeStructure();
		ees24.setTextfieldId("id_xxx_xxx24");
		ees24.setTextfieldText(id_xxx_xxx24);
		ees24.setEnterpriseId(enterpriseId);
		collection.add(ees24);
		
		EnterpriseEmployeeStructure ees25 = new EnterpriseEmployeeStructure();
		ees25.setTextfieldId("id_xxx_xxx25");
		ees25.setTextfieldText(id_xxx_xxx25);
		ees25.setEnterpriseId(enterpriseId);
		collection.add(ees25);
		
		EnterpriseEmployeeStructure ees26 = new EnterpriseEmployeeStructure();
		ees26.setTextfieldId("id_xxx_xxx26");
		ees26.setTextfieldText(id_xxx_xxx26);
		ees26.setEnterpriseId(enterpriseId);
		collection.add(ees26);
		
		EnterpriseEmployeeStructure ees27 = new EnterpriseEmployeeStructure();
		ees27.setTextfieldId("id_xxx_xxx27");
		ees27.setTextfieldText(id_xxx_xxx27);
		ees27.setEnterpriseId(enterpriseId);
		collection.add(ees27);
		
		EnterpriseEmployeeStructure ees28 = new EnterpriseEmployeeStructure();
		ees28.setTextfieldId("id_xxx_xxx28");
		ees28.setTextfieldText(id_xxx_xxx28);
		ees28.setEnterpriseId(enterpriseId);
		collection.add(ees28);
		
		EnterpriseEmployeeStructure ees29 = new EnterpriseEmployeeStructure();
		ees29.setTextfieldId("id_xxx_xxx29");
		ees29.setTextfieldText(id_xxx_xxx29);
		ees29.setEnterpriseId(enterpriseId);
		collection.add(ees29);
		
		EnterpriseEmployeeStructure ees30 = new EnterpriseEmployeeStructure();
		ees30.setTextfieldId("id_xxx_xxx30");
		ees30.setTextfieldText(id_xxx_xxx30);
		ees30.setEnterpriseId(enterpriseId);
		collection.add(ees30);
		
		return collection;
	}
	
	

	public void setEnterpriseId(int enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public void setId_xxx_xxx1(String idXxxXxx1) {
		id_xxx_xxx1 = idXxxXxx1;
	}

	public void setId_xxx_xxx2(String idXxxXxx2) {
		id_xxx_xxx2 = idXxxXxx2;
	}

	public void setId_xxx_xxx3(String idXxxXxx3) {
		id_xxx_xxx3 = idXxxXxx3;
	}

	public void setId_xxx_xxx4(String idXxxXxx4) {
		id_xxx_xxx4 = idXxxXxx4;
	}

	public void setId_xxx_xxx5(String idXxxXxx5) {
		id_xxx_xxx5 = idXxxXxx5;
	}

	public void setId_xxx_xxx6(String idXxxXxx6) {
		id_xxx_xxx6 = idXxxXxx6;
	}

	public void setId_xxx_xxx7(String idXxxXxx7) {
		id_xxx_xxx7 = idXxxXxx7;
	}

	public void setId_xxx_xxx8(String idXxxXxx8) {
		id_xxx_xxx8 = idXxxXxx8;
	}

	public void setId_xxx_xxx9(String idXxxXxx9) {
		id_xxx_xxx9 = idXxxXxx9;
	}

	public void setId_xxx_xxx10(String idXxxXxx10) {
		id_xxx_xxx10 = idXxxXxx10;
	}

	public void setId_xxx_xxx11(String idXxxXxx11) {
		id_xxx_xxx11 = idXxxXxx11;
	}

	public void setId_xxx_xxx12(String idXxxXxx12) {
		id_xxx_xxx12 = idXxxXxx12;
	}

	public void setId_xxx_xxx13(String idXxxXxx13) {
		id_xxx_xxx13 = idXxxXxx13;
	}

	public void setId_xxx_xxx14(String idXxxXxx14) {
		id_xxx_xxx14 = idXxxXxx14;
	}

	public void setId_xxx_xxx15(String idXxxXxx15) {
		id_xxx_xxx15 = idXxxXxx15;
	}

	public void setId_xxx_xxx16(String idXxxXxx16) {
		id_xxx_xxx16 = idXxxXxx16;
	}

	public void setId_xxx_xxx17(String idXxxXxx17) {
		id_xxx_xxx17 = idXxxXxx17;
	}

	public void setId_xxx_xxx18(String idXxxXxx18) {
		id_xxx_xxx18 = idXxxXxx18;
	}

	public void setId_xxx_xxx19(String idXxxXxx19) {
		id_xxx_xxx19 = idXxxXxx19;
	}

	public void setId_xxx_xxx20(String idXxxXxx20) {
		id_xxx_xxx20 = idXxxXxx20;
	}

	public void setId_xxx_xxx21(String idXxxXxx21) {
		id_xxx_xxx21 = idXxxXxx21;
	}

	public void setId_xxx_xxx22(String idXxxXxx22) {
		id_xxx_xxx22 = idXxxXxx22;
	}

	public void setId_xxx_xxx23(String idXxxXxx23) {
		id_xxx_xxx23 = idXxxXxx23;
	}

	public void setId_xxx_xxx24(String idXxxXxx24) {
		id_xxx_xxx24 = idXxxXxx24;
	}

	public void setId_xxx_xxx25(String idXxxXxx25) {
		id_xxx_xxx25 = idXxxXxx25;
	}

	public void setId_xxx_xxx26(String idXxxXxx26) {
		id_xxx_xxx26 = idXxxXxx26;
	}

	public void setId_xxx_xxx27(String idXxxXxx27) {
		id_xxx_xxx27 = idXxxXxx27;
	}

	public void setId_xxx_xxx28(String idXxxXxx28) {
		id_xxx_xxx28 = idXxxXxx28;
	}

	public void setId_xxx_xxx29(String idXxxXxx29) {
		id_xxx_xxx29 = idXxxXxx29;
	}

	public void setId_xxx_xxx30(String idXxxXxx30) {
		id_xxx_xxx30 = idXxxXxx30;
	}
	
}

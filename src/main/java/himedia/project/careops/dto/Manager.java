package himedia.project.careops.dto;
/*@author 노태윤
@editDate 2024-09-13~2024-09-13*/

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
@Builder
@Getter
@NoArgsConstructor
public class Manager {
    private String manager_id;
    private int manager_dept_no;
    private String manager_dept_name;
    private String manager_password;
    private String manager_name;
    private String manager_phone_number;
    
    // Getter and Setter
	public Manager(String manager_id, int manager_dept_no, String manager_dept_name, String manager_password,
			String manager_name, String manager_phone_number) {
		this.manager_id = manager_id;
		this.manager_dept_no = manager_dept_no;
		this.manager_dept_name = manager_dept_name;
		this.manager_password = manager_password;
		this.manager_name = manager_name;
		this.manager_phone_number = manager_phone_number;
	}

}

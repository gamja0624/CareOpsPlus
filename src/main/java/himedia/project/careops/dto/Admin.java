package himedia.project.careops.dto;
/*@author 노태윤
@editDate 2024-09-13~2024-09-19*/
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*@author 노태윤
@editDate 2024-09-13~2024-09-13*/
//
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    private String id;

    private String password;

    private String admin_id;
    private String admin_dept_no;
    private String admin_password; 
    private String admin_name;
    private String admin_phone_number;

    public Admin(String admin_id, String admin_dept_no, String admin_password) {
        this.admin_id = admin_id;
        this.admin_dept_no = admin_dept_no;
        this.admin_password = admin_password;
    }
    
    public String getadmin_dept_no() {
        return admin_dept_no;
    }

    public String getadmin_name() {
        return admin_name;
    }

    public String getadmin_phonenumber() {
        return admin_phone_number;
    }

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

    
}

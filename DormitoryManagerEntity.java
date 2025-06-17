package sut.edu.zyp.dormitory.manage.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 宿舍管理员实体
 *
 * @author zyp
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "dormitory_manager")
public class DormitoryManagerEntity extends AbstractBaseEntity implements Serializable {

    /**
     * 宿舍管理员编号
     */
    @Column(name = "id",unique = true, length = 20, nullable = false)
    private String id;

    /**
     * 管理员姓名
     */
    @Column(length = 50, nullable = false)
    private String name;

    /**
     * 管理员密码
     */
    @Column(length = 100, nullable = false)
    private String password = "e10adc3949ba59abbe56e057f20f883e";

    /**
     * 管理员性别
     */
    @Column(length = 1, nullable = false)
    private String gender;

    /**
     * 管理员电话
     */
    @Column(length = 20, nullable = false)
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

package com.boot.integration.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "design_user")
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Long version;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "creation_date")
    private Date creationDate;

    @Transient
    private List<Role> roleList;

    /**
     * @return id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return version
     */
    public Long getVersion()
    {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Long version)
    {
        this.version = version;
    }

    /**
     * @return creation_date
     */
    public Date getCreationDate()
    {
        return creationDate;
    }

    /**
     * @param creationDate
     */
    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public List<Role> getRoleList()
    {
        return roleList;
    }

    public void setRoleList(List<Role> roleList)
    {
        this.roleList = roleList;
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", version="
            + version + ", creationDate=" + creationDate + ", roleList=" + roleList + '}';
    }
}

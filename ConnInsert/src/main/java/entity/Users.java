package entity;

import Ann.Column;

/**
 * Created by hzq on 2017/12/1.
 */
public class Users {

    @Column("u_no")
    private int uno;
    @Column("u_account")
    private String account;
    @Column("u_name")
    private String uname;
    @Column("u_pwd")
    private String upwd;

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
}
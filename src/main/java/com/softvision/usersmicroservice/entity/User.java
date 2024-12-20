package com.softvision.usersmicroservice.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private java.util.Set<Post> posts = new java.util.HashSet<>();
    public User(){

    }
    public User(Long userid, String firstName,String lastName, String email,String password) {
        this.userid = userid;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;


    }



    public void setUserid(Long userid) {
        this.userid = userid;
    }


    public Long getUserid() {
        return userid;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public String getLastName(){return lastName;}
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return email;

    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }
        public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}


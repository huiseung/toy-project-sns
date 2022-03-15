package com.huiseung.back.entity.user;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Table(name="users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private String profileImageUrl;
    @Column
    private Integer loginCount;
    @Column
    private LocalDateTime lastLoginAt;
    @Column(nullable = false)
    private LocalDateTime createAt;

    //@Entity는 NoArgsConstructor를 필요로 한다
    public User(){}

    public User(Long id, String name, String email, String password,
                String profileImageUrl, Integer loginCount,
                LocalDateTime lastLoginAt, LocalDateTime createAt){
        checkArgument(name != null, "name must be provided");
        checkArgument(email != null, "email must be provided");
        checkArgument(password != null, "password must be provided");

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.loginCount = loginCount;
        this.lastLoginAt = lastLoginAt;
        this.createAt = defaultIfNull(createAt, LocalDateTime.now());
    }
    //
    public Long getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getProfileImageUrl(){return profileImageUrl;}
    public Integer getLoginCount(){return loginCount;}
    public LocalDateTime getLastLoginAt(){return lastLoginAt;}
    public LocalDateTime getCreateAt(){return createAt;}
    //

    public void login(PasswordEncoder passwordEncoder, String credential){
        if(!passwordEncoder.matches(credential, password)){
            throw new IllegalArgumentException("not match password");
        }
        this.loginCount += 1;
        this.lastLoginAt = LocalDateTime.now();
    }
    //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.getId());
    }
    //
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    //
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("email", email)
                .append("password", "[PROTECTED]")
                .append("profileImageUrl", profileImageUrl)
                .append("loginCount", loginCount)
                .append("lastLoginAt", lastLoginAt)
                .append("createAt", createAt)
                .toString();
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        public Long id;
        public String name;
        public String email;
        public String password;
        public String profileImageUrl;
        public Integer loginCount;
        public LocalDateTime lastLoginAt;
        public LocalDateTime createAt;

        public Builder(){}

        public Builder id(Long id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }
        public Builder profileImageUrl(String profileImageUrl){
            this.profileImageUrl = profileImageUrl;
            return this;
        }
        public User build(){
            return new User(
                    this.id,
                    this.name,
                    this.email,
                    this.password,
                    this.profileImageUrl,
                    this.loginCount,
                    this.lastLoginAt,
                    this.createAt
            );
        }
    }
}

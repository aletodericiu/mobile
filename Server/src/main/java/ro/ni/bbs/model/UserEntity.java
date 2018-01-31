package ro.ni.bbs.model;

import lombok.*;

import javax.persistence.*;

/**
 * *  Created by rares on 1/10/2018.
 */

@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;

    public UserEntity(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}

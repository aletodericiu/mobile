package ro.ni.bbs.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Rares Abrudan 19.09.2017.
 */

@Entity
@Table(name="client")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String uuid;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String carModel;
    @Column
    private String carName;

    public ClientEntity(String uuid, String name, String email, String carModel, String carName)
    {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.carModel = carModel;
        this.carName = carName;
    }
}


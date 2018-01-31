package ro.ni.bbs.dto;

import lombok.*;

/**
 * Created by Rares Abrudan 19.09.2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ClientDTO
{
    String uuid;
    String name;
    String email;
    String carModel;
    String carName;
}

package ro.ni.bbs.dto;

import lombok.*;

/**
 * *  Created by rares on 1/10/2018.
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserDTO
{
    private String username;
    private String password;
}

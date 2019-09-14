package af.dfi.data.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUser {

    private String id;
    private String username;
    private String email;
    private String phone;
    private boolean enbled;

}

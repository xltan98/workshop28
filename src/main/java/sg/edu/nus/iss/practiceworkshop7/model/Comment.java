package sg.edu.nus.iss.practiceworkshop7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String cId;
    private String user;
    private Integer rating;
    private String cText;
    private Integer gid;
}

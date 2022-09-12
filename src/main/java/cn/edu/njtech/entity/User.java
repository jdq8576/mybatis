package cn.edu.njtech.entity;

import lombok.*;

/**
 * @author tim
 * @date 2022/9/9 7:45 下午
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String name;
    private String gender;
}

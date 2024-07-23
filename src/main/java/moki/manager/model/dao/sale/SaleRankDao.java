package moki.manager.model.dao.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRankDao {

    private String name;
    private Integer price;
    private Long totalCount;
}

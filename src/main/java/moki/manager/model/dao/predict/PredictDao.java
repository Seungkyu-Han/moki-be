package moki.manager.model.dao.predict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictDao {

    private LocalDate localDate;

    private Float count;
}

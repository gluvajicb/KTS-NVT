package tim20.KTS_NVT.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldLengthException extends RuntimeException {
    private String fieldName;
    private int minSize;
    private int maxSize;
}
